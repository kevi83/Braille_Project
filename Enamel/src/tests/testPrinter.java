package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enamel.Block;
import enamel.InvalidBlockException;
import enamel.InvalidCellException;
import enamel.OddSpecialCharacterException;
import enamel.BrailleInterpreter;
import enamel.Printer;

public class testPrinter {
	
	//new comment
	
	File file;
	Printer printer;
	Scanner reader;
	int buttons;
	BrailleInterpreter interpreter = new BrailleInterpreter();
	boolean first;
	private String[] buttonLabels = {"ONEE", "TWOO", "THREEE", "FOURR",
			"FIVEE", "SIXX", "SEVENN", "EIGHTT", "NINEE", "TENN", "ELEVENN",
			"TWELVEE"};
	
	//Initiates the file for each test
	@Before
	public void setup() {
		file = new File("test.txt");
	}
	
	//Deletes the file created by each test
	@After
	public void tearDown() {
		file.delete();
	}
	
	//Commonly reused tests for initial block of file
	public void initialBlock(int cell, int buttons) {
		this.buttons = buttons;
		assertEquals("Cell " + cell, reader.nextLine());
		assertEquals("Button " + buttons, reader.nextLine());
		assertEquals("", reader.nextLine());
		assertEquals("/~pause:1", reader.nextLine());
		assertEquals("", reader.nextLine());
	}
	
	/*
	//Commonly reused tests for new blocks
	public void checkOldBlock(Block block) throws InvalidCellException {
		if(first) first = false;
		else assertEquals("/~NEXTT", reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		assertEquals("/~disp-cell-pins:0 " + interpreter.getPins(block.letter), reader.nextLine());
		assertEquals(block.story, reader.nextLine());
		assertEquals("/~skip-button:0 ONEE", reader.nextLine());
		assertEquals("/~skip-button:1 TWOO", reader.nextLine());
		assertEquals("/~user-input", reader.nextLine());
		assertEquals("/~ONEE", reader.nextLine());
		assertEquals((block.answer == 1) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
		assertEquals((block.answer == 1) ? block.correctResponse : block.wrongResponse, reader.nextLine());
		assertEquals("/~skip:NEXTT", reader.nextLine());
		assertEquals("/~TWOO", reader.nextLine());
		assertEquals((block.answer == 2) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
		assertEquals((block.answer == 2) ? block.correctResponse : block.wrongResponse, reader.nextLine());
		assertEquals("/~skip:NEXTT", reader.nextLine());
		assertEquals("", reader.nextLine());
	}
	
	public void checkOldBlock(ArrayList<Block> blocks, Block block) throws InvalidCellException {
		if(first) first = false;
		else assertEquals("/~NEXTT", reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		assertEquals("/~disp-cell-pins:0 " + interpreter.getPins(block.letter), reader.nextLine());
		assertEquals(block.story, reader.nextLine());
		assertEquals("/~skip-button:0 ONEE", reader.nextLine());
		assertEquals("/~skip-button:1 TWOO", reader.nextLine());
		assertEquals("/~user-input", reader.nextLine());
		assertEquals("/~ONEE", reader.nextLine());
		assertEquals((block.answer == 1) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
		assertEquals((block.answer == 1) ? block.correctResponse : block.wrongResponse, reader.nextLine());
		assertEquals("/~skip:NEXTT", reader.nextLine());
		assertEquals("/~TWOO", reader.nextLine());
		assertEquals((block.answer == 2) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
		assertEquals((block.answer == 2) ? block.correctResponse : block.wrongResponse, reader.nextLine());
		assertEquals("/~skip:" + blocks.get(blocks.indexOf(block) + 1), reader.nextLine());
		assertEquals("", reader.nextLine());
	}
	
	
	private void checkNewBlock(Block block) throws InvalidCellException {
		if(first) first = false;
		else assertEquals("/~" + block.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		assertEquals("/~disp-string:" + block.cells, reader.nextLine());
		assertEquals(block.story, reader.nextLine());
		for(int i = 1; i <= block.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " " + "JUMPP" + i , reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= block.buttonsUsed; i++) {
			assertEquals("/~" + "JUMPP" + i, reader.nextLine());
			assertEquals((block.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((block.answer == i) ? block.correctResponse : block.wrongResponse, reader.nextLine());
			assertEquals("/~skip:" + blocks.get(index + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	*/
	
	private void checkNewBlock(ArrayList<Block> blocks, Block block) throws InvalidCellException {
		boolean repeat = (block.buttonsUsed < this.buttons);
		assertEquals("/~" + block.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		if(repeat) assertEquals("/~repeat", reader.nextLine());
		assertEquals("/~disp-string:" + block.cells, reader.nextLine());
		assertEquals(block.story, reader.nextLine());
		if(repeat) {
			assertEquals("/~endrepeat", reader.nextLine());
			assertEquals("/~repeat-button:" + this.buttons, reader.nextLine());
		}
		for(int i = 1; i <= block.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " " + "JUMPP" + i , reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= block.buttonsUsed; i++) {
			assertEquals("/~" + "JUMPP" + i, reader.nextLine());
			assertEquals((block.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((block.answer == i) ? block.correctResponse : block.wrongResponse, reader.nextLine());
			if(blocks.indexOf(block) < blocks.size() - 1) 
				assertEquals("/~skip:" + blocks.get(blocks.indexOf(block) + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	
	//Tests for file creation/reading exceptions, also checks that the initial block is printed correctly
	@Test
	public void testInitial1() throws OddSpecialCharacterException {
		
		try {
			printer = new Printer("test.txt", 1, 4);
		} catch (IOException e1) {
			fail("Constructor threw IOException");
		} catch (InvalidBlockException e) {
			fail("Constructor threw InvalidBlockException");
			e.printStackTrace();
		}
		
		try {
			printer.print();
		} catch (IOException e) {
			fail("print() threw IOException");
		}
		
		
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			fail("Scanner threw FileNotFoundException");
		}
		
		initialBlock(1, 4);
	}
	
	@Test
	public void test1OldBlock() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt");
		Block tBlock = new Block("name", "hi", "yes", "no", 1, 'c', 2);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkNewBlock(blocks, tBlock);
	}
	
	@Test
	public void testOld2Block() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 1, 4);
		Block tBlock1 = new Block("name", "hi", "yes", "no", 1, 'c');
		Block tBlock2 = new Block("name", "hello", "yep", "nope", 2, 'd');
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock1);
		blocks.add(tBlock2);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkNewBlock(blocks, tBlock1);
		checkNewBlock(blocks, tBlock2);
	}
	
	@Test
	public void testOldBlockList1() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 1, 4);
		Block tBlock1 = new Block("name", "hi", "yes", "no", 1, 'c', 2);
		Block tBlock2 = new Block("name", "hello", "yep", "nope", 2, 'd', 2);
		ArrayList<Block> blockList = new ArrayList<>();
		blockList.add(tBlock1);
		blockList.add(tBlock2);
		printer.addBlockList(blockList);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkNewBlock(blockList, tBlock1);
		checkNewBlock(blockList, tBlock2);
	}
	
	@Test
	public void testOldBlockList2() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 1, 4);
		Block tBlock1 = new Block("name", "hi", "yes", "no", 1, 'c', 2);
		ArrayList<Block> blockList = new ArrayList<>();
		blockList.add(tBlock1);
		printer.addBlockList(blockList);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkNewBlock(blockList, tBlock1);
	}
	
	@Test
	public void testNoBlock() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 3, 6);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(3, 6);
	}
	
	@Test
	public void testNoCorrect() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		Block tBlock = new Block("name", "hey!", "", "no", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(5, 6);
		checkNewBlock(blocks, tBlock);
	}
	
	@Test
	public void testNoIncorrect() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		Block tBlock = new Block("name", "hey!", "yes", "", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(5, 6);
		checkNewBlock(blocks, tBlock);
	}
	
	@Test
	public void testSoundTag() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		Block tBlock = new Block("name", "hey! <correct.wav>", "yes", "", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(5, 6);
		
		boolean repeat = (tBlock.buttonsUsed < this.buttons);
		assertEquals("/~" + tBlock.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		if(repeat) assertEquals("/~repeat", reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.cells, reader.nextLine());
		assertEquals(tBlock.story.split("<")[0], reader.nextLine());
		assertEquals("/~sound:correct.wav", reader.nextLine());
		if(repeat) {
			assertEquals("/~endrepeat", reader.nextLine());
			assertEquals("/~repeat-button:" + this.buttons, reader.nextLine());
		}
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " JUMPP" + i, reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~" + "JUMPP" + i, reader.nextLine());
			assertEquals((tBlock.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((tBlock.answer == i) ? tBlock.correctResponse : tBlock.wrongResponse, reader.nextLine());
			if(i <= blocks.size() - 1)
				assertEquals("/~skip:" + blocks.get(i + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	
	@Test
	public void testSoundTag2() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		Block tBlock = new Block("name", "hey! <correct.wav> nice", "yes", "", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(5, 6);
		
		boolean repeat = (tBlock.buttonsUsed < this.buttons);
		assertEquals("/~" + tBlock.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		if(repeat) assertEquals("/~repeat", reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.cells, reader.nextLine());
		assertEquals(tBlock.story.split("<")[0], reader.nextLine());
		assertEquals("/~sound:correct.wav", reader.nextLine());
		assertEquals(tBlock.story.split("<")[1].split(">")[1], reader.nextLine());
		if(repeat) {
			assertEquals("/~endrepeat", reader.nextLine());
			assertEquals("/~repeat-button:" + this.buttons, reader.nextLine());
		}
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " JUMPP" + i, reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~" + "JUMPP" + i, reader.nextLine());
			assertEquals((tBlock.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((tBlock.answer == i) ? tBlock.correctResponse : tBlock.wrongResponse, reader.nextLine());
			if(i <= blocks.size() - 1)
				assertEquals("/~skip:" + blocks.get(i + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	
	@Test
	public void testSoundTag3() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		Block tBlock = new Block("name", "<correct.wav> nice", "yes", "", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(5, 6);
		
		boolean repeat = (tBlock.buttonsUsed < this.buttons);
		assertEquals("/~" + tBlock.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		if(repeat) assertEquals("/~repeat", reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.cells, reader.nextLine());
		assertEquals(tBlock.story.split("<")[0], reader.nextLine());
		assertEquals("/~sound:correct.wav", reader.nextLine());
		assertEquals(tBlock.story.split("<")[1].split(">")[1], reader.nextLine());
		if(repeat) {
			assertEquals("/~endrepeat", reader.nextLine());
			assertEquals("/~repeat-button:" + this.buttons, reader.nextLine());
		}
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " JUMPP" + i, reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~JUMPP" + i, reader.nextLine());
			assertEquals((tBlock.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((tBlock.answer == i) ? tBlock.correctResponse : tBlock.wrongResponse, reader.nextLine());
			if(i <= blocks.size() - 1)
				assertEquals("/~skip:" + blocks.get(i + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	
	@Test
	public void testSoundTag4() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		Block tBlock = new Block("name", "<correct.wav>", "yes", "", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(5, 6);
		
		boolean repeat = (tBlock.buttonsUsed < this.buttons);
		assertEquals("/~" + tBlock.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		if(repeat) assertEquals("/~repeat", reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.cells, reader.nextLine());
		assertEquals(tBlock.story.split("<")[0], reader.nextLine());
		assertEquals("/~sound:correct.wav", reader.nextLine());
		if(repeat) {
			assertEquals("/~endrepeat", reader.nextLine());
			assertEquals("/~repeat-button:" + this.buttons, reader.nextLine());
		}
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " JUMPP" + i, reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~JUMPP" + i, reader.nextLine());
			assertEquals((tBlock.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((tBlock.answer == i) ? tBlock.correctResponse : tBlock.wrongResponse, reader.nextLine());
			if(i <= blocks.size() - 1)
				assertEquals("/~skip:" + blocks.get(i + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	
	@Test
	public void testSoundTag5() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		try {
			Block tBlock = new Block("name", "p< < correct.wav>", "yes", "", 3, 'c', 5);
			printer.addBlock(tBlock);
			printer.print();
			fail();
		}
		
		catch(OddSpecialCharacterException e) {
			
		}
	}
	
	@Test
	public void testSoundTag6() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		try {
			Block tBlock = new Block("name", "p  <<correct.wav>", "yes", "", 3, 'c', 5);
			printer.addBlock(tBlock);
			printer.print();
			fail();
		}
		
		catch(OddSpecialCharacterException e) {
			
		}
	}
	
	@Test
	public void testSoundTag7() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		try {
			Block tBlock = new Block("name", "p  >hey <", "yes", "", 3, 'c', 5);
			printer.addBlock(tBlock);
			printer.print();
			fail();
		}
		
		catch(OddSpecialCharacterException e) {
			
		}
	}
	
	@Test
	public void testPinTag() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 6, 6);
		Block tBlock = new Block("name", "hey! *waffle* nice", "yes", "", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(6, 6);
		
		boolean repeat = (tBlock.buttonsUsed < this.buttons);
		assertEquals("/~" + tBlock.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		if(repeat) assertEquals("/~repeat", reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.cells, reader.nextLine());
		assertEquals(tBlock.story.split("\\*")[0], reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.story.split("\\*")[1], reader.nextLine());
		assertEquals(tBlock.story.split("\\*")[2], reader.nextLine());
		if(repeat) {
			assertEquals("/~endrepeat", reader.nextLine());
			assertEquals("/~repeat-button:" + this.buttons, reader.nextLine());
		}
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " JUMPP" + i, reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~JUMPP" + i, reader.nextLine());
			assertEquals((tBlock.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((tBlock.answer == i) ? tBlock.correctResponse : tBlock.wrongResponse, reader.nextLine());
			if(i <= blocks.size() - 1)
				assertEquals("/~skip:" + blocks.get(i + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	
	@Test
	public void testPinTag1() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 6, 6);
		Block tBlock = new Block("name", "*waffle* nice", "yes", "", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(6, 6);
		
		boolean repeat = (tBlock.buttonsUsed < this.buttons);
		assertEquals("/~" + tBlock.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		if(repeat) assertEquals("/~repeat", reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.cells, reader.nextLine());
		assertEquals(tBlock.story.split("\\*")[0], reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.story.split("\\*")[1], reader.nextLine());
		assertEquals(tBlock.story.split("\\*")[2], reader.nextLine());
		if(repeat) {
			assertEquals("/~endrepeat", reader.nextLine());
			assertEquals("/~repeat-button:" + this.buttons, reader.nextLine());
		}
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " JUMPP" + i, reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~JUMPP" + i, reader.nextLine());
			assertEquals((tBlock.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((tBlock.answer == i) ? tBlock.correctResponse : tBlock.wrongResponse, reader.nextLine());
			if(i <= blocks.size() - 1)
				assertEquals("/~skip:" + blocks.get(i + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	
	@Test
	public void testPinTag2() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 6, 6);
		Block tBlock = new Block("name", "nice *waffle*", "yes", "", 3, 'c', 5);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock);
		printer.addBlockList(blocks);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(6, 6);
		
		boolean repeat = (tBlock.buttonsUsed < this.buttons);
		assertEquals("/~" + tBlock.name.toUpperCase(), reader.nextLine());
		assertEquals("/~disp-clearAll", reader.nextLine());
		if(repeat) assertEquals("/~repeat", reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.cells, reader.nextLine());
		assertEquals(tBlock.story.split("\\*")[0], reader.nextLine());
		assertEquals("/~disp-string:" + tBlock.story.split("\\*")[1], reader.nextLine());
		if(repeat) {
			assertEquals("/~endrepeat", reader.nextLine());
			assertEquals("/~repeat-button:" + this.buttons, reader.nextLine());
		}
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~skip-button:" + i + " JUMPP" + i, reader.nextLine());
		}
		assertEquals("/~user-input", reader.nextLine());
		for(int i = 1; i <= tBlock.buttonsUsed; i++) {
			assertEquals("/~JUMPP" + i, reader.nextLine());
			assertEquals((tBlock.answer == i) ? "/~sound:correct.wav" : "/~sound:wrong.wav", reader.nextLine());
			assertEquals((tBlock.answer == i) ? tBlock.correctResponse : tBlock.wrongResponse, reader.nextLine());
			if(i <= blocks.size() - 1)
				assertEquals("/~skip:" + blocks.get(i + 1).name.toUpperCase(), reader.nextLine());
		}
		assertEquals("", reader.nextLine());
	}
	
	@Test
	public void testPinTag3() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException {
		printer = new Printer("test.txt", 5, 6);
		try {
			Block tBlock = new Block("name", " * * * ", "yes", "", 3, 'c', 5);
			printer.addBlock(tBlock);
			printer.print();
			fail();
		}
		
		catch(OddSpecialCharacterException e) {
			System.out.println(e.getMessage());
		}
	}

}
