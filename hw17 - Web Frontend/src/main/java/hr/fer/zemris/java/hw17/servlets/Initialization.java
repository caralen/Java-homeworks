package hr.fer.zemris.java.hw17.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This class implements <code>ServletContextListener</code> and sets static variable 
 * in class <code>ExtractorUtil</code> so it has the reference to the servlet context.
 * @author Alen Carin
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ExtractorUtil.CONTEXT = sce.getServletContext();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
