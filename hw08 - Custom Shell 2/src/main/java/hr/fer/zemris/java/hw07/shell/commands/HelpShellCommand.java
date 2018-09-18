package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The HelpShellCommand is a command which helps the user with using the rest of the commands.
 */
public class HelpShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("This command if called with no arguments will print a list of all available commands.");
		description.add("If there is a single argument - command name, it will print description for that command.");
	}

	/**
	 * Instantiates a new help shell command.
	 */
	public HelpShellCommand() {
		name = "help";
	}

	/**
	 * Arguments must be zero or one sequences of strings divided by a space (except in quotations).
	 * If the arguments is an empty string then the names of all available commands is printed out.
	 * It the arguments contains a single sequence and that is a correct name of a certain command
	 * then the command description is printed out.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if(arguments.length() == 0) {
			env.writeln("Here is a list of all available commands:");
			Collection<ShellCommand> col = env.commands().values();
			for(ShellCommand command : col) {
				env.writeln(command.getCommandName());
			}
		} else if(argumentsArray.length == 1) {
			List<String> description = env.commands().get(arguments).getCommandDescription();
			description.stream().forEach(s -> env.writeln(s.toString()));
		} else {
			env.writeln("Invalid arguments for the help command");
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
