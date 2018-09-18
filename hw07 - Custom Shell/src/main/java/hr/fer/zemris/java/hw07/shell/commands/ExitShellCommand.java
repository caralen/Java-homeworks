package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The ExitShellCommand is instantiated when the user types in "exit".
 * The shell should terminate the program then.
 */
public class ExitShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("Command used for exiting the program.");
		description.add("It takes no arguments.");
	}

	/**
	 * Instantiates a new exit shell command.
	 */
	public ExitShellCommand() {
		name = "exit";
	}

	/**
	 * The arguments must be an empty string.
	 * Method prints out the goodbye message and returns the terminate status
	 * which signals that the program should be terminated.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments.length() != 0) {
			env.writeln("Invalid arguments for exit command!");
		} else {
			env.writeln("Goodbye!");
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
