package enamel;

public class Block {
	
	String premise;
	String correctResponse;
	String wrongResponse;
	int answer;
	char letter;
	int buttonsUsed;
	
	public Block(String prem, String corr, String wrong, int ans, char letter, int buttonsUsed) {
		premise = prem;
		correctResponse = corr;
		wrongResponse = wrong;
		answer = ans;
		this.letter = letter;
	}
}
