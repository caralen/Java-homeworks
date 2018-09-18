package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The PwdShellCommand is a shell command used for printing the working directory.
 */
public class PwdShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("Command pwd takes no arguments.");
		description.add("Prints the absolute path to the current directory.");
	}

	public PwdShellCommand() {
		name = "pwd";
	}

	/**
	 * Prints the working directory.
	 * Arguments must be an empty string.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() == 0) {
			env.writeln(env.getCurrentDirectory().toString());
		} else {
			env.writeln("Invalid arguments for the pwd command, should be no argument.");
		}
		return ShellStatus.CONTINUE;
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
