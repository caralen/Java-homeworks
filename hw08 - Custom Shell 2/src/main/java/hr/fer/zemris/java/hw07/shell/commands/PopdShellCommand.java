package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The PopdShellCommand is a command used for popping a directory from the stack and setting
 * it as the current directory if it is valid.
 */
public class PopdShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		name = "popd";
		
		description = new LinkedList<>();
		description.add("The popd command pops current directory from the stack.");
		description.add("Then the current directory becomes the one that was poped from the stack.");
		description.add("It takes no arguments.");
	}

	/**
	 * Pops directory from the stack and sets it as the current directory.
	 * If the path popped from the stack is not an existing directory then the
	 * current directory stays the same as before.
	 * Arguments must be an empty string.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.length() == 0) {
			env.writeln("popd command takes no arguments.");
		} else {
			Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
			
			if (stack.empty()) {
				env.writeln("Cannot do popd command on an empty stack!");
			} else if (Files.isDirectory(stack.peek())) {
				env.setCurrentDirectory(stack.pop());
			} else {
				stack.pop();
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(description);
	}

}
