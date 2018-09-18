package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * The DAO (data access object) is an interface for accessing data objects.
 */
public interface DAO {

	/**
	 * Gets entry with the given <code>id</code>. If it doesn't exist,
	 * returns <code>null</code>.
	 * 
	 * @param id is the entry key
	 * @return entry or <code>null</code> if it doesn't exist.
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Gets the blog entries which are mapped to the given user.
	 *
	 * @param user the user
	 * @return the blog entries
	 */
	public List<BlogEntry> getBlogEntries(BlogUser user);
	
	/**
	 * Gets the user with the given nick.
	 *
	 * @param nick the nickname of the user.
	 * @return the user
	 * @throws DAOException the Data access object exception
	 */
	public BlogUser getUser(String nick) throws DAOException;
	
	/**
	 * Gets all users from the database.
	 *
	 * @return the users
	 * @throws DAOException the Data access object exception
	 */
	public List<BlogUser> getUsers() throws DAOException;
	
	/**
	 * Saves blog entry to the database.
	 * 
	 * @param BlogEntry is the blog entry which will be saved to the database
	 * @throws DAOException the Data access object exception
	 */
	public void saveBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Saves blog comment to the database.
	 * 
	 * @param comment that will be saved to the database
	 * @throws DAOException the Data access object exception
	 */
	public void saveBlogComment(BlogComment comment) throws DAOException;
	
	/**
	 * Saves blog user to the database.
	 * @param user which will be saved to the database
	 * @throws DAOException the Data access object exception
	 */
	public void saveUser(BlogUser user) throws DAOException;
}