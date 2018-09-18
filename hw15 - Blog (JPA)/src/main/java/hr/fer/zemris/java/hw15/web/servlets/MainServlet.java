package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.BlogUserLoginForm;

/**
 * The Class MainServlet is a servlet which is called upon the start of the application.
 * It gets a list of all users in the database and sets it as an argument.
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUserLoginForm form = new BlogUserLoginForm();
		req.setAttribute("entry", form);
		
		List<BlogUser> authors = DAOProvider.getDAO().getUsers();
		req.setAttribute("authors", authors);
		
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}
}
