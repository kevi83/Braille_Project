package tests;

import enamel.Block;
import enamel.Printer;
import enamel.BrailleInterpreter;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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
	public void checkBlock(Block block) {
		assertEquals("/~disp-cell-clear:0", reader.nextLine());
		assertEquals("/~disp-cell-pins:0 " + interpreter.getPins(block.letter), reader.nextLine());
		assertEquals(block.premise, reader.nextLine());
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
	public void test1Block() throws IOException {
		printer = new Printer("test.txt", 1, 4);
		Block tBlock = new Block("hi", "yes", "no", 1, 'c', 2);
		printer.addBlock(tBlock);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkBlock(tBlock);
	}
	
	@Test
	public void test2Block() throws IOException {
		printer = new Printer("test.txt", 1, 4);
		Block tBlock1 = new Block("hi", "yes", "no", 1, 'c', 2);
		Block tBlock2 = new Block("hello", "yep", "nope", 1, 'd', 2);
		printer.addBlock(tBlock1);
		printer.addBlock(tBlock2);
		printer.print();
		reader = new Scanner(file);
		
		initialBlock(1, 4);
		checkBlock(tBlock1);
		checkBlock(tBlock2);
	}
	
	

}
