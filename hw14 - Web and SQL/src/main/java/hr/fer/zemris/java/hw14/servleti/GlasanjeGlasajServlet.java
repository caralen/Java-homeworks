package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * The Class GlasanjeGlasajServlet is a servlet which updates the database votes count for the option that the user clicked. 
 * Database is updated based on the parameters in the URL.
 * Further processing is redirected to the servlet mapped to "/servleti/glasanje-rezultati" with the poll id as a parameter.
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pollParameter = req.getParameter("pollID");
		String optionParameter = req.getParameter("optionID");
		
		if(pollParameter == null || optionParameter == null) {
			req.setAttribute("message", "No parameter sent!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		long pollID;
		long optionsID;
		
		try {
			pollID = Long.parseLong(pollParameter);
			optionsID = Long.parseLong(optionParameter);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Parameter must be of type long!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		DAOProvider.getDao().addVote(optionsID);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}
}
