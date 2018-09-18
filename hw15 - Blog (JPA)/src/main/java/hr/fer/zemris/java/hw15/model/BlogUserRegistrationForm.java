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
 * The Class BlogUserRegistrationForm is a form which contains certain fields of the <code>BlogUser</code> model.
 * It is used for collecting data before committing them to the database.
 * This form is used while trying to register user.
 */
public class BlogUserRegistrationForm {

	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The nick. */
	private String nick;
	
	/** The email. */
	private String email;
	
	/** The password. */
	private String password;
	
	/** The errors. */
	Map<String, String> errors = new HashMap<>();
	
	
	/**
	 * Fetches error for the given field.
	 *
	 * @param name the name
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
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.nick = prepare(req.getParameter("nick"));
		this.email = prepare(req.getParameter("email"));
		this.password = prepare(req.getParameter("password"));
	}
	
	/**
	 * Fills data from the blog user.
	 *
	 * @param user the blog user
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void fillBlogUser(BlogUser user) throws IOException {
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setNick(this.nick);
		user.setEmail(this.email);
		user.setPasswordHash(ShaHash.calculateSha(password));
	}
	
	/**
	 * Validates data. If there is an errors, it is put in the errors map.
	 */
	public void validate() {
		errors.clear();
		
		if(this.firstName.isEmpty()) {
			errors.put("firstName", "First name is mandatory!");
		}
		
		if(this.lastName.isEmpty()) {
			errors.put("lastName", "Last name is mandatory!");
		}
		
		if(this.nick.isEmpty()) {
			errors.put("nick", "Nickname is mandatory!");
		}
		
		EntityManager em = JPAEMProvider.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) 
				em.createNamedQuery("BlogUser.checkNick")
				.setParameter("nick", this.nick)
				.getResultList();
		
		if(!users.isEmpty()) {
			errors.put("nick", "User with that nick already exists!");
		}

		if(this.email.isEmpty()) {
			errors.put("email", "Email is mandatory!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("email", "Email doesn't have valid format.");
			}
		}
		
		if(this.password.isEmpty()) {
			errors.put("password", "Password is mandatory!");
			
		} else if(password.length() < 3) {
			errors.put("password", "Password is too short.");
		}
	}
	
	/**
	 * Prepares given string.
	 *
	 * @param s the string
	 * @return the string
	 */
	private String prepare(String s) {
		if(s==null) return "";
		return s.trim();
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
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
