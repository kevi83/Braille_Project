package enamel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader2 {
	
	static BrailleInterpreter interpreter = new BrailleInterpreter();

	public static ArrayList<Block> load(File file) throws FileNotFoundException, CorruptFileException, InvalidBlockException {
		
		Scanner reader = new Scanner(file);
		String init = loadInit(reader);
		ArrayList<Block> result = new ArrayList<Block>();
		
		while(true) {
			try {
				Block current = loadBlock(reader);
				result.add(current);
			}
			catch(CorruptFileException e) {
				break;
			}
		}
		
		return result;
	}
	
	private static Block loadBlock(Scanner reader) throws InvalidBlockException, CorruptFileException {
		
		//Block(String name, String story, String correct, String wrong, int answer, String cells, int buttonsUsed)
		String name = null;
		String story = null;
		String correct = null;
		String wrong = null;
		int answer = -1;
		String cells = null;
		int buttonsUsed = 0;
		boolean nextCorrect = false;
		
		while(reader.hasNextLine()) {
			String line = read(reader.nextLine());
			if(!line.equals("Empty%") && !line.equals("%ENDD")) {
				
				String[] format = line.split("%");
				if(format.length != 2) throw new CorruptFileException();
				
				String lineType = format[0];
				String data = format[1];
				
				if(lineType.equals("Config")) {
					
					if(data.equals("disp-clearAll"));
					else if(data.equals("repeat"));
					else if(data.equals("endrepeat"));
					else if(data.equals("user-input"));
					else if(data.contains("JUMPP") && !data.contains("button")) {
						int answerNumber = Integer.parseInt(data.substring(5));
						if(answerNumber >= buttonsUsed) buttonsUsed = answerNumber;
					}
					else if(data.contains(":")) {

						String[] colonFormat = data.split(":");
						if(colonFormat.length != 2) throw new CorruptFileException();
						
						String colonLineType = colonFormat[0];
						String colonData = colonFormat[1];
						
						if(colonLineType.equals("disp-cell-pins")) {
							
							String[] pinFormat = colonData.split(" ");
							if(pinFormat.length != 2) throw new CorruptFileException();
							
							int pinLocation = Integer.parseInt(pinFormat[0]);
							char pinSetting = interpreter.getChar(pinFormat[1]);

							if(cells == null) cells = "" + pinSetting;
							else if(pinLocation == cells.length()) cells += String.valueOf(pinSetting);
						}
						if(colonLineType.equals("disp-string")) {
							cells = colonData;
						}
						
						if(colonLineType.equals("repeat-button"));
						else if(colonLineType.equals("skip-button")) {
							
							String[] skipFormat = colonData.split(" ");
							if(skipFormat.length != 2) throw new CorruptFileException();
						}
						else if(colonLineType.equals("sound")) {
							if(colonData.equals("correct.wav")) {
								nextCorrect = true;
								answer = buttonsUsed;
							}
							else if(colonData.equals("wrong.wav")) nextCorrect = false;
						}						
					}
					else {
						if(name == null) name = data;
						else throw new CorruptFileException("Misunderstood Config Data: " + data);
					}
					
				}
				else if(lineType.equals("Plain")) {
					if(story == null) story = data;
					if(nextCorrect) {
						correct = data;
						answer = buttonsUsed;
					}
					else wrong = data;
				}
				
			}
			else break;
		}
				
		try {
			return new Block(name, story, correct, wrong, answer, cells, buttonsUsed);
		}
		catch(InvalidBlockException e) {
			throw new CorruptFileException(e.getMessage());		
		}
		catch(NullPointerException e) {
			throw new CorruptFileException(e.getMessage());		
		}
	}
	
	private static String loadInit(Scanner reader) throws CorruptFileException {
		
		String i;
		String result = "";
		
		if(reader.hasNextLine()) {
			String[] split = read(reader.nextLine()).split("%");
			if(split.length == 2) {
				i = split[1];
				if(i.length() > 3 && i.substring(0, 4).equals("Cell")) result = "Cells%" + i.split(" ")[1];
				else throw new CorruptFileException("Initial Block is Corrupted");
			}
			else throw new CorruptFileException("Initial Block is Corrupted");
		}
		else throw new CorruptFileException("Initial Block is Corrupted");
		
		if(reader.hasNextLine()) {
			String[] split = read(reader.nextLine()).split("%");
			if(split.length == 2) {
				i = split[1];
				if(i.length() > 5 && i.substring(0, 6).equals("Button")) result += "%Buttons%" + i.split(" ")[1];
				else throw new CorruptFileException("Initial Block is Corrupted");
			}
			else throw new CorruptFileException("Initial Block is Corrupted");
		}
		else throw new CorruptFileException("Initial Block is Corrupted");
		
		if(reader.hasNextLine()) {
			i = read(reader.nextLine());
			if(i.length() == 6 && i.substring(0, 6).equals("Empty%"));
			else throw new CorruptFileException("Initial Block is Corrupted");
		}
		else throw new CorruptFileException("Initial Block is Corrupted");
		
		if(reader.hasNextLine()) {
			i = read(reader.nextLine());
			if(i.length() == 7 && i.substring(0, 7).equals("Pause%1"));
			else throw new CorruptFileException("Initial Block is Corrupted");
		}
		else throw new CorruptFileException("Initial Block is Corrupted");
		
		if(reader.hasNextLine()) {
			i = read(reader.nextLine());
			if(i.length() == 6 && i.substring(0, 6).equals("Empty%"));
			else throw new CorruptFileException("Initial Block is Corrupted");
		}
		else throw new CorruptFileException("Initial Block is Corrupted");
		
		return result;
	}

	
	private static String read(String line) {
		
		if(isEmpty(line)) return "Empty%" + line;
		
		else if(isConfig(line)) {
			line = line.substring(2);
			if(line.length() > 4 && line.substring(0, 5).equals("pause")) return "Pause%" + line.split(":")[1];
			return "Config%" + line;
		}
		
		else {
			
			return "Plain%" + line;
		}
	}
	
	private static boolean isEmpty(String string) {
		return string.equals("");
	}
	
	private static boolean isConfig(String string) {
		return string.substring(0, 2).equals("/~");
	}
	
	public static void main(String[] args) throws IOException, OddSpecialCharacterException, InvalidBlockException, InvalidCellException, CorruptFileException {
		print();
	}
	
	static void print() throws OddSpecialCharacterException, InvalidBlockException, InvalidCellException, IOException, CorruptFileException {
		Printer printer = new Printer("test.txt", 3, 4);
		Block tBlock1 = new Block("name", "hi", "yes", "no", 1, "wow", 4);
		Block tBlock2 = new Block("name", "hello", "yep", "nope", 2, 'd');
		ArrayList<Block> blocks = new ArrayList<>();
		blocks.add(tBlock1);
		blocks.add(tBlock2);
		printer.addBlockList(blocks);
		printer.print();
		Loader2.load(new File("test.txt"));
	}
}
