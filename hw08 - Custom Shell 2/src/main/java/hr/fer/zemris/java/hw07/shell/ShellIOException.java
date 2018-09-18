package hr.fer.zemris.java.hw07.shell;

/**
 * The Class which represents an IO exception that happened in the shell.
 */
@SuppressWarnings("serial")
public class ShellIOException extends RuntimeException {

	/**
	 * Instantiates a new shell IO exception.
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Instantiates a new shell IO exception.
	 *
	 * @param message of the exception
	 * @param cause of the exception
	 * @param enableSuppression if the suppression should be enabled
	 * @param writableStackTrace the writable stack trace
	 */
	public ShellIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new shell IO exception.
	 *
	 * @param message of the exception
	 * @param cause of the exception
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new shell IO exception.
	 *
	 * @param message of the exception
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new shell IO exception.
	 *
	 * @param cause of the exception
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
}
