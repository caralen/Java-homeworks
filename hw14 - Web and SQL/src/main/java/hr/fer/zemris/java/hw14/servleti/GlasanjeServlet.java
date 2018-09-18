package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * The Class GlasanjeServlet is a servlet which reads a list of poll options from the database that
 * have the same poll id as the the one passed through arguments.
 * Further processing is forwarded to the glasanjeIndex.jsp.
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String parameter = req.getParameter("pollID");
		
		if(parameter == null) {
			req.setAttribute("message", "No parameter sent!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		long pollID;
		try {
			pollID = Long.parseLong(parameter);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Parameter must be of type long!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		List<PollOption> options = DAOProvider.getDao().getOptionsForPollId(pollID);
		
		req.setAttribute("poll", poll);
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
