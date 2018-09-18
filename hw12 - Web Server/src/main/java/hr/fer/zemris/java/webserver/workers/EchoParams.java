package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class EchoParams implements IWebWorker. 
 * It writes html code for outputing a table with keys and values from the parameters given in url.
 */
public class EchoParams implements IWebWorker {

	/**
	 * Calls write method on context multiple times to write html code which generates a web page
	 * that contains a table with keys and values of the parameters given in the url.
	 */
	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		context.setStatusCode(200);
		
		Map<String, String> parameters = context.getParameters();
		
		try {
			context.write("<html><body>");
			context.write("<table>");
			context.write("<thead>");
			context.write("<tr><th>Ključ</th><th>Vrijednost</th></tr>");
			context.write("</thead>");
			context.write("<tbody>");
			for(Map.Entry<String, String> entry : parameters.entrySet()) {
				context.write("<tr><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>");
			}
			context.write("</tbody>");
			context.write("</table>");
			context.write("</body></html>");
		} catch (IOException ex) {
			// Log exception to servers log...
			ex.printStackTrace();
		}
	}

}
