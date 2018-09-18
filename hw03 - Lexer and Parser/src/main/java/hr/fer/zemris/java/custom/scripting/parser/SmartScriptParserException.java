package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class which represents an exception which happened in <code>SmartScriptParser</code>.
 * Provides two constructors: default and another one which accepts message.
 * Extends <code>RuntimeException</code> class
 * @author Alen Carin
 *
 */
@SuppressWarnings("serial")
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Default constructor.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructor. Accepts message of type String and forwards it to the super constructor
	 * @param message
	 * 			describing error that occured
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
