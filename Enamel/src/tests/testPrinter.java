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
import enamel.BrailleInterpreter;
import enamel.InvalidBlockException;
import enamel.InvalidCellException;
import enamel.Printer;

public class testPrinter {
	
	//new comment
	
	File file;
	Printer printer;
	Scanner reader;
	BrailleInterpreter interpreter = new BrailleInterpreter();
	
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
		assertEquals("Cell " + cell, reader.nextLine());
		assertEquals("Button " + buttons, reader.nextLine());
		assertEquals("", reader.nextLine());
	}
	
	//Commonly reused tests for new blocks
	public void checkBlock(Block block) throws InvalidCellException {
		assertEquals("/~disp-cell-clear:0", reader.nextLine());
		assertEquals("/~disp-cell-pins:0 " + interpreter.getPins(block.letter), reader.nextLine());
		assertEquals(block.premise, reader.nextLine());
		assertEquals("/~skip-button:0 ONEE", reader.nextLine());
		assertEquals("/~skip-button:1 TWOO", reader.nextLine());
		assertEquals("/~user-input", reader.nextLine());
		assertEquals("/~ONEE", reader.nextLine());
		assertEquals((block.answer == 1) ? "/~correct.wav" : "/~wrong.wav", reader.nextLine());
		assertEquals((block.answer == 1) ? block.correctResponse : block.wrongResponse, reader.nextLine());
		assertEquals("/~skip:NEXTT", reader.nextLine());
		assertEquals("/~TWOO", reader.nextLine());
		assertEquals((block.answer == 2) ? "/~correct.wav" : "/~wrong.wav", reader.nextLine());
		assertEquals((block.answer == 2) ? block.correctResponse : block.wrongResponse, reader.nextLine());
		assertEquals("/~skip:NEXTT", reader.nextLine());
		assertEquals("", reader.nextLine());
	}
	
	//Tests for file creation/reading exceptions, also checks that the initial block is printed correctly
	@Test
	public void testInitial1() {
		
		try {
			printer = new Printer("test.txt", 1, 4);
		} catch (IOException e1) {
			fail("Constructor threw IOException");
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
		
		//No more lines
		assertEquals(false, reader.hasNextLine());
	}
	
	@Test
	public void test1Block() throws IOException, InvalidBlockException, InvalidCellException {
		printer = new Printer("test.txt");
		Block tBlock = new Block("name", "hi", "yes", "no", 1, 'c', 2);
		printer.addBlock(tBlock);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkBlock(tBlock);
	}
	
	@Test
	public void test2Block() throws IOException, InvalidBlockException, InvalidCellException {
		printer = new Printer("test.txt", 1, 4);
		Block tBlock1 = new Block("name", "hi", "yes", "no", 1, 'c');
		Block tBlock2 = new Block("name", "hello", "yep", "nope", 2, 'd');
		printer.addBlock(tBlock1);
		printer.addBlock(tBlock2);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkBlock(tBlock1);
		checkBlock(tBlock2);
	}
	
	@Test
	public void testBlockList1() throws IOException, InvalidBlockException, InvalidCellException {
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
		checkBlock(tBlock1);
		checkBlock(tBlock2);
	}
	
	@Test
	public void testBlockList2() throws IOException, InvalidBlockException, InvalidCellException {
		printer = new Printer("test.txt", 1, 4);
		Block tBlock1 = new Block("name", "hi", "yes", "no", 1, 'c', 2);
		ArrayList<Block> blockList = new ArrayList<>();
		blockList.add(tBlock1);
		printer.addBlockList(blockList);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkBlock(tBlock1);
	}

}
