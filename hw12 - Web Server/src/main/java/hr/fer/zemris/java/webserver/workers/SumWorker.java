package hr.fer.zemris.java.webserver.workers;

import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class SumWorker implements IWebWorker. It gets parameters from the context and takes ones which have key "a" and "b".
 * Calculates the sum of their values ands sets temporary paramters.
 */
public class SumWorker implements IWebWorker {

	/**
	 * It takes parameters from the context and extracts ones which have key "a" and "b".
	 * Calculates the sum of their values ands puts parameters and the sum in temporary paramters.
	 * After calculation dispatchRequest method is called on the dispatcher from the context to continue processing.
	 */
	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		Map<String, String> params = context.getParameters();
		
		int a = 1;
		int b = 2;
		try {
			if(params.get("a") != null) {
				a = Integer.parseInt(params.get("a"));
			}
			if(params.get("b") != null) {
				b = Integer.parseInt(params.get("b"));
			}
			
			int sum = a + b;
			context.setTemporaryParameter("zbroj", String.valueOf(sum));
			context.setTemporaryParameter("b", String.valueOf(b));
			context.setTemporaryParameter("a", String.valueOf(a));
			
			context.getDispatcher().dispatchRequest("/private/calc.smscr");
			
		} catch(NullPointerException e) {
			
		} catch(NumberFormatException e) {
			
		} 
			
	}

}
