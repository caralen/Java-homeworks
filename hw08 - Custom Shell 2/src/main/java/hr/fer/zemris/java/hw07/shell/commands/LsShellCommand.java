package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The LsShellCommand is a command used for printing out the list of files in the folder.
 */
public class LsShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("Command ls takes a single argument – directory");
		description.add("and writes a directory listing (not recursive).");
	}

	/**
	 * Instantiates a new ls shell command.
	 */
	public LsShellCommand() {
		name = "ls";
	}

	/**
	 * Lists all file names in the given directory.
	 * Arguments must be a sequence of characters containing no spaces (except in quotations).
	 * List of file names is printed in a specific format.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		if(argumentsArray.length != 1) {
			env.writeln("Invalid path for the ls command");
			return ShellStatus.CONTINUE;
		}
		
		Path path = env.getCurrentDirectory().resolve(argumentsArray[0]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(Files.isDirectory(path)) {
			try (Stream<Path> listFiles = Files.list(path)) {
				
				listFiles.forEach(s -> {
					
					try {
						BasicFileAttributeView faView = Files.getFileAttributeView(
								path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
						);
						BasicFileAttributes attributes = faView.readAttributes();
						FileTime fileTime = attributes.creationTime();
						String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
						
						env.writeln(
								(Files.isDirectory(s) ? "d" : "-") +
								(Files.isReadable(s) ? "r" : "-") +
								(Files.isWritable(s) ? "w" : "-") +
								(Files.isExecutable(s) ? "x" : "-") + 
								" " +
								String.format("%10d", Files.size(s)) +
								" " +
								(formattedDateTime) + 
								" " +
								s.getFileName().toString());
					} catch(IOException e) {
						env.writeln("ls command cannot be executed. " + e.getMessage());
					}
				});
			} catch (IOException e) {
				env.writeln("ls command cannot be executed. " + e.getMessage());
			}
		} else {
			env.writeln("Invalid arguments for the ls command");
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
