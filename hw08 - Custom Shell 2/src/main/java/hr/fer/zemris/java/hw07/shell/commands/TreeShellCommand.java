package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
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
 * The TreeShellCommand is a command which is used to recursively print out the contents of the given directory.
 */
public class TreeShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("The tree command expects a single argument - directory name");
		description.add("Prints a tree (each directory level shifts output two charatcers to the right)");
	}

	/**
	 * Instantiates a new tree shell command.
	 */
	public TreeShellCommand() {
		name = "tree";
	}

	/**
	 * Prints a recursive tree structure of the files and folders in the given folder
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if(argumentsArray.length != 1) {
			env.writeln("Invalid number of arguments for the tree command");
			return ShellStatus.CONTINUE;
		}
		
		Path path = env.getCurrentDirectory().resolve(argumentsArray[0]);
		
		FileVisitor<Path> visitor = new MyFileVisitor(env);
		try {
			Files.walkFileTree(path, visitor);
		} catch (IOException e) {
			env.writeln("Cannot execute tree command. " + e.getMessage());
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
	
	/**
	 * The Class MyFileVisitor which extends the <code>SimpleFileVisitor</code> class.
	 * It defines behaviour while visiting files and directories.
	 */
	private static class MyFileVisitor extends SimpleFileVisitor<Path> {
		
		/** The reference to the <code>Environment</code> implementation. */
		private Environment env;
		
		/** The current depth in the directory tree. */
		private static int depth;
		
		/**
		 * Instantiates a new my file visitor.
		 *
		 * @param env {@link #env}
		 */
		public MyFileVisitor(Environment env) {
			this.env = env;
		}

		/**
		 * Prints out the directory file name as well as the padding in front.
		 * Increments the value of the depth field by one.
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln(getPadding() + dir.getFileName());
			depth++;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Prints out the name of the file
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(getPadding() + file.getFileName().toString());
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Terminates the tree walking.
		 */
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			env.writeln("Error while visiting file.");
			return FileVisitResult.TERMINATE;
		}

		/**
		 * Decrements the depth field value by one.
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			depth--;
			return FileVisitResult.CONTINUE;
		}
		
		/**
		 * Gets the padding.
		 *
		 * @return the padding
		 */
		private static String getPadding() {
			String padding = "";
			for(int i = 0; i < depth; i++) {
				padding += "  ";
			}
			return padding;
		}
	}

}
