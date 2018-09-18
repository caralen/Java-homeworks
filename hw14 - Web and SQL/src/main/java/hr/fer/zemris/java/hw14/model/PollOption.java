package hr.fer.zemris.java.hw14.model;


/**
 * The Class PollOption represents a model of a poll option.
 * A single poll can have many poll options.
 */
public class PollOption {

	/** The id of the poll option. */
	private long id;
	
	/** The option title. */
	private String optionTitle;
	
	/** The option link. */
	private String optionLink;
	
	/** The id of the poll this option is part of. */
	private long pollID;
	
	/** The number of votes for this option. */
	private long votesCount;
	
	/**
	 * Instantiates a new poll option.
	 */
	public PollOption() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the option title.
	 *
	 * @return the option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the option title.
	 *
	 * @param optionTitle the new option title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Gets the option link.
	 *
	 * @return the option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the option link.
	 *
	 * @param optionLink the new option link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Gets the poll ID.
	 *
	 * @return the poll ID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets the poll ID.
	 *
	 * @param pollID the new poll ID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Gets the votes count.
	 *
	 * @return the votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the votes count.
	 *
	 * @param votesCount the new votes count
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PollOption id="+id;
	}
}
