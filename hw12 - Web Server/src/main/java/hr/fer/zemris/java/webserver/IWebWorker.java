package hr.fer.zemris.java.webserver;

/**
 * The Interface IWebWorker. It represents a web worker which does some action.
 */
public interface IWebWorker {
	
	/**
	 * Processes request.
	 *
	 * @param context the context
	 * @throws Exception the exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
