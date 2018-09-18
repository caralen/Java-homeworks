package hr.fer.zemris.java.hw15.model;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class BlogEntryForm is a form which contains certain fields of the <code>BlogEntry</code> model.
 * It is used for collecting data before committing them to the database.
 */
public class BlogEntryForm {

	/** The id of the entry. */
	private String id;
	
	/** The title of the entry. */
	private String title;
	
	/** The text of the entry. */
	private String text;
	
	/** The errors map. */
	Map<String, String> errors = new HashMap<>();
	
	
	/**
	 * Fetches error.
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
	 * @return true, if there are errors
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Checks for error.
	 *
	 * @param name the name of the field
	 * @return true, if there is an error
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Fills data from http request.
	 *
	 * @param req the request
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void fillFromHttpRequest(HttpServletRequest req) throws IOException {
		this.id = req.getParameter("id");
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));
	}
	
	/**
	 * Fills data from given entry.
	 *
	 * @param entry the entry
	 */
	public void fillFromEntry(BlogEntry entry) {
		if(entry.getId()==null) {
			this.id = "";
		} else {
			this.id = entry.getId().toString();
		}
		this.title = entry.getTitle();
		this.text = entry.getText();
		
	}

	/**
	 * Fills given entry with data.
	 *
	 * @param entry the entry
	 */
	public void fillEntry(BlogEntry entry) {
		if(this.id.isEmpty()) {
			entry.setId(null);
		} else {
			entry.setId(Long.valueOf(this.id));
		}
		
		entry.setLastModifiedAt(new Date());
		entry.setTitle(this.title);
		entry.setText(this.text);
	}
	
	/**
	 * Validates data.
	 */
	public void validate() {
		errors.clear();
		
		if(this.title.isEmpty()) {
			errors.put("title", "Title is mandatory!");
		}
		
		if(this.text.isEmpty()) {
			errors.put("text", "Text is mandatory!");
		}
	}
	
	/**
	 * Prepares given string.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String prepare(String s) {
		if(s==null) return "";
		return s.trim();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Sets the errors.
	 *
	 * @param errors the errors
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
