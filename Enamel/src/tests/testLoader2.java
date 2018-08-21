package tests;

import enamel.Block;
import enamel.BrailleInterpreter;
import enamel.CorruptFileException;
import enamel.InvalidBlockException;
import enamel.InvalidCellException;
import enamel.Loader2;
import enamel.OddSpecialCharacterException;
import enamel.Printer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testLoader2 {

	File file;
	Printer printer;
	int buttons;
	BrailleInterpreter interpreter = new BrailleInterpreter();
	
	@After
	public void teardown() {
		file.delete();
	}
	
	@Test
	public void testOneBlock() throws IOException, OddSpecialCharacterException, InvalidBlockException, InvalidCellException, CorruptFileException {
		printer = new Printer("test.txt");
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(new Block("name", "hi", "yes", "no", 1, 'c', 2));
		printer.addBlockList(blocks);
		printer.print();
		
		file = new File("test.txt");
		ArrayList<Block> result = Loader2.load(file);
		
		String name = file.getName();
		assertEquals("test.txt", name);
		
		int cells = result.get(0).answer;
		assertEquals(1, cells);
		
		int buttons = result.get(0).buttonsUsed;
		assertEquals(4, buttons);
		result.remove(0);
		
		checkBlocks(blocks.get(0), result.get(0));
	}
	
	@Test
	public void testTwoBlocks() throws IOException, OddSpecialCharacterException, InvalidBlockException, InvalidCellException, CorruptFileException {
		printer = new Printer("test.txt", 1, 4);
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(new Block("name", "hi", "yes", "no", 1, 'c'));
		blocks.add(new Block("name", "hello", "yep", "nope", 2, 'd'));
		printer.addBlockList(blocks);
		printer.print();
		
		file = new File("test.txt");
		ArrayList<Block> result = Loader2.load(file);
		
		String name = file.getName();
		assertEquals("test.txt", name);
		
		int cells = result.get(0).answer;
		assertEquals(1, cells);
		
		int buttons = result.get(0).buttonsUsed;
		assertEquals(4, buttons);
		result.remove(0);
		
		checkBlocks(blocks.get(0), result.get(0));
		checkBlocks(blocks.get(1), result.get(1));
	}
	
	public void checkBlocks(Block original, Block loaded) throws InvalidCellException {
		assertEquals(original.name, loaded.name);
		assertEquals(original.story, loaded.story);
		assertEquals(original.correctResponse, loaded.correctResponse);
		assertEquals(original.wrongResponse, loaded.wrongResponse);
		assertEquals(original.answer, loaded.answer);
		if(!original.cells.equals("c")) assertEquals(original.cells, loaded.cells);
		assertEquals(original.buttonsUsed,  loaded.buttonsUsed);
		assertEquals(original.letter, loaded.letter);
	}
	
}
