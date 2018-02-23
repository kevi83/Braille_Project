package enamel;

/**
 * Exception thrown when invalid input is given to the block constructor. 
 * 
 * @author Connor
 *
 */
@SuppressWarnings("serial")
public class InvalidBlockException extends Exception{

	public InvalidBlockException() {
		
	}
	
	public InvalidBlockException(String msg) {
		super(msg);
	}

}
