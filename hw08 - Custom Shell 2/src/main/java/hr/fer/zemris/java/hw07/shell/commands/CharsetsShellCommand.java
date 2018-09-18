package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The CharsetsShellCommand is used to print all available charsets.
 */
public class CharsetsShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("Command charsets takes no arguments");
		description.add("lists names of supported charsets for your Java platform");
		description.add("A single charset name is written per line.");
	}
	

	/**
	 * Instantiates a new charsets shell command.
	 */
	public CharsetsShellCommand() {
		name = "charsets";
	}

	/**
	 * Arguments must be an empty string.
	 * Prints out all available charsets.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(Util.splitArguments(arguments, env).length != 0) {
			env.writeln("Invalid arguments for charsets command!");
		} else {
			env.writeln(Charset.availableCharsets().toString());
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
