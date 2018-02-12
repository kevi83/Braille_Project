package enamel;

public class Block {
	
	public String premise;
	public String correctResponse;
	public String wrongResponse;
	public int answer;
	public char letter;
	public int buttonsUsed;
	
	/**
	 * 
	 * @param premise - String containing the Question / Story
	 * @param correct - String containing the message stated for the correct answer
	 * @param wrong - String containing the message stated for the wrong answer
	 * @param answer - Integer for the correct button press
	 * @param letter - Character to show up on the braille cell
	 * @param buttonsUsed - Amount of buttons used for answers for block
	 */
	public Block(String premise, String correct, String wrong, int answer, char letter, int buttonsUsed) {
		this.premise = premise;
		correctResponse = correct;
		wrongResponse = wrong;
		this.answer = answer;
		this.letter = letter;
		this.buttonsUsed = buttonsUsed;
	}
	
	/**
	 * 
	 * @param premise - String containing the Question / Story
	 * @param correct - String containing the message stated for the correct answer
	 * @param wrong - String containing the message stated for the wrong answer
	 * @param answer - Integer for the correct button press
	 * @param letter - Character to show up on the braille cell
	 */
	public Block(String premise, String correct, String wrong, int answer, char letter) {
		this(premise, correct, wrong, answer, letter, 2);
	}
}
