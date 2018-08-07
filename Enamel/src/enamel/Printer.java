package enamel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Printer {
	
	private File file;
	private FileWriter fileWriter;
	private PrintWriter printWriter;
	private ArrayList<String> lines = new ArrayList<>();
	private int cellsAmt;
	private ArrayList<Block> blocks;
	private int buttons;
	
	/**
	 * Full Constructor
	 * 
	 * @param fileName - Name of the file the scenario will be saved as
	 * @param cells - Number of cells the machine used has <b>available</b>
	 * @param buttonsAvailable - Number of buttons available on the machine
	 * @throws IOException - Required by Java
	 * @throws OddSpecialCharacterException 
	 * @throws InvalidBlockException 
	 */
	public Printer(String fileName, int cells, int buttonsAvailable) throws IOException, OddSpecialCharacterException, InvalidBlockException {
		file = new File(fileName);
		fileWriter = new FileWriter(fileName);
		printWriter = new PrintWriter(fileWriter);
		if(!file.exists()) file.createNewFile();
		initialBlock(cells, buttonsAvailable);
		this.cellsAmt = cells;
		this.buttons = buttonsAvailable;
	}
	
	/**
	 * Simplified constructor for 1 Cell, 4 Buttons
	 * 
	 * @param fileName - Name of the file the scenario will be saved as
	 * @throws IOException - Required by Java
	 * @throws OddSpecialCharacterException 
	 * @throws InvalidBlockException 
	 */
	public Printer(String fileName) throws IOException, OddSpecialCharacterException, InvalidBlockException {
		this(fileName, 1, 4);
	}
	
	/**
	 * Method used to add blocks to the text file
	 * 
	 * @param block - Single block to be printed to the text file. 
	 * @throws InvalidCellException 
	 * @throws OddSpecialCharacterException 
	 * @throws InvalidBlockException 
	 */
	public void addBlock(Block block) throws OddSpecialCharacterException, InvalidBlockException {
		boolean repeat = (block.buttonsUsed < this.buttons);
		addSectionName(block.name);
		clearPins();
		if(repeat) addRepeat();
		displayString(block.cells);
		addSpoken(block.story);
		if(repeat) {
			endRepeat();
			repeatButton(this.buttons);
		}
		addInputBlock(block.buttonsUsed);
		for(int i = 1; i <= block.buttonsUsed; i++) {
			addResponse(block, (block.answer == i) ? block.correctResponse : block.wrongResponse, i, (block.answer == i));
		}
		newLine();
	}
	
	public void addBlockList(ArrayList<Block> blocks) throws OddSpecialCharacterException, InvalidBlockException {
		
		this.blocks = blocks;
		for(Block block : blocks) {
			addBlock(block);
		}
	}
	
	/**
	 * Prints the blocks that have been sent to the printer object to the file previously provided
	 * 
	 * @throws IOException - Required by Java
	 */
	public void print() throws IOException {
		for(String line : lines) {
			printWriter.print(line);
		}
		printWriter.flush();
		printWriter.close();
	}
	
	//Adds line starting with /~, string, ends with newline character
	private void addConfig(String line) {
		lines.add("/~" + line + "\n");
	}
	
	/**
	 * Recursive algorithm paired with arrowTags and asteriskTags
	 * 
	 * If the input string doesn't have asterisks or arrow brackets, it
	 * prints it as spoken text. If not, it sends the line to arrowTags or
	 * asteriskTags to be parsed accordingly. Those methods make recursive 
	 * calls back to addSpoken
	 * 
	 * @param line
	 * @throws OddSpecialCharacterException
	 * @throws InvalidBlockException 
	 */
	private void addSpoken(String line) throws OddSpecialCharacterException, InvalidBlockException {
		
		if(!line.contains("*") && !line.contains("<") && !line.contains(">")) {
			lines.add(line + "\n");
		}
		
		else if(line.contains("<") || line.contains(">")) {
			arrowTags(line);
		}
		
		else {
			asteriskTags(line);
		}
		
	}
	
	/**
	 * Recursive method used by addSpoken, acts as one of the recursive cases
	 * 
	 * @param line
	 * @throws OddSpecialCharacterException
	 * @throws InvalidBlockException 
	 */
	private void arrowTags(String line) throws OddSpecialCharacterException, InvalidBlockException {
		
		if(!line.contains("<") && line.contains(">")) throw new OddSpecialCharacterException();
		if(line.contains("<") && !line.contains(">")) throw new OddSpecialCharacterException();
		
		String testLine = line;
		
		while(testLine.contains("<") || testLine.contains(">")) {
			if(!testLine.contains("<") && testLine.contains(">")) throw new OddSpecialCharacterException();
			if(testLine.contains("<") && !testLine.contains(">")) throw new OddSpecialCharacterException();
			testLine = testLine.replaceFirst("<", "");
			testLine = testLine.replaceFirst(">", "");
		}
		
		String[] split = line.split("<");
		
		addSpoken(split[0]);
		
		for(int i = 1; i < split.length; i++) {
			
			String[] superSplit = split[i].split(">");
			if(superSplit.length > 2 || superSplit[0].contains("\\*")) throw new OddSpecialCharacterException();
			
			addSound(superSplit[0].trim());
			if(superSplit.length == 2) addSpoken(superSplit[1]);
		}
		
	}
	
	/**
	 * Recursive method used by addSpoken, acts as a recursive case
	 * 
	 * @param line
	 * @throws OddSpecialCharacterException
	 * @throws InvalidBlockException 
	 */
	private void asteriskTags(String line) throws OddSpecialCharacterException, InvalidBlockException {
		
		String[] split = line.split("\\*");
		
		if(split.length % 2 == 0 && split.length > 2) throw new OddSpecialCharacterException();
		
		for(int i = 0; i < split.length; i++) {
			if(i % 2 == 0) addSpoken(split[i]);
			else {
				if(split[i].contains("<") || split[i].contains(">")) throw new OddSpecialCharacterException();
				displayString(split[i]);
			}
		}
		
	}
	
	//Adds empty line (Like hitting enter)
	private void newLine() {
		lines.add("\n");
	}
	
	//Inserts initial block to file declaring cells and buttons on machine
	//buttonsAvailable refers to how many buttons are on the simulator / machine
	private void initialBlock(int cells, int buttonsAvailable) throws OddSpecialCharacterException, InvalidBlockException {
		addSpoken("Cell " + cells);
		addSpoken("Button " + buttonsAvailable);
		newLine();
		addPause(1);
		newLine();
	}
	
	//Standard line used at the beginning of a block	
	private void clearPins() {
		addConfig("disp-clearAll");
	}
	
	/*Sets pins for the requested character
	private void setPins(char letter) throws InvalidCellException {
		addConfig("disp-cell-pins:0 " + interpreter.getPins(letter));
	}
	*/
	
	//Adds sound for the response depending on if the response is correct
	private void addAnswerSound(boolean correct) {
		if(correct) addSound("correct.wav");
		else addSound("wrong.wav");
	}
	
	private void addSound(String fileName) {
		addConfig("sound:" + fileName);
	}
	
	/*Skips to NEXTT, used if another block will follow the current block. used at the end
	private void addSkip() {
		addConfig("skip:NEXTT");
	}
	*/
	
	private void addPause(int length) {
		addConfig("pause:" + length);
	}
	
	/*
	private void addNext() {
		addConfig("NEXTT");
	}
	*/
	
	private void addSectionName(String sectionName) {
		addConfig(sectionName);
	}
	
	private void nextSection(Block block) {
		
		int index = blocks.indexOf(block);
		
		if(index == blocks.size() - 1) return;
		
		else {
			addConfig("skip:" + blocks.get(index + 1).name.toUpperCase());
		}
	}
	
	private void repeatButton(int button) {
		addConfig("repeat-button:" + button);
	}
	
	private void addRepeat() {
		addConfig("repeat");
	}
	
	private void endRepeat() {
		addConfig("endrepeat");
	}
	
	/*
	private void resetButtons() {
		addConfig("reset-buttons");
	}
	*/
	
	private void displayString(String in) throws InvalidBlockException {
		if(in.length() <= this.cellsAmt) addConfig("disp-string:" + in);
		else {
			throw new InvalidBlockException("The length of the string is too long for the Braille Cells to represent");
		}
		
	}
	
	//Input declaring portion of a block
	//NOTE: buttonsUsed refers to the buttons being used for the given scenario / block
	private void addInputBlock(int buttonsUsed) {
		for(int i = 1; i <= buttonsUsed; i++) {
			addConfig("skip-button:" + i + " " + "JUMPP" + i);
		}
		addConfig("user-input");
	}
	
	//Spoken is the spoken response, button is the button that creates the response
	//NOTE: button refers to the number displayed on the box / simulation. 1 = 1
	private void addResponse(Block block, String spoken, int button, boolean correct) throws OddSpecialCharacterException, InvalidBlockException {
		addConfig("JUMPP" + button);
		addAnswerSound(correct);
		addSpoken(spoken);
		nextSection(block);
	}
	
	

}
