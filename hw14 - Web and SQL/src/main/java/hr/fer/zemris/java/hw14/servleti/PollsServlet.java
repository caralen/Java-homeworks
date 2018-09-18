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


/**
 * The Class PollsServlet is a servlet which fetches list of polls from the database 
 * and forwards that list to the ankete.jsp file.
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPollsList();
		
		req.setAttribute("polls", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/ankete.jsp").forward(req, resp);
	}
}
