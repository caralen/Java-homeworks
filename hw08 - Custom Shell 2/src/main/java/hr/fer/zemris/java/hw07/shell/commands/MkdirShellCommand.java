package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The MkdirShellCommand is a command used for creating a new directory.
 */
public class MkdirShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("The mkdir command takes a single argument: directory name.");
		description.add("Creates the appropriate directory structure.");
	}

	/**
	 * Instantiates a new mkdir shell command.
	 */
	public MkdirShellCommand() {
		name = "mkdir";
	}

	/**
	 * Creates a new directory in a given directory.
	 * Arguments must be a single sequence of characters with no spaces (except in quotations).
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if(argumentsArray.length != 1) {
			env.writeln("Invalid number of arguments for the execute command, should be 1.");
		}
		Path dir = env.getCurrentDirectory().resolve(arguments);
		try {
			Files.createDirectory(dir);
		} catch (IOException e) {
			env.writeln("Error in mkdir command" + e.getMessage());
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
