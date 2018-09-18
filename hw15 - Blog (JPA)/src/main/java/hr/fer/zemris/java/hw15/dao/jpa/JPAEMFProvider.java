package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * The Class JPAEMFProvider is a class which holds a static reference to the <code>EntityManagerFactory</code>.
 */
public class JPAEMFProvider {

	/** The reference to entity manager factory.. */
	public static EntityManagerFactory emf;
	
	/**
	 * Gets the reference to entity manager factory.
	 *
	 * @return the emf
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets the reference to entity manager factory..
	 *
	 * @param emf the new emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}