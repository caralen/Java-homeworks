package hr.fer.zemris.java.custom.collections;

/**
 * Exception which is thrown when somebody tries to pop() or peek() on an empty stack
 * @author Alen Carin
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor which calls the super constructor
	 */
	public EmptyStackException() {
	}
	
	/**
	 * Constructor which takes string message and passes it to the super constructor
	 * @param message which should be displayed to the user
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
