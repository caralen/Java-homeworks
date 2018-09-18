package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class Home implements IWebWorker. It is mapped to /index2.html path.
 * It represents a worker on the homepage of the web application.
 */
public class Home implements IWebWorker {

	/**
	 * Reads persistent parameter which is mapped to key "bgcolor" in context in persistentParameters map.
	 * If a value is found it is set as temporary parameter, else the default value is set to temporary parameter.
	 * After the background color processing method disPatchRequest is called on the dispatcher from the context.
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String background = context.getPersistentParameters().get("bgcolor");
		
		if(background != null) {
			context.setTemporaryParameter("background", background);
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		
		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}

}
