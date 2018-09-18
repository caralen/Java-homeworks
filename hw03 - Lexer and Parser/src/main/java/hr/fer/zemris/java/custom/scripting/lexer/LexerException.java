package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class for handling exceptions caused by the Lexer class.
 * @author Alen Carin
 *
 */
@SuppressWarnings("serial")
public class LexerException extends RuntimeException {

	/**
	 * Default constructor.
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor which takes exception message and delegates it to the parent class constructor.
	 * @param message
	 * 				which will be delegated
	 */
	public LexerException(String message) {
		super(message);
	}
}
