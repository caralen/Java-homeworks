package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogCommentForm;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * The Class SaveBlogComment is a servlet which saves blog comment into the database.
 * If the comment already exists it is filled with new data, otherwise new comment is created.
 */
@WebServlet("/servleti/saveComment")
public class SaveBlogComment extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		BlogCommentForm form = new BlogCommentForm();
		form.fillFromHttpRequest(req);
		form.validate();
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(form.getEntryId()));
		
		String method = req.getParameter("method");
		if(!method.equals("Save")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" 
					+ entry.getCreator().getNick() + "/" + entry.getId());
			return;
		}
		
		if(form.hasErrors()) {
			req.setAttribute("form", form);
			req.setAttribute("blog", entry);
			req.getRequestDispatcher("/WEB-INF/pages/ViewBlog.jsp").forward(req, resp);
			return;
		}
		
		Object email = req.getSession().getAttribute("current.user.email");
		String sessionEmail = null;
		if(email != null) {
			sessionEmail = email.toString();
		} else {
			sessionEmail = "anonymus";
		}
		
		BlogComment comment = new BlogComment();
		form.fillEntry(comment);
		comment.setBlogEntry(entry);
		comment.setUsersEMail(sessionEmail);
		
		DAOProvider.getDAO().saveBlogComment(comment);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/"
				+ entry.getCreator().getNick() + "/" + entry.getId());
		
	}
}
