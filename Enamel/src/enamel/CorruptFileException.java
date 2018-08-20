package enamel;

@SuppressWarnings("serial")
public class CorruptFileException extends Exception{

	public CorruptFileException() {
		
	}
	
	public CorruptFileException(String msg) {
		super(msg);
	}

}
