package hr.fer.zemris.java.hw17.model;

/**
 * The Class Picture.
 */
public class Picture {

	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The tags. */
	private String[] tags;
	
	
	/**
	 * Instantiates a new picture.
	 *
	 * @param name the name
	 * @param description the description
	 * @param tags the tags
	 */
	public Picture(String name, String description, String ... tags) {
		super();
		this.name = name;
		this.description = description;
		this.tags = tags;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public String[] getTags() {
		return tags;
	}
	
	/**
	 * Sets the tags.
	 *
	 * @param tags the new tags
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
}
