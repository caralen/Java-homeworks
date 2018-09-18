package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * The Class JPAEMProvider is a class which takes care of providing entity manager.
 * 
 */
public class JPAEMProvider {

	/** The locals. */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Gets the entity manager.
	 * If field value {@link #locals} doesn't contain entity manager then the new entity manager is created.
	 *
	 * @return the entity manager
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Commits all transactions and closes entity manager..
	 *
	 * @throws DAOException the DAO exception
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
}