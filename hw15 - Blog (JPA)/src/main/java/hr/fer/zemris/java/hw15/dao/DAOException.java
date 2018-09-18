package hr.fer.zemris.java.hw15.dao;

/**
 * The Class DAOException represents an exception which happened while accessing data objects.
 */
public class DAOException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the exception message
	 * @param cause the cause of the exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the exception message
	 */
	public DAOException(String message) {
		super(message);
	}
}