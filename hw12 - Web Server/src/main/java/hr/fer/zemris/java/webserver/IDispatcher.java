package hr.fer.zemris.java.webserver;

/**
 * The Interface IDispatcher.
 */
public interface IDispatcher {
	
	/**
	 * Dispatches request.
	 *
	 * @param urlPath is a string representation of the url path to a file
	 * @throws Exception the exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
