package tests;

import org.junit.*;
import enamel.Block;
import enamel.InvalidBlockException;

import static org.junit.Assert.*;

public class testBlock {
	
	@Test
	public void testConstructor1() throws InvalidBlockException {
		Block block = new Block("name", "story", "correct", "wrong", 1, 'c', 2);
		assertEquals("name", block.name);
		assertEquals("story", block.story);
		assertEquals("correct", block.correctResponse);
		assertEquals("wrong", block.wrongResponse);
		assertEquals(1, block.answer);
		assertEquals('c', block.letter);
		assertEquals(2, block.buttonsUsed);
	}
	
	@Test
	public void testConstructor2() throws InvalidBlockException {
		Block block = new Block("name", "story", "", "", 2, 'f', 2);
		assertEquals("name", block.name);
		assertEquals("story", block.story);
		assertEquals("", block.correctResponse);
		assertEquals("", block.wrongResponse);
		assertEquals(2, block.answer);
		assertEquals('f', block.letter);
		assertEquals(2, block.buttonsUsed);
	}
	
	@Test
	public void testConstructor3() {
		
		try {
			Block block = new Block("name", "", "", "", 1, 'f', 1);
			fail("No exception thrown for invalid block");
		}
		
		catch (InvalidBlockException e) {
					
		}
	}
	
	@Test
	public void testConstructor4() {
		
		try {
			Block block = new Block("", "prem", "", "", 1, 'f', 1);
			fail("No exception thrown for invalid block");
		}
		
		catch (InvalidBlockException e) {
					
		}
	}
	
	@Test
	public void testConstructor5() {
		
		try {
			Block block = new Block("name", "prem", "", "", 3, 'f', 1);
			fail("No exception thrown for invalid block");
		}
		
		catch (InvalidBlockException e) {
					
		}
	}
	
	@Test
	public void testSimpleConstructor() throws InvalidBlockException {
		Block block = new Block("name", "story", "correct", "wrong", 1, 'c');
		assertEquals("name", block.name);
		assertEquals("story", block.story);
		assertEquals("correct", block.correctResponse);
		assertEquals("wrong", block.wrongResponse);
		assertEquals(1, block.answer);
		assertEquals('c', block.letter);
		assertEquals(2, block.buttonsUsed);
	}

}
