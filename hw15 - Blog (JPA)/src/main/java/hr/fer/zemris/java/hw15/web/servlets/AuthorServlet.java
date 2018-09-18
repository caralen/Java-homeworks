package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogCommentForm;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogEntryForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * The Class AuthorServlet is mapped to all urls that start with "/servleti/author/.
 * It is used for all manipulation of the blog entries, as well as reading them.
 * It is used for creating, editing, listing blog entries.
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nick = null;
		String path = req.getServletPath() + req.getPathInfo();
		path = path.replaceAll("/servleti/author", "");
		
		if (Pattern.matches("/.+/new", path)) {
			Object sessionNick = req.getSession().getAttribute("current.user.nick");
			nick = path.split("/")[1];
			
			if(sessionNick == null || !nick.equals(sessionNick.toString())) {
				sendError(req, resp, "Invalid nick given");
				return;
			}
			
			BlogEntryForm form = new BlogEntryForm();
			req.setAttribute("entry", form);
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntryForm.jsp").forward(req, resp);
			return;
			
		} else if (Pattern.matches("/.+/edit", path)) {
			Long id = null;
			try {
				id = Long.valueOf(req.getParameter("id"));
			} catch(Exception ex) {
				req.setAttribute("message", "Invalid blog entry id given.");
				req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
				return;
			}

			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			
			if(entry == null || !entry.getCreator().getNick().equals(req.getSession().getAttribute("current.user.nick"))) {
				sendError(req, resp, "Invalid blog entry");
				return;
			}
			
			BlogEntryForm form = new BlogEntryForm();
			form.fillFromEntry(entry);
			
			req.setAttribute("entry", form);
			
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntryForm.jsp").forward(req, resp);
			
		} else if(Pattern.matches("/.+/.+", path)){
			String[] parts = path.split("/");
			nick = parts[1];
			String blogId = parts[parts.length-1];
			
			if(DAOProvider.getDAO().getUser(nick) == null) {
				sendError(req, resp, "Invalid nick given.");
				return;
			}
			
			Long id;
			try {
				id = Long.parseLong(blogId);
			} catch(Exception e) {
				sendError(req, resp, "Invalid parameter.");
				return;
			}
			
			BlogCommentForm form = new BlogCommentForm();
			form.setEntryId(String.valueOf(id));
			req.setAttribute("nick", nick);
			req.setAttribute("form", form);
			req.setAttribute("blog", DAOProvider.getDAO().getBlogEntry(id));
			req.getRequestDispatcher("/WEB-INF/pages/ViewBlog.jsp").forward(req, resp);
			
		} else if (Pattern.matches("/.+", path)) {
			nick = path.replaceAll("/", "");
			BlogUser user = DAOProvider.getDAO().getUser(nick);
			
			if(user == null) {
				sendError(req, resp, "User with given nick doesn't exist");
				return;
			}
			
			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(user);
			
			req.setAttribute("entries", entries);
			req.setAttribute("nick", nick);
			req.getRequestDispatcher("/WEB-INF/pages/Titles.jsp").forward(req, resp);
			
		} else {
			sendError(req, resp, "Invalid parameter.");
		}
	}

	private void sendError(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
		req.setAttribute("message", message);
		req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
	}
}
