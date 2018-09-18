package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.model.BlogUserRegistrationForm;

/**
 * The Class RegistrationServlet is a servlet which takes care of the user registration.
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BlogUserRegistrationForm form = new BlogUserRegistrationForm();
		req.setAttribute("entry", form);
		req.getRequestDispatcher("/WEB-INF/pages/RegistrationForm.jsp").forward(req, resp);
	}
}
