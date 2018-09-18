package hr.fer.zemris.java.hw07.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.*;

/**
 * Class which represents a shell program.
 * @author Alen Carin
 *
 */
public class MyShell {
	

	/**
	 * Method which is called upon the start of the program.
	 * @param args command line arguments, not used here.
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Environment env = new EnvironmentImpl(sc);
		
		env.writeln("Welcome to MyShell v1.0");
		
		ShellStatus status = ShellStatus.CONTINUE;
		
		while(status != ShellStatus.TERMINATE) {
			env.write(env.getPromptSymbol() + " ");
			String lines = env.readLine();
			
			ShellCommand command = null;
			String arguments = "";
			
			while(lines.endsWith(" " + env.getMorelinesSymbol())) {
				lines = lines.substring(0, lines.length()-1);
				env.write(env.getMultilineSymbol() + " ");
				lines += env.readLine();
			}
			String[] parts = lines.split("\\s+");
			for(int i = 1; i < parts.length; i++) {
				arguments += parts[i] + " ";
			}
			arguments = arguments.substring(0, arguments.length() > 0 ? arguments.length()-1 : 0);
			
			command = env.commands().get(parts[0]);
			
			if(command != null) {
				status = command.executeCommand(env, arguments);
			} else {
				env.writeln("Invalid command");
				continue;
			}
		}
		sc.close();
	}
	
	/**
	 * Implementation of the <code>Environment</code> interface.
	 * @author Alen Carin
	 *
	 */
	private static class EnvironmentImpl implements Environment {
		
		/** The prompt symbol. */
		private char promptSymbol;
		
		/** The more lines symbol. */
		private char moreLinesSymbol;
		
		/** The multi line symbol. */
		private char multiLineSymbol;
		
		/** A sorted map of available commands. */
		private SortedMap<String, ShellCommand> commands;
		
		/** The scanner for read from the console. */
		Scanner sc;
		
		/**
		 * Instantiates a new environment impl.
		 *
		 * @param sc {@link #sc}
		 */
		public EnvironmentImpl(Scanner sc) {
			this.sc = sc;
			promptSymbol = '>';
			moreLinesSymbol = '\\';
			multiLineSymbol = '|';
			commands = initialiseCommands(commands);
		}
		
		@Override
		public String readLine() throws ShellIOException {
			return sc.nextLine();
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.format("%s", text);
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public Character getMultilineSymbol() {
			return multiLineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multiLineSymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			moreLinesSymbol = symbol;
		}
		
	}


	/**
	 * Initialises commands.
	 *
	 * @param commands {@link #commands}
	 * @return sorted map of available commands.
	 */
	private static SortedMap<String, ShellCommand> initialiseCommands(SortedMap<String, ShellCommand> commands) {
		commands = new TreeMap<>();
		
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		
		return commands;
	}
}
