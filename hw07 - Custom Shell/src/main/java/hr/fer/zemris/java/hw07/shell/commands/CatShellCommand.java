package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The CatShellCommand is used for printing out the contents of the file to the console.
 */
public class CatShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("Command cat takes one or two arguments.");
		description.add("The first argument is path to some file and is mandatory.");
		description.add("The second argument is charset name that should be used to interpret chars from bytes.");
		description.add("If not provided, a default platform charset should be used.");
		description.add("This command opens given file and writes its content to console.");
	}
	
	/**
	 * Instantiates a new cat shell command.
	 */
	public CatShellCommand() {
		this.name = "cat";
	}

	/**
	 * Reads the contents of the file if it is a regular file and 
	 * prints the contents using the Environment's writeln() method.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		Charset charset = Charset.defaultCharset();
		
		if (argumentsArray.length < 1 || argumentsArray.length > 2) {
			env.writeln("Invalid number of arguments for the cat command");
		} else {
			Path path = Paths.get(argumentsArray[0]);
			
			if(!Files.isRegularFile(path)) {
				env.writeln("The given path is not a regular file.");
				return ShellStatus.CONTINUE;
			}
			if (argumentsArray.length == 2) {
				try {
					charset = Charset.forName(argumentsArray[1]);
				} catch(RuntimeException e) {
					env.writeln("Invalid charset argument");
					return ShellStatus.CONTINUE;
				}
			}
			
			try {
				Files.readAllLines(path, charset).forEach(e -> env.writeln(e));
			} catch (IOException e) {
				env.writeln("cat command cannot be executed. " + e.getMessage());
			}
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
