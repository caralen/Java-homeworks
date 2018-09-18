package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;


/**
 * The Class GlasanjeRezultatiServlet is a servlet which sets list with contents of the poll options table that
 * have the given poll id and forwards further processing to the "glasanjeRez.jsp".
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

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
		
		List<PollOption> options = DAOProvider.getDao().getOptionsForPollId(pollID);
		List<PollOption> maxVotes = new ArrayList<>();
		
		long max = 0;
		
		for(PollOption option : options) {
			if(option.getVotesCount() > max) {
				maxVotes.clear();
				maxVotes.add(option);
				max = option.getVotesCount();
			} else if(option.getVotesCount() == max) {
				maxVotes.add(option);
			}
		}
		Collections.sort(options, (e1,e2) -> Long.compare(e2.getVotesCount(), e1.getVotesCount()));
		
		req.setAttribute("pollID", pollID);
		req.setAttribute("maxVotes", maxVotes);
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
