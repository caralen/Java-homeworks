package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;


/**
 * The Class Inicijalizacija implements <code>ServletContextListener</code>.
 * It's methods are called when the context is initialized or destroyed.
 * It reads from the dbsettings.properties file for important information about database.
 * It prepares database for the web application.
 * If tables "Polls" and "PollOptions" are not present in given database they are created and populated.
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {
	
	/** The polls table name. */
	private static String POLLS_TABLE_NAME = "POLLS";
	
	/** The poll options table name. */
	private static String POLL_OPTIONS_TABLE_NAME = "POLLOPTIONS";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String configFileName = "WEB-INF/dbsettings.properties";
		String connectionURL = readProperties(configFileName, sce);
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		
		try {
			Connection con = cpds.getConnection();
			
			if (!tableExists(con, POLLS_TABLE_NAME) && !tableExists(con, POLL_OPTIONS_TABLE_NAME)) {
				createTable(con, SQLUtil.createPoll);
				createTable(con, SQLUtil.createPollOptions);
				populatePolls(con, sce.getServletContext());
				
			} else if(!tableExists(con, POLLS_TABLE_NAME)) {
				createTable(con, SQLUtil.createPoll);
				populatePolls(con, sce.getServletContext());
				
			} else if (!tableExists(con, POLL_OPTIONS_TABLE_NAME)) {
				
				if (SQLUtil.pollsEmpty(con)) {
					createTable(con, SQLUtil.createPollOptions);
					populatePolls(con, sce.getServletContext());

				} else {
					createTable(con, SQLUtil.createPollOptions);

					String pollsFile = sce.getServletContext().getRealPath("/WEB-INF/polls.txt");

					List<String> lines = null;
					try {
						lines = Files.readAllLines(Paths.get(pollsFile));
					} catch (IOException e) {
						e.printStackTrace();
					}

					String bandsFile = sce.getServletContext().getRealPath("/WEB-INF/bands.txt");
					String nationsFile = sce.getServletContext().getRealPath("/WEB-INF/nations.txt");
					String[] files = new String[] { bandsFile, nationsFile };
					int counter = 0;

					for (String line : lines) {
						String[] pollParts = line.split("\t");

						long pollID = SQLUtil.findIdForTitle(con, pollParts[0]);
						if (pollID != 0) {
							SQLUtil.insertOptions(con, pollID, files[counter++]);
						}
					}
				}
				
			}
			if(SQLUtil.pollsEmpty(con)) {
				populatePolls(con, sce.getServletContext());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}
	

	/**
	 * Populates polls table.
	 *
	 * @param con the connection
	 * @throws SQLException the SQL exception
	 */
	private static void populatePolls(Connection con, ServletContext context) throws SQLException {
		String bandsFile = context.getRealPath("/WEB-INF/bands.txt");
		String nationsFile = context.getRealPath("/WEB-INF/nations.txt");
		String pollsFile = context.getRealPath("/WEB-INF/polls.txt");
		
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(pollsFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String bandsPoll = lines.get(0);
		String nationsPoll = lines.get(1);
		
		long pollID = SQLUtil.insertPoll(con, bandsPoll);
		SQLUtil.insertOptions(con, pollID, bandsFile);
		pollID = SQLUtil.insertPoll(con, nationsPoll);
		SQLUtil.insertOptions(con, pollID, nationsFile);
	}
	

	/**
	 * Checks if a table with given name exists.
	 *
	 * @param con the connection
	 * @param tableName the table name
	 * @return true, if table exists
	 * @throws SQLException the SQL exception
	 */
	private static boolean tableExists(Connection con, String tableName) throws SQLException {
		boolean exists = false;
		
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getTables(null, null, tableName, new String[] { "TABLE" });

		if(res.next()) {
			exists = true;
		}
	    return exists;
	}
	
	/**
	 * Creates the table by executing the given statement.
	 *
	 * @param con the connection
	 * @param statement the sql query
	 * @throws SQLException the SQL exception
	 */
	private static void createTable(Connection con, String statement) throws SQLException {
		try(PreparedStatement pst = con.prepareStatement(statement)){
			pst.executeUpdate();
		}
	}
	
	/**
	 * Read properties from the given properties file.
	 *
	 * @param configFileName the configuration file name
	 * @param sce the servlet context event
	 * @return the string which represents connection url
	 */
	private String readProperties(String configFileName, ServletContextEvent sce) {
		Properties settingsProperties = new Properties();
		
		try {
			settingsProperties.load(Files.newInputStream(Paths.get(sce.getServletContext().getRealPath(configFileName))));
		} catch (IOException e) {
			throw new RuntimeException("Cannot open properties file.", e);
		}
		String host = settingsProperties.getProperty("host");
		String port = settingsProperties.getProperty("port");
		String dbName = settingsProperties.getProperty("name");
		String user = settingsProperties.getProperty("user");
		String password = settingsProperties.getProperty("password");
		
		if(host == null || port == null || dbName == null || user == null || password == null) {
			throw new RuntimeException("Invalid properties file");
		}
		// jdbc:derby://localhost:1527/votingDB;user=ivica;password=ivo
		
		return "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password=" + password;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}