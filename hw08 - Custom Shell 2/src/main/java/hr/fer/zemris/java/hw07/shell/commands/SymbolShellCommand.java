package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

/**
 * The SymbolShellCommand is a command used for controlling the shell symbols.
 * 
 */
public class SymbolShellCommand implements ShellCommand {
	
	/** The prompt string. */
	private static String PROMPT = "PROMPT";
	
	/** The morelines string. */
	private static String MORELINES = "MORELINES";
	
	/** The multiline string. */
	private static String MULTILINE = "MULTILINE";
	
	/** The name value. */
	private String name;
	
	/** List of description strings. */
	private List<String> description;
	
	{
		description = new LinkedList<>();
		description.add("Command symbol that can be used to change symbols.");
	}

	/**
	 * Instantiates a new symbol shell command.
	 */
	public SymbolShellCommand() {
		name = "symbol";
	}

	/**
	 * Arguments must be one or two sequences of characters which are divided by spaces (except in quotations).
	 * If arguments is a single sequence which is equal to one of three symbol names then that symbol is printed.
	 * If arguments contains two sequences then the second must be a single character to which the symbol
	 * value will be changed.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsArray = Util.splitArguments(arguments, env);
		
		if (argumentsArray.length < 1 || argumentsArray.length > 2) {
			env.writeln("Invalid number of arguments for the symbol command.");
		} else if (argumentsArray.length == 1) {
			if(argumentsArray[0].equals(PROMPT)) {
				env.writeln("Symbol for " + PROMPT + " is " + "'" + env.getPromptSymbol() + "'");
			} else if(argumentsArray[0].equals(MORELINES)) {
				env.writeln("Symbol for " + MORELINES + " is " + "'" + env.getMorelinesSymbol() + "'");
			} else if(argumentsArray[0].equals(MULTILINE)) {
				env.writeln("Symbol for " + MULTILINE + " is " + "'" + env.getMultilineSymbol() + "'");
			} else {
				env.writeln("Invalid name for the symbol.");
			}
		} else {
			if(argumentsArray[1].length() != 1) {
				env.writeln("Symbol must be a single character.");
				return ShellStatus.CONTINUE;
			}
			char newSymbol = argumentsArray[1].charAt(0);
			
			if(argumentsArray[0].equals(PROMPT)) {
				env.writeln("Symbol for " + PROMPT + " changed from " + "'" + env.getPromptSymbol() + "' to " 
						+ "'" + newSymbol + "'");
				env.setPromptSymbol(newSymbol);
			} else if(argumentsArray[0].equals(MORELINES)) {
				env.writeln("Symbol for " + MORELINES + " changed from " + "'" + env.getMorelinesSymbol() + "' to " 
						+ "'" + newSymbol + "'");
				env.setMorelinesSymbol(newSymbol);
			} else if(argumentsArray[0].equals(MULTILINE)) {
				env.writeln("Symbol for " + MULTILINE + " changed from " + "'" + env.getMultilineSymbol() + "' to " 
						+ "'" + newSymbol + "'");
				env.setMultilineSymbol(newSymbol);
			} else {
				env.writeln("Invalid name for the symbol.");
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
