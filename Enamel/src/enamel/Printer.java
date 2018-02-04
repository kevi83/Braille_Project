package enamel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Printer {
	
	File file;
	FileOutputStream output;
	ArrayList<String> lines = new ArrayList<>();
	BrailleInterpreter interpreter = new BrailleInterpreter();
	String[] buttonLabels = {"ONEE", "TWOO", "THREEE", "FOURR"};
	
	/**
	 * 
	 * @param fileName - Name of the file the scenario will be saved as
	 * @param cells - Number of cells the machine used has <b>available</b>
	 * @param buttonsAvailable - Number of buttons available on the machine
	 * @throws IOException - Required by Java
	 */
	public Printer(String fileName, int cells, int buttonsAvailable) throws IOException {
		file = new File(fileName);
		output = new FileOutputStream(file);
		if(!file.exists()) file.createNewFile();
		initialBlock(cells, buttonsAvailable);
	}
	
	/**
	 * 
	 * @param block - Single block to be printed to the text file. 
	 */
	public void addBlock(Block block) {
		clearPins();
		setPins(block.letter);
		addSpoken(block.premise);
		addInputBlock(block.buttonsUsed);
		addResponse(block.correctResponse, 1, true);
		addResponse(block.wrongResponse, 2, false);
		newLine();
	}
	
	public void print() throws IOException {
		for(String line : lines) {
			byte[] temp = line.getBytes();
			output.write(temp);
		}
	}
	
	//Adds line starting with /~, string, ends with newline character
	private void addConfig(String line) {
		lines.add("/~" + line + "\n");
	}
	
	//Adds line with string, ends with newline character
	private void addSpoken(String line) {
		lines.add(line + "\n");
	}
	
	//Adds empty line (Like hitting enter)
	private void newLine() {
		lines.add("\n");
	}
	
	//Inserts initial block to file declaring cells and buttons on machine
	//buttonsAvailable refers to how many buttons are on the simulator / machine
	private void initialBlock(int cells, int buttonsAvailable) {
		addSpoken("Cell " + cells);
		addSpoken("Button " + buttonsAvailable);
		newLine();
	}
	
	//Standard line used at the beginning of a block
	private void clearPins() {
		addConfig("disp-cell-clear:0");
	}
	
	//Sets pins for the requested character
	private void setPins(char letter) {
		addConfig("disp-cell-pins:0 " + interpreter.getPins(letter));
	}
	
	//Adds sound for the response depending on if the response is correct
	private void addSound(boolean correct) {
		if(correct) addConfig("correct.wav");
		else addConfig("wrong.wav");
	}
	
	//Skips to NEXTT, used if another block will follow the current block. used at the end
	private void addSkip() {
		addConfig("skip:NEXTT");
	}
	
	//inputs NEXTT
	private void addNext() {
		addConfig("NEXTT");
	}
	
	//Input declaring portion of a block
	//NOTE: buttonsUsed refers to the buttons being used for the given scenario / block
	private void addInputBlock(int buttonsUsed) {
		for(int i = 0; i < buttonsUsed; i++) {
			addConfig("skip-button:" + i + " " + buttonLabels[i]);
		}
		addConfig("user-input");
	}
	
	//Spoken is the spoken response, button is the button that creates the response
	//NOTE: button refers to the number displayed on the box / simulation. 1 = 1
	private void addResponse(String spoken, int button, boolean correct) {
		addConfig(buttonLabels[button-1]);
		addSound(correct);
		addSpoken(spoken);
		addSkip();
	}
	
	

}
