package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class BgColorWorker implements IWebWorker. It is a web worker used for changing the current color.
 */
public class BgColorWorker implements IWebWorker {

	/**
	 * Extracts bgcolor parameter from the parameters map in RequestContext and puts it in the persistentParameters map.
	 * It calls context's write method multiple times with the html text in arguments.
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		String message;
		
		if(color != null && color.length() == 6) {
			context.setPersistentParameter("bgcolor", color);
			message = "Color updated";
		} else {
			message = "Color not updated";
		}
		
		try {
			context.write("<html><body>");
			context.write("<p>" + message + "<br>");
			context.write("<a href=\"/index2.html\">Home</a>");
			context.write("</body></html>");
		} catch (IOException ex) {
			// Log exception to servers log...
			ex.printStackTrace();
		}
		
	}

}
