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
 * The CptreeShellCommand is a shell command used for copying a tree of files to another location.
 * @author Alen Carin
 *
 */
public class CptreeShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		name = "cptree";
		
		description = new LinkedList<>();
		description.add("The cptree command copies a the given tree of folders and files to another location.");
		description.add("Arguments must be 2 sequences of characters containing no spaces (except in quotations.");
	}

	/**
	 * Copies the given folder recursively.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if(argumentsArray.length != 2) {
			env.writeln("Invalid number of arguments for the cptree command.");
			return ShellStatus.CONTINUE;
		}
		
		Path pathSrc = env.getCurrentDirectory().resolve(argumentsArray[0]);
		Path pathDest = env.getCurrentDirectory().resolve(argumentsArray[1]);
		
		try {
			Files.walkFileTree(pathSrc, new SimpleFileVisitor<Path>() {
				
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					Files.createDirectories(pathDest.resolve(pathSrc).relativize(dir));
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.copy(file, pathDest.resolve(pathSrc.relativize(file)));
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			env.writeln("IOException happened while trying to execute cptree command. " + e.getMessage());
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
