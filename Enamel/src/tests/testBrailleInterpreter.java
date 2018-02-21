package tests;

import org.junit.*;
import enamel.BrailleInterpreter;
import enamel.InvalidCellException;

import static org.junit.Assert.*;

public class testBrailleInterpreter {

	BrailleInterpreter brailleInt;
	
	@Before
	public void testConstructor() {
		brailleInt = new BrailleInterpreter();
	}
	
	@Test
	public void testgetPins1() throws InvalidCellException {
		assertEquals("11111111", brailleInt.getPins(' '));
	}
	
	@Test
	public void testgetPins2() {
		
		try {
			brailleInt.getPins(':');
			fail("Unregistered character, should have thrown an exception");
		}
		catch (InvalidCellException e) {}
	}
	
}
