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
 * The DropdShellCommand is a shell command that is used for dropping the first directory from the stack.
 */
public class DropdShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		name = "dropd";
		description = new LinkedList<>();
		description.add("The dropd command pops directory from the stack.");
		description.add("It takes no argumenta.");
	}
	

	/**
	 * If the stack in shared data is not empty than the first directory is dropped.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.length() == 0) {
			env.writeln("dropd command takes no arguments.");
		} else {
			Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
			
			if (stack.empty()) {
				env.writeln("Cannot do dropd command on an empty stack!");
			} else {
				stack.pop();
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
