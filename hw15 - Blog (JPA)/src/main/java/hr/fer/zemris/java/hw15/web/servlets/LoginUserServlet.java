package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.BlogUserLoginForm;

/**
 * The Class LoginUserServlet is a servlet used for user logging into application.
 * Arguments should be passed via post method because of security.
 */
@WebServlet("/servleti/login")
public class LoginUserServlet extends HttpServlet {

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
	 * Performs the user login process.
	 *
	 * @param req the request
	 * @param resp the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if(!method.equals("Login")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		
		BlogUserLoginForm form = new BlogUserLoginForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if(form.hasErrors()) {
			req.setAttribute("entry", form);
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = form.getBlogUser();
		
		HttpSession session = req.getSession();
		session.setAttribute("current.user.id", user.getId());
		session.setAttribute("current.user.fn", user.getFirstName());
		session.setAttribute("current.user.ln", user.getLastName());
		session.setAttribute("current.user.nick", user.getNick());
		session.setAttribute("current.user.email", user.getEmail());
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
