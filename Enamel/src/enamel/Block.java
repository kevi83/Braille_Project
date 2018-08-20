package enamel;

public class Block {
	
	public String name;
	public String story;
	public String correctResponse;
	public String wrongResponse;
	public int answer;
	public char letter;
	public String cells;
	public int buttonsUsed;
	
	/**
	 * (Old) Main Constructor
	 * 
	 * @param story - String containing the Question / Story
	 * @param correct - String containing the message stated for the correct answer
	 * @param wrong - String containing the message stated for the wrong answer
	 * @param answer - Integer for the correct button press
	 * @param letter - Character to show up on the braille cell
	 * @param buttonsUsed - Amount of buttons used for answers for block
	 * @throws InvalidBlockException 
	 */
	public Block(String name, String story, String correct, String wrong, int answer, char letter, int buttonsUsed) throws InvalidBlockException {
		this(name, story, correct, wrong, answer, Character.toString(letter), buttonsUsed);
	}
	
	/**
	 * (Old) Simplified Constructor - Assumes buttons used to be 2.
	 * 
	 * 
	 * @param story - String containing the Question / Story
	 * @param correct - String containing the message stated for the correct answer
	 * @param wrong - String containing the message stated for the wrong answer
	 * @param answer - Integer for the correct button press
	 * @param letter - Character to show up on the braille cell
	 * @throws InvalidBlockException 
	 */
	public Block(String name, String story, String correct, String wrong, int answer, char letter) throws InvalidBlockException {
		this(name, story, correct, wrong, answer, letter, 2);
	}
	
	/**
	 * String constructor - Old constructor modified to take strings instead of single characters.
	 * 
	 * @param name - Name of the section
	 * @param story - String containing section's story
	 * @param correct - String containing message for the right answer
	 * @param wrong - String containing message for wrong answer
	 * @param answer - Number for correct button
	 * @param cells - String to come up on the braille cells
	 * @param buttonsUsed - How many buttons the section uses
	 * @throws InvalidBlockException - Thrown when invalid paramaters are passed to the constructor
	 */
	public Block(String name, String story, String correct, String wrong, int answer, String cells, int buttonsUsed) throws InvalidBlockException {
		
		if(name.equals("")) throw new InvalidBlockException("noname");
		if(story.equals("")) throw new InvalidBlockException("Story field is empty");
		if(answer > buttonsUsed) throw new InvalidBlockException("Answer button is outside the range of available buttons");
		if(answer <= 0) throw new InvalidBlockException("Answer button can't have a negative number");
		if(buttonsUsed <= 0) throw new InvalidBlockException("Buttons used can't have a negative number");
		if(name == null || story == null || correct == null || wrong == null || cells == null) throw new InvalidBlockException("Null text");
		
		this.name = name;
		this.story = story;
		correctResponse = correct;
		wrongResponse = wrong;
		this.answer = answer;
		this.cells = cells;
		this.buttonsUsed = buttonsUsed;
	}
	
	
	
	/**
	 * Copy constructor 
	 * Author - Micah Arndt
	 * @param b - Block object to be copied
	 */
	
	public Block(Block b) {
		this.name = b.name;
		this.story = b.story;
		correctResponse = b.correctResponse;
		wrongResponse = b.wrongResponse;
		this.answer = b.answer;
		this.cells = b.cells;
		this.buttonsUsed = b.buttonsUsed;
	}
	
}
