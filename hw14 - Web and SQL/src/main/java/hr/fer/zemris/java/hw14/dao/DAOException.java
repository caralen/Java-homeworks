package hr.fer.zemris.java.hw14.dao;

/**
 * The Class DAOException extends <code>RuntimeException</code>.
 */
public class DAOException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new DAO exception.
	 */
	public DAOException() {
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the exception message
	 * @param cause the exception cause
	 * @param enableSuppression the enable suppression flag
	 * @param writableStackTrace the writable stack trace flag
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the exception message
	 * @param cause the exception cause
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

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param cause the exception cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}