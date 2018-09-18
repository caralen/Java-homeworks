package hr.fer.zemris.java.hw15.model;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class BlogCommentForm is a form which contains certain fields of the <code>BlogComment</code> model.
 * It is used for collecting data before committing them to the database.
 */
public class BlogCommentForm {

	/** The entry id. */
	private String entryId;
	
	/** The message. */
	private String message;
	
	/** The errors map. */
	Map<String, String> errors = new HashMap<>();
	
	
	/**
	 * Fetches error for the field with the given name.
	 *
	 * @param name the name of the field
	 * @return the string
	 */
	public String fetchError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Checks for errors.
	 *
	 * @return true, if the map contains errors
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Checks for error.
	 *
	 * @param name the name of the field
	 * @return true, if successful
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Fill fields from the http request.
	 *
	 * @param req the request
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void fillFromHttpRequest(HttpServletRequest req) throws IOException {
		this.entryId = req.getParameter("entryId");
		this.message = req.getParameter("comment");
	}
	
	/**
	 * Fill given entry from the fields.
	 *
	 * @param comment the comment
	 */
	public void fillEntry(BlogComment comment) {
		comment.setMessage(this.message);
		comment.setPostedOn(new Date());
	}
	
	/**
	 * Checks if data in this form is valid and puts errors in the map.
	 */
	public void validate() {
		errors.clear();
		
		if(this.message.isEmpty()) {
			errors.put("message", "Message is mandatory!");
		}
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the entry id.
	 *
	 * @return the entry id
	 */
	public String getEntryId() {
		return entryId;
	}

	/**
	 * Sets the entry id.
	 *
	 * @param entryId the new entry id
	 */
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
}
