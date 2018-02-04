package enamel;

public class Block {
	
	public String premise;
	public String correctResponse;
	public String wrongResponse;
	public int answer;
	public char letter;
	public int buttonsUsed;
	
	public Block(String prem, String corr, String wrong, int ans, char letter, int buttonsUsed) {
		premise = prem;
		correctResponse = corr;
		wrongResponse = wrong;
		answer = ans;
		this.letter = letter;
	}
}
