package tests;

import org.junit.*;
import enamel.Block;
import static org.junit.Assert.*;

public class testBlock {
	
	@Test
	public void testConstructor() {
		Block block = new Block("premise", "correct", "wrong", 1, 'c', 2);
		assertEquals("premise", block.premise);
		assertEquals("correct", block.correctResponse);
		assertEquals("wrong", block.wrongResponse);
		assertEquals(1, block.answer);
		assertEquals('c', block.letter);
		assertEquals(2, block.buttonsUsed);
	}

}
