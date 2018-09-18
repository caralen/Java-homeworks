package hr.fer.zemris.java.hw14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Utility class used for creating and populating tables in the database.
 * @author Alen Carin
 *
 */
public class SQLUtil {

	/** SQL query for creating Poll table. */
	static String createPoll = "CREATE TABLE Polls\r\n" + 
			"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
			"title VARCHAR(150) NOT NULL,\r\n" + 
			"message CLOB(2048) NOT NULL\r\n" + 
			")";
	
	/** SQL query for creating PollOptions table. */
	static String createPollOptions = "CREATE TABLE PollOptions\r\n" + 
			"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
			"optionTitle VARCHAR(100) NOT NULL,\r\n" + 
			"optionLink VARCHAR(150) NOT NULL,\r\n" + 
			"pollID BIGINT,\r\n" + 
			"votesCount BIGINT,\r\n" + 
			"FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + 
			")";
	
	/**
	 * Populates table PollOptions with information about national teams.
	 * @param con the connection
	 * @param pollID the poll identification number
	 * @throws SQLException
	 * @throws  
	 */
	static void insertOptions(Connection con, long pollID, String fileName) throws SQLException {
		PreparedStatement pst = con.prepareStatement(
				"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,0)",
				Statement.RETURN_GENERATED_KEYS);
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String line : lines) {
			String[] parts = line.split("\t");
			addRowOptions(pst, parts[0], parts[1], pollID);
		}
		pst.close();
	}
	
	/**
	 * Populates table PollOptions table with information about bands.
	 * @param con the connection
	 * @param pollID the poll identification number
	 * @throws SQLException
	 */
	static void insertBands(Connection con, long pollID) throws SQLException {
		PreparedStatement pst = con.prepareStatement(
				"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,0)",
				Statement.RETURN_GENERATED_KEYS);
		
		addRowOptions(pst, "The Beatles", "https://www.youtube.com/watch?v=z9ypq6_5bsg", pollID);
		addRowOptions(pst, "The Platters", "https://www.youtube.com/watch?v=FyM8NVl4yBY", pollID);
		addRowOptions(pst, "The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", pollID);
		addRowOptions(pst, "The Four Seasons", "https://www.youtube.com/watch?v=kYBZqfOZiS4", pollID);
		addRowOptions(pst, "The Marcels", "https://www.youtube.com/watch?v=v0fy1HeJv80", pollID);
		addRowOptions(pst, "The Everly Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8", pollID);
		addRowOptions(pst, "The Mamas And The Papas", "https://www.youtube.com/watch?v=N-aK6JnyFmk", pollID);
		pst.close();
	}
	
	/**
	 * Executes update query with given information.
	 * @param pst the prepared statement
	 * @param optionTitle the title of the option
	 * @param optionLink the link of the option
	 * @param pollID the poll identification number
	 * @throws SQLException
	 */
	private static void addRowOptions(PreparedStatement pst, String optionTitle, String optionLink, long pollID) 
			throws SQLException {
		
		pst.setString(1, optionTitle);
		pst.setString(2, optionLink);
		pst.setLong(3, pollID);
		pst.executeUpdate();
	}
	
	/**
	 * Inserts bands poll into Polls table.
	 * @param con the connection
	 * @return identification number of the inserted poll
	 * @throws SQLException
	 */
	static long insertPoll(Connection con, String poll) throws SQLException {
		PreparedStatement pst;
		
		pst = con.prepareStatement("INSERT INTO POLLS (title, message) values (?,?)",
				Statement.RETURN_GENERATED_KEYS);
		
		String[] pollParts = poll.split("\t");
		
		addRowPolls(pst, pollParts[0], pollParts[1]);
		
		ResultSet rset = pst.getGeneratedKeys();
		long pollID = 0;
		
		if(rset != null && rset.next()) {
			pollID = rset.getLong(1);
		} else {
			throw new RuntimeException("Cannot insert into table Polls");
		}
		rset.close();
		pst.close();
		return pollID;
	}
	
	/**
	 * Inserts world cup winner poll into Polls table.
	 * @param con the connection
	 * @return identification number of the inserted poll
	 * @throws SQLException
	 */
	static long insertWorldCupPoll(Connection con) throws SQLException {
		PreparedStatement pst;
		
		pst = con.prepareStatement("INSERT INTO POLLS (title, message) values (?,?)",
				Statement.RETURN_GENERATED_KEYS);
		
		addRowPolls(pst, 
				"Glasanje za pobjednika svjetskog prvenstva",
				"Od navedenih nacija, za koga mislite da je novi svjetski prvak u nogometu?");
		
		ResultSet rset = pst.getGeneratedKeys();
		long pollID = 0;
		
		if(rset != null && rset.next()) {
			pollID = rset.getLong(1);
		} else {
			throw new RuntimeException("Cannot insert into table Polls");
		}
		rset.close();
		pst.close();
		return pollID;
	}
	
	/**
	 * Executes update query with given information.
	 * @param pst the prepared statement
	 * @param title the title of the poll
	 * @param message the message of the poll
	 * @throws SQLException
	 */
	private static void addRowPolls(PreparedStatement pst, String title, String message) throws SQLException {
		pst.setString(1, title);
		pst.setString(2, message);
		pst.executeUpdate();
	}
	
	/**
	 * Finds the poll id for bands poll.
	 *
	 * @param con the connection
	 * @return the poll id
	 * @throws SQLException the SQL exception
	 */
	static long findIdForTitle(Connection con, String title) throws SQLException {
		PreparedStatement pst = con.prepareStatement("select id from Polls "
				+ "where title LIKE ?");
		
		pst.setString(1, title);
		ResultSet rs = pst.executeQuery();
		long pollID = 0;
		
		if(rs != null && rs.next()) {
			pollID = rs.getLong(1);
		}
		rs.close();
		pst.close();
		return pollID;
	}
	
	/**
	 * Checks if the polls table is empty.
	 * @param con the connection
	 * @return true if the polls table is empty, false otherwise
	 * @throws SQLException
	 */
	static boolean pollsEmpty(Connection con) throws SQLException {
		PreparedStatement pst = con.prepareStatement("select * from Polls");
		ResultSet rs = pst.executeQuery();
		
		//Ako je prazna Polls tablica, onda mora biti i pollOptions
		if(!rs.next()) {
			rs.close();
			pst.close();
			return true;
		}
		rs.close();
		pst.close();
		return false;
	}
}
