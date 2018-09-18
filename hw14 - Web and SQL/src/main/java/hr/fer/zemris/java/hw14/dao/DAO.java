package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic i Alen Carin
 *
 */
public interface DAO {

	/**
	 * Gets list of all polls from the database
	 * 
	 * @return list of polls
	 * @throws DAOException in case of error
	 */
	public List<Poll> getPollsList() throws DAOException;
	
	/**
	 * Returns poll with the given id that is stored in the database.
	 * If the poll doesn't exists, null is returned.
	 * @param id of the poll
	 * @return poll that has the given id
	 * @throws DAOException
	 */
	public Poll getPoll(long id) throws DAOException;
	
	/**
	 * Gets list of <code>PollOptions</code> which have the pollID value equal to the given value.
	 * @param id the poll identification number
	 * @return list of poll options have the given poll id
	 */
	public List<PollOption> getOptionsForPollId(long id);
	
	/**
	 * Gets <code>PollOption</code> that has the id equal to the given id.
	 * @param optionId the identification number of the poll option
	 * @return poll option that has the given id
	 */
	public PollOption getOptionForId(long optionId);
	
	/**
	 * Increments number of votes for the poll option that has the given id.
	 * @param id the identification number of the poll option
	 */
	public void addVote(long id);
}