package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * The Class JPADAOImpl is an implementation of the <code>DAO</code>
 * It has public methods for accessing data objects.
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@Override
	public List<BlogUser> getUsers() throws DAOException {
		
		return JPAEMProvider.getEntityManager()
				.createQuery("SELECT b FROM BlogUser b", BlogUser.class)
				.getResultList();
	}
	
	@Override
	public BlogUser getUser(String nick) throws DAOException {
		
		return (BlogUser) JPAEMProvider.getEntityManager()
				.createQuery("SELECT b FROM BlogUser as b where b.nick=:nick")
				.setParameter("nick", nick)
				.getSingleResult();
	}

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) {
		
		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = JPAEMProvider.getEntityManager()
				.createQuery("SELECT b FROM BlogEntry as b where b.creator=:user")
				.setParameter("user", user)
				.getResultList();
		
		return entries;
	}

	@Override
	public void saveBlogEntry(BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(entry);
	}

	@Override
	public void saveBlogComment(BlogComment comment) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
	}

	@Override
	public void saveUser(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(user);
	}

}