package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The CdShellCommand is a shell command which is used to change the current directory.
 */
public class CdShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("Command cd takes one argument, path to the new current directory.");
		description.add("Sets the new directory passed in arguments to be the current directory.");
	}
	

	public CdShellCommand() {
		name = "cd";
	}

	/**
	 * Argument must be a sequence of characters without any spaces.
	 * Sets the new directory passed through arguments to be the new current directory.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if (argumentsArray.length == 1) {
			Path currentDir = env.getCurrentDirectory();
			Path newCurrentDir = currentDir.resolve(arguments);
			env.setCurrentDirectory(newCurrentDir);
		} else {
			env.writeln("Invalid number of arguments for the cd command, should be 1");
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
