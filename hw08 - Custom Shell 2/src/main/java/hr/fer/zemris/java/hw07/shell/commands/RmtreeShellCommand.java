package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The RmtreeShellCommand is a shell command used for deleting the whole tree of folders and files.
 */
public class RmtreeShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		name = "rmtree";
		
		description = new LinkedList<>();
		description.add("Removes the directory passed through arguments and all of its subtrees.");
		description.add("Command rmtree takes one argument which is a path to an existing folder.");
	}

	/**
	 * Removes the directory passed through arguments and all of its subtrees.
	 * Command rmtree takes one argument which is a path to an existing folder.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if (argumentsArray.length != 1) {
			env.writeln("Invalid number of arguments for the rmtree command.");
			return ShellStatus.CONTINUE;
		}
		
		Path path = env.getCurrentDirectory().resolve(argumentsArray[0]);

		if (Files.isDirectory(path)) {

			try {
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				env.writeln("IOException happended while executing command rmtree " + e.getMessage());
			}
		}

		return null;
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
