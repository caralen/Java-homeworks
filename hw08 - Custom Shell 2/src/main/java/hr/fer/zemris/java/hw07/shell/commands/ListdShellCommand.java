package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The ListdShellCommand is a shell commands that lists all paths stored in the stack.
 */
public class ListdShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		name = "listd";
		
		description = new LinkedList<>();
		description.add("The listd command lists all of the paths from the stack.");
		description.add("Each path in its own line");
	}

	/**
	 * Lists all of the path from the stack stored in shared data.
	 * Arguments must be empty string.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.length() == 0) {
			env.writeln("dropd command takes no arguments.");
		} else {
			Stack<Path> stack = (Stack<Path>) ((Stack<Path>) env.getSharedData("cdstack")).clone();
			
			while(!stack.empty()) {
				env.writeln(stack.pop().toString());
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
