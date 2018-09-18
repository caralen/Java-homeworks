package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * The CopyShellCommand is used for copying a file to another location.
 */
public class CopyShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("The copy command expects two arguments: source file name and destination file name.");
		description.add("copy command works only with files (no directories).");
		description.add("Copies the file at the given path to the new directory");
	}

	/**
	 * Instantiates a new copy shell command.
	 */
	public CopyShellCommand() {
		name = "copy";
	}

	/**
	 * Arguments must contain two sequences of characters divided by spaces if not in quotation.
	 * First argument must be path to the certain file and the second can be a directory or the file as well.
	 * If the second arguments is a directory than the new file name will be the same as the original's.
	 * The original file is then copied to the new location.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if (argumentsArray.length != 2) {
			env.writeln("Invalid number of arguments for the copy command.");
		} else {
			Path pathSrc = Paths.get(argumentsArray[0]);
			Path pathDest = Paths.get(argumentsArray[1]);
			
			if(Files.isRegularFile(pathDest) && Files.exists(pathDest)) {
				env.writeln("File already exists, would you like to overrite it? (y/n)");
				if(env.readLine().equals("n")) {
					env.writeln("File will not be overwritten");
					return ShellStatus.CONTINUE;
				}
				else if(env.readLine().equals("y")) {
					env.writeln("File will be overwritten");
				}
				else {
					env.writeln("Invalid answer");
					return ShellStatus.CONTINUE;
				}
			}
				
			if(Files.isDirectory(pathSrc)) {
				env.writeln("First argument in copy command cannot be directory.");
			}
			
			if(Files.isDirectory(pathDest)) {
				pathDest = Paths.get(argumentsArray[1] + "\\" + pathSrc.getFileName());
			}
			
			try (InputStream is = new BufferedInputStream(Files.newInputStream(pathSrc));
					OutputStream os = new BufferedOutputStream(Files.newOutputStream(pathDest))) {
				
				byte[] buff = new byte[1024];
				
				while (true) {
					int r = is.read(buff);
					if (r < 0) {
						break;
					} 
					os.write(buff, 0, r);
				}
			} catch(IOException e) {
				env.writeln("Error in copy command. " + e.getMessage());
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
