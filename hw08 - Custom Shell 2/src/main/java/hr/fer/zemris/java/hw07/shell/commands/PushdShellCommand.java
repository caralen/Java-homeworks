package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The PushdShellCommand is a command that pushes current directory to stack and
 * the new current directory becomes the one passed through arguments.
 */
public class PushdShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		name = "pushd";
		
		description = new LinkedList<>();
		description.add("The pushd command pushes current directory on stack.");
		description.add("Then the current directory becomes the one passed through arguments.");
		description.add("It takes one argument which is a path to a existing directory.");
	}

	/**
	 * Pushes current directory to the stack in the shared data and sets the current directory 
	 * to the path passed through arguments.
	 * Arguments must be a sequence of characters representing a path to the existing directory.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if(argumentsArray.length != 1) {
			env.writeln("Invalid number of arguments for the pushd command.");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(argumentsArray[0]);
		
		if (!Files.isDirectory(path)) {
			env.writeln("Invalid argument for the pushd command, should be existing directory");
		} else {
			Stack<Path> stack = null;
			
			if(env.getSharedData("cdstack") == null) {
				stack = new Stack<>();
			} else {
				stack = (Stack<Path>) env.getSharedData("cdstack");
			}
			stack.push(env.getCurrentDirectory());
			env.setSharedData("cdstack", stack);
			env.setCurrentDirectory(path);
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
