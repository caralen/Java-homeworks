package hr.fer.java.zemris.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for setting a session attribute "pickedBgColor" 
 * which is a color which will be used for background during the session.
 * @author Alen Carin
 *
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets parameter under the name "color" from the request 
	 * and sets the new session color attribute under the name "pickedBgCol".
	 * The request is then forwarded to "index.jsp"
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = "#ffffff";
		color = req.getParameter("color");
		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
}
