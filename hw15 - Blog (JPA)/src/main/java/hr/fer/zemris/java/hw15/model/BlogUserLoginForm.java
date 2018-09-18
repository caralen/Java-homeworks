package hr.fer.zemris.java.hw15.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.crypto.ShaHash;
import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;

/**
 * The Class BlogUserLoginForm is a form which contains certain fields of the <code>BlogUser</code> model.
 * It is used for collecting data before committing them to the database.
 * This form is used while trying to login.
 */
public class BlogUserLoginForm {

	/** The nick. */
	private String nick;
	
	/** The password. */
	private String password;
	
	/** The user. */
	private BlogUser user;
	
	/** The errors map. */
	Map<String, String> errors = new HashMap<>();
	
	
	/**
	 * Fetches error for the given field.
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
	 * Checks for error for the given field.
	 *
	 * @param name the name of the field
	 * @return true, if successful
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
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
	}
	
	/**
	 * Gets the blog user.
	 *
	 * @return the blog user
	 */
	public BlogUser getBlogUser() {
		return user;
	}
	
	/**
	 * Validates data. If there are errors, they are put in the errors map
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void validate() throws IOException {
		errors.clear();
		
		if(this.nick.isEmpty()) {
			errors.put("nick", "Nickname is empty!");
		}
		
		if(this.password.isEmpty()) {
			errors.put("password", "Password is empty!");
		}
		
		if(!this.nick.isEmpty() && !this.password.isEmpty()) {
			
			EntityManager em = JPAEMProvider.getEntityManager();

			@SuppressWarnings("unchecked")
			List<BlogUser> users = (List<BlogUser>) 
					em.createNamedQuery("BlogUser.getUser")
					.setParameter("nick", this.nick)
					.setParameter("password", ShaHash.calculateSha(password))
					.getResultList();
			
			if (users.isEmpty()) {
				errors.put("nick", "Invalid nick or password");
				this.password = null;
				this.user = null;
			} else {
				this.user = users.get(0);
			}
		}
	}
	
	/**
	 * Prepares data.
	 *
	 * @param s the string
	 * @return the string
	 */
	private String prepare(String s) {
		if(s==null) return "";
		return s.trim();
	}

	/**
	 * Gets the nick.
	 *
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the nick.
	 *
	 * @param nick the new nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param passwordHash the new password
	 */
	public void setPassword(String passwordHash) {
		this.password = passwordHash;
	}

	/**
	 * Gets the errors map.
	 *
	 * @return the errors map
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Sets the errors map.
	 *
	 * @param errors the errors
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
