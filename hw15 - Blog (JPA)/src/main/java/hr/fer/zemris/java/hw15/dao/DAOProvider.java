package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * The Class DAOProvider holds a static to the <code>JPADAOImpl</code>.
 */
public class DAOProvider {

	/** The static field which represents dao. */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Gets the dao.
	 *
	 * @return the dao
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}