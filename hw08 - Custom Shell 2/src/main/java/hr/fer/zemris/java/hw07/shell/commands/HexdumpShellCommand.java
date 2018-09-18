package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The HexdumpShellCommand is used for printing contents of the file in hex string format and normal characters.
 */
public class HexdumpShellCommand implements ShellCommand {
	
	/** The name of the command. */
	private String name;
	
	/** The description of the command. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("The hexdump command expects a single argument: file name.");
		description.add("It produces hex-output from the contents of the file.");
	}

	/**
	 * Instantiates a new hexdump shell command.
	 */
	public HexdumpShellCommand() {
		name = "hexdump";
	}

	/**
	 * Arguments must contain a single sequence of characters with no spaces (except in quotation marks).
	 * File is read 16 by 16 bytes and the contents are printed out both in hex string value and
	 * normal ascii character value. Each line of output represents 16 bytes.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (Util.splitArguments(arguments, env).length != 1) {
			env.writeln("Invalid number of arguments for the hexdump command");
			return ShellStatus.CONTINUE;
		}
		
		try (InputStream is = new BufferedInputStream(
				Files.newInputStream(env.getCurrentDirectory().resolve(arguments)))){
			
			byte[] buff1 = new byte[8];
			byte[] buff2 = new byte[8];
			int counter = 0;
			boolean lastLine = false;
			
			while (!lastLine) {
				int r1 = is.read(buff1);
				
				if (r1 < 0) {
					break;
				} 
				env.write(String.format("%08X: ", counter));
				
				byte[] smallerBytes1 = new byte[r1];
				for (int i = 0; i < r1; i++) {
					smallerBytes1[i] = buff1[i];
				}
				env.write(Util.byteToHex(smallerBytes1));
				
				for(int i = 0; i < (buff1.length-r1); i++) {
					env.write(" ");
					env.write(String.format("%2s", " "));
				}
				
				env.write("|");
				
				int r2 = is.read(buff2);
				
				byte[] smallerBytes2;
				if (r2 < 0) {
					lastLine = true;
					smallerBytes2 = new byte[0];
				} else {
					smallerBytes2 = new byte[r2];
				}
				for (int i = 0; i < smallerBytes2.length; i++) {
					smallerBytes2[i] = buff2[i];
				}
				env.write(Util.byteToHex(smallerBytes2));
				
				for(int i = 0; i < (buff2.length-smallerBytes2.length); i++) {
					env.write(String.format("%2s", " "));
					env.write(" ");
				}
				
				env.write("|");
				env.write(" ");
				
				char[] charArray1 = Util.byteToCharArray(smallerBytes1);
				charArray1 = Util.replaceUnsupported(charArray1);
				env.write(String.valueOf(charArray1));
				
				char[] charArray2 = Util.byteToCharArray(smallerBytes2);
				charArray2 = Util.replaceUnsupported(charArray2);
				env.write(String.valueOf(charArray2));
				
				counter += smallerBytes1.length + smallerBytes2.length;
				env.writeln("");
				} 
			
		} catch (IOException e) {
			env.writeln("Error in hexdump command. " + e.getMessage());
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
