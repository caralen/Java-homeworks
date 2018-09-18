package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class BlogUser is a class which represents a single user of the blog.
 */
@NamedQueries({
	@NamedQuery(name="BlogUser.checkNick",query="select b from BlogUser as b where b.nick=:nick"),
	@NamedQuery(name="BlogUser.getUser", query="select b from BlogUser as b where b.nick=:nick and b.passwordHash=:password"),
})
@Entity
@Table(name="blog_user")
public class BlogUser {

	/** The id of the user. */
	private Long id;
	
	/** The first name of the user. */
	private String firstName;
	
	/** The last name of the user. */
	private String lastName;
	
	/** The nick of the user. */
	private String nick;
	
	/** The email of the user. */
	private String email;
	
	/** The password hash. */
	private String passwordHash;
	
	/** The collection of blog entries. */
	private Collection<BlogEntry> blogEntries = new ArrayList<>();
	
	/**
	 * Gets the collection of blog entries.
	 *
	 * @return the blog entries
	 */
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	public Collection<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Sets the blog entries collection.
	 *
	 * @param blogEntries the new blog entries
	 */
	public void setBlogEntries(Collection<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	@Column(length=20,nullable=false)
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
	@Column(length=30,nullable=false)
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
	@Column(length=50,nullable=false,unique=true)
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
	@Column(length=50,nullable=false)
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
	 * Gets the password hash.
	 *
	 * @return the password hash
	 */
	@Column(length=100,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Sets the password hash.
	 *
	 * @param passwordHash the new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	
}
