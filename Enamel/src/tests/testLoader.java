package tests;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enamel.Block;
import enamel.InvalidBlockException;
import enamel.InvalidCellException;
import enamel.OddSpecialCharacterException;
import enamel.Printer;
import enamel.Loader;

public class testLoader {
	File file;
	Printer printer;
	Loader loader;
	//Initiates the file for each test
	@Before
	public void setup() {
		file = new File("test.txt");
	}
	
	@After
	public void tearDown() {
		file.delete();
	}
	private void blockToBlockTest(ArrayList<Block> a, ArrayList<Block> b){
		assertEquals(a.size(), b.size());
		for (int i=0; i<a.size(); i++) {
			assertEquals(a.get(i).name, b.get(i).name);
			assertEquals(a.get(i).story, b.get(i).story);
			assertEquals(a.get(i).correctResponse, b.get(i).correctResponse);
			assertEquals(a.get(i).wrongResponse, b.get(i).wrongResponse);
			assertEquals(a.get(i).cells, b.get(i).cells);
			assertEquals(a.get(i).buttonsUsed, b.get(i).buttonsUsed);
			assertEquals(a.get(i).answer, b.get(i).answer);
		}
	}
	@Test
	public void test1() throws IOException, InvalidBlockException, InvalidCellException, OddSpecialCharacterException{
		Block tBlock1,tBlock2;

		printer = new Printer("test.txt", 1, 4);
		loader = new Loader();
		tBlock1 = new Block("name", "hi", "yes", "no", 1, 'c');
		tBlock2 = new Block("name", "hello", "yep", "nope", 2, 'd');
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock1);
		blocks.add(tBlock2);
		printer.addBlockList(blocks);
		printer.print();
		ArrayList<Block> loaded = loader.start(file);
		blockToBlockTest(blocks, loaded);
		
	}
}
