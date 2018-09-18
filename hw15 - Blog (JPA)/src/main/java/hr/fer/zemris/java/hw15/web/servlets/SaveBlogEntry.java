package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogEntryForm;

/**
 * The Class SaveBlogEntry is a servlet which takes care of saving blog entry.
 */
@WebServlet("/servleti/saveBlog")
public class SaveBlogEntry extends HttpServlet {

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
	 * If the blog entry already exist it is filled with new data that user filled.
	 * Otherwise, new blog entry is created with the data that user filled.
	 *
	 * @param req the request
	 * @param resp the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		
		String nick = (String) req.getSession().getAttribute("current.user.nick");
		
		String method = req.getParameter("method");
		if(!method.equals("Save")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick);
			return;
		}
		
		BlogEntryForm form = new BlogEntryForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if(form.hasErrors()) {
			req.setAttribute("entry", form);
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntryForm.jsp").forward(req, resp);
			return;
		}
		
		
		if(form.getId() == null || form.getId().isEmpty()) {
			BlogEntry entry = new BlogEntry();
			form.fillEntry(entry);
			entry.setCreatedAt(new Date());
			entry.setCreator(DAOProvider.getDAO().getUser(nick));
			DAOProvider.getDAO().saveBlogEntry(entry);
			
		} else {
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(form.getId()));
			form.fillEntry(entry);
		}
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick);
	}
}
