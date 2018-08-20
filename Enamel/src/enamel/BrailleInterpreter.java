package enamel;

import java.util.HashMap;

public class BrailleInterpreter {

	public HashMap<Character, String> alphabet = new HashMap<Character, String>();
	public HashMap<String, Character> translate = new HashMap<String, Character>();

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
		
		translate.put("10000000", 'a');
		translate.put("11000000", 'b');
		translate.put("10100000", 'c');
		translate.put("10011000", 'd');
		translate.put("10001000", 'e');
		translate.put("11010000", 'f');
		translate.put("11011000", 'g');
		translate.put("11001000", 'h');
		translate.put("01010000", 'i');
		translate.put("01011000", 'j');
		translate.put("10100000", 'k');
		translate.put("11100000", 'l');
		translate.put("10110000", 'm');
		translate.put("10111000", 'n');
		translate.put("10101000", 'o');
		translate.put("11110000", 'p');
		translate.put("11111000", 'q');
		translate.put("11101000", 'r');
		translate.put("01110000", 's');
		translate.put("01111000", 't');
		translate.put("10100100", 'u');
		translate.put("11100100", 'v');
		translate.put("01011100", 'w');
		translate.put("10110100", 'x');
		translate.put("10111100", 'y');
		translate.put("10101100", 'z');
		translate.put("11111111", ' ');
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
	

	public char getChar(String pins) {
		char ans = translate.get(pins);
		return ans;
	}
}
