package hr.fer.java.zemris.hw13.listeneri;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The Class TimeTracker implements <code>ServletContextListener</code>.
 * It is a listener which sets the context attribute "time" to current time in milliseconds 
 * each time the context is initialised.
 */
@WebListener
public class TimeTracker implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	/**
	 * Sets the context attribute "time" to current time in milliseconds each time a context is initialised.
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("time", System.currentTimeMillis());
	}

}
