package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

// TODO: Auto-generated Javadoc
/**
 * The Environment Interface which defines methods for communication between shell and the user.
 */
public interface Environment {
	
	/**
	 * Reads a single line from standard input.
	 *
	 * @return the input string.
	 * @throws ShellIOException if there was an IO exception while reading
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes the given text to the standard output.
	 *
	 * @param text which will be written to the standard output
	 * @throws ShellIOException if there was an IO exception while writing
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes the given text to the standard output with the new line sign ('\n') at the end.
	 *
	 * @param text which will be written to the standard output
	 * @throws ShellIOException if there was an IO exception while writing
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns a read-only sorted map of available shell commands.
	 *
	 * @return the sorted map of commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Gets the multiline symbol.
	 *
	 * @return the multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol.
	 *
	 * @param symbol the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Gets the prompt symbol.
	 *
	 * @return the prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol.
	 *
	 * @param symbol the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Gets the morelines symbol.
	 *
	 * @return the morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the morelines symbol.
	 *
	 * @param symbol the new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Gets the current directory.
	 *
	 * @return the current directory
	 */
	Path getCurrentDirectory();
	
	/**
	 * Sets the current directory.
	 *
	 * @param path to the current directory
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * Gets the shared data.
	 *
	 * @param key the key
	 * @return the shared data
	 */
	Object getSharedData(String key);
	
	/**
	 * Sets the shared data.
	 *
	 * @param key the key
	 * @param value the value
	 */
	void setSharedData(String key, Object value);

}
