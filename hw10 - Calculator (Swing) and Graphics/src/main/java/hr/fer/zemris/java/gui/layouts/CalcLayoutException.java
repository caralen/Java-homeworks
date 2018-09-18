package hr.fer.zemris.java.gui.layouts;

/**
 * The Class CalcLayoutException which is an exception in calculator layout.
 */
public class CalcLayoutException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new calc layout exception.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Instantiates a new calc layout exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public CalcLayoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new calc layout exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new calc layout exception.
	 *
	 * @param message the message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new calc layout exception.
	 *
	 * @param cause the cause
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}

	
}
