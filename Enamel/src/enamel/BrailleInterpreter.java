package enamel;

import java.util.HashMap;

public class BrailleInterpreter {

	public HashMap<Character, String> alphabet = new HashMap<Character, String>();

	public BrailleInterpreter() {
		alphabet.put('a', "10000000");
		alphabet.put('b', "11000000");
		alphabet.put('c', "10100000");
		alphabet.put('d', "10011000");
		alphabet.put('e', "10001000");
		alphabet.put('f', "11010000");
		alphabet.put('g', "11011000");
		alphabet.put('h', "11001000");
		alphabet.put('i', "01010000");
		alphabet.put('j', "01011000");
		alphabet.put('k', "10100000");
		alphabet.put('l', "11100000");
		alphabet.put('m', "10110000");
		alphabet.put('n', "10111000");
		alphabet.put('o', "10101000");
		alphabet.put('p', "11110000");
		alphabet.put('q', "11111000");
		alphabet.put('r', "11101000");
		alphabet.put('s', "01110000");
		alphabet.put('t', "01111000");
		alphabet.put('u', "10100100");
		alphabet.put('v', "11100100");
		alphabet.put('w', "01011100");
		alphabet.put('x', "10110100");
		alphabet.put('y', "10111100");
		alphabet.put('z', "10101100");
		alphabet.put(' ', "11111111");
	}
	
	/**
	 * Takes a character parameter and turns it to a string of Binary that tells the Braille 
	 * cell what to output for a given letter.
	 * 
	 * @param letter - Letter to be translated to braille
	 * @return Binary string corresponding to braille pins for given letter
	 * @throws InvalidCellException - If the given letter isn't in registered in the interpreter
	 */
	public String getPins(char letter) throws InvalidCellException {
		String ans = alphabet.get(letter);
		if(ans == null) throw new InvalidCellException();
		return ans;
	}
}
