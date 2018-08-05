package enamel;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Loader {
	
	// basic char used to inicailize a blank block
	char a = "a".charAt(0);
	
	Block holdOn;
	ArrayList<Block> blocklist = new ArrayList<Block>();
	ArrayList<String> buttonOrder = new ArrayList<String>();
	String stringBasedBoolean = "story";
	Boolean inText = false;
	Boolean initialBlock = true;
	
	// the file scanners we shall use.
	private Scanner fileScanner;
	private Scanner nextLineCheck; 
	

	public Loader() {
	try {
		 holdOn = new Block(" ", " ", " ", " ", 1, a, 1);
		}
		catch (InvalidBlockException e) {
			e.printStackTrace(); 
			System.out.println(e.getMessage());
		}
	
	
	}
	
	public ArrayList<Block> start(File read) throws EOFException {
		String fileLine, lineAfter ="";
		try {
			fileScanner = new Scanner(read);
			nextLineCheck = new Scanner(read);
		}
		catch(FileNotFoundException e){
			 e.printStackTrace(); 
			 System.out.println(e.getMessage());
		}
		
		// there are a few lines of text that need to be skipped on every text file
		if (fileScanner.hasNextLine()&&nextLineCheck.hasNextLine()) {
			
			// this keeps nextLineCheck, one line ahead of fileScanner
			nextLineCheck.nextLine();
			for (int i=0; i<4; i++) {
				if (fileScanner.hasNextLine()&&nextLineCheck.hasNextLine()) {
					nextLineCheck.nextLine();
					fileScanner.nextLine();
				}
				else {
					throw new EOFException(); // this may be over kill
				}
			}	
		}
		else {
			throw new EOFException(); // this may be over kill
		}
		
		
		// loops the file scanners through every line of text
		while (fileScanner.hasNextLine()) {
			fileLine = fileScanner.nextLine();
			if (nextLineCheck.hasNextLine()) {
				lineAfter = nextLineCheck.nextLine();
			} // this may require EOF exception
			interpretLine(fileLine, lineAfter);
		}
		blocklist.add(new Block(holdOn)); // last block of file
		fileScanner.close();
		nextLineCheck.close();
		
		return blocklist;
	}
	
	private void interpretLine(String line, String nextLine) {
	
		if(line.equals("")){ //This my not be correct to it's intended use
			if (nextLine.equals("")) {
				nextLine = "Space Skiped";
				return;
			}
			
			else if(initialBlock) {
				blockClear();
				setName(nextLine.substring(2));
				initialBlock = false;
				

			}
			
			else {
				blocklist.add(new Block(holdOn));
				stringBasedBoolean = "story";
				inText = false;
				blockClear();
				buttonOrder.clear();
				setName(nextLine.substring(2));
			}
			
		}
	
		else if(line.length() >= 7 && line.substring(0, 7).equals("/~JUMPP")) {
			
			inText = false;
			String param = line.substring(2);
			if (nextLine.length() >= 8 && nextLine.substring(0, 8).equals("/~sound:")) {
				if (nextLine.substring(8).equals("correct.wav")) { // substring at 2 maybe wrong
					setAnswer(buttonOrder.indexOf(param));
				}
				
			}
		}
		
		else if(line.length() >= 14 && line.substring(0, 14).equals("/~skip-button:")) {
			
			String[] param;
			param = line.substring(14).split("\\s"); // possible mistake
			
			buttonOrder.add(param[1]);
			
			if (nextLine.length() >= 14 ) {
				// possible issues with this condition 
				if(!(nextLine.substring(0, 14).equals("/~skip-button:"))) {	
					setButtonsUsed(param[0]);
				}
			}
			
			else {
				setButtonsUsed(param[0]);
			}
			inText = false;
		}
	
		else if (line.length() >= 14 && line.substring(0, 14).equals("/~disp-string:")) {
			if(inText) {
				String[] param = line.split("\\s");
				// need to double check which special characters are for which functions
				switchAdd("*"+param[1]+"*");
			}
			else {
				setCell(line.substring(14));
				inText = true;
			}
		}
		/**
		else if(line.length() >= 8 && line.substring(0, 8).equals("/~button")) {
			String[] param = line.substring(8).split("\\s"); // this may not be the appropriate format
			
			if (nextLine.length() >= 7 && nextLine.substring(0, 7).equals("/~sound")) {
				if (nextLine.substring(7).equals("correct.wav")) { // another instance of " correct.wav" maybe incorrect
					setAnswer(buttonOrder.indexOf(param[1]));
				}
			}
		}
			**/
		else if(line.length() >= 8 && line.substring(0, 8).equals("/~sound:")) {
		
			if(inText) {
				String param = line.substring(8);
				// need to double check which special characters are for which functions
				switchAdd("<"+param+">");
			}
			else {
							// space my be a problem
				if (line.substring(8).equals("correct.wav")) {
					stringBasedBoolean = "correct";
				
				}
				else if(line.substring(8).equals("wrong.wav")) {
					stringBasedBoolean = "wrong";
				}
				inText = true;
			}		

		}
		
		
		// may need to change, this is so that the line which don't hold info on the blocks,
		// don't get added as text.
		
		else if(line.length() >= 2 && line.substring(0, 2).equals("/~")) {
			
		}
		
		else {
			switchAdd(line);
			
		}
		
	}
	
	
	private void setName(String n) {
		holdOn.name = n;
	}
	private void addStory(String s) {
		holdOn.story +=s;
	}
	
	private void addCorrect(String c) {
		holdOn.correctResponse += c;
	}

	private void addWrong(String w) {
		holdOn.wrongResponse += w;
	}

	private void setAnswer(int a) {
		holdOn.answer = a+1;
	}

	private void setCell(String c) {
		holdOn.cells = c;
	}

	private void setButtonsUsed(String b) {
		
		holdOn.buttonsUsed = Integer.valueOf(b);
	}
	
	private void switchAdd(String param) {
		switch(stringBasedBoolean) {
		case "story":
			addStory(param);
			break;
		case "correct":
			addCorrect(param);
			break;
		case "wrong":
			addWrong(param);
			break;
		default:
			System.out.println("Error with stringBasedBoolean. Unexpected value: "+stringBasedBoolean);
			break;
		}
	}
	public void clear() {
		blocklist.clear();
		blockClear();
		buttonOrder.clear();
		
	}
	
	private void blockClear() {
		holdOn.name = "";
		holdOn.story = "";
		holdOn.answer = 1;
		holdOn.correctResponse = "";
		holdOn.wrongResponse = "";
		holdOn.cells = "a";
		holdOn.buttonsUsed = 1;
	}
	
	

}
