package enamel;

public class Block {
	
	public String name;
	public String premise;
	public String correctResponse;
	public String wrongResponse;
	public int answer;
	public char letter;
	public int buttonsUsed;
	
	/**
	 * Main Constructor
	 * 
	 * @param premise - String containing the Question / Story
	 * @param correct - String containing the message stated for the correct answer
	 * @param wrong - String containing the message stated for the wrong answer
	 * @param answer - Integer for the correct button press
	 * @param letter - Character to show up on the braille cell
	 * @param buttonsUsed - Amount of buttons used for answers for block
	 * @throws InvalidBlockException 
	 */
	public Block(String name, String premise, String correct, String wrong, int answer, char letter, int buttonsUsed) throws InvalidBlockException {
		
		//Throws an exception if any of the 3 conditions are met
		if(name.equals("") || premise.equals("") || answer > buttonsUsed) throw new InvalidBlockException();
		
		this.name = name;
		this.premise = premise;
		correctResponse = correct;
		wrongResponse = wrong;
		this.answer = answer;
		this.letter = letter;
		this.buttonsUsed = buttonsUsed;
	}
	
	/**
	 * Simplified Constructor - Assumes buttons used to be 2.
	 * 
	 * @param premise - String containing the Question / Story
	 * @param correct - String containing the message stated for the correct answer
	 * @param wrong - String containing the message stated for the wrong answer
	 * @param answer - Integer for the correct button press
	 * @param letter - Character to show up on the braille cell
	 * @throws InvalidBlockException 
	 */
	public Block(String name, String premise, String correct, String wrong, int answer, char letter) throws InvalidBlockException {
		this(name, premise, correct, wrong, answer, letter, 2);
	}
}
