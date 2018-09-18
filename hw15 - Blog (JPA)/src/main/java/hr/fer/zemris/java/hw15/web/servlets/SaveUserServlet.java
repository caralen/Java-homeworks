package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.BlogUserRegistrationForm;

/**
 * The Class SaveUserServlet is a servlet which takes care of saving users.
 * Data should be passed through the post method because of security reasons.
 */
@WebServlet("/servleti/save")
public class SaveUserServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * Checks if there are errors in data which user filled.
	 * If data are valid the new user is created.
	 *
	 * @param req the request
	 * @param resp the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if(!method.equals("Save")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		
		BlogUserRegistrationForm form = new BlogUserRegistrationForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if(form.hasErrors()) {
			req.setAttribute("entry", form);
			req.getRequestDispatcher("/WEB-INF/pages/RegistrationForm.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		form.fillBlogUser(user);
		
		DAOProvider.getDAO().saveUser(user);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
