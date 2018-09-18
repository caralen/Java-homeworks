package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * The ShellCommand Interface which represents a single shell command.
 */
public interface ShellCommand {

	/**
	 * Executes command.
	 *
	 * @param env implementation of the <code>Environment</code> interface
	 * @param arguments string of arguments which user entered
	 * @return the shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Gets the command name.
	 *
	 * @return the command name
	 */
	String getCommandName();
	
	/**
	 * Gets the read-only list of strings that represent the command description.
	 *
	 * @return the command description
	 */
	List<String> getCommandDescription();
}
