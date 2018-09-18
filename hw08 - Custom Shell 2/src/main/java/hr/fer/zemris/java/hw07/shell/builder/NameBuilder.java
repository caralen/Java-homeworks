package hr.fer.zemris.java.hw07.shell.builder;

/**
 * Represents a single name builder. There are three types of name builders:
 * <li>the first one keeps constant strings
 * <li>the second keeps a group which is referenced
 * <li>the third keeps a list of references to all the others name builders
 * @author Alen Carin
 *
 */
public interface NameBuilder {

	/**
	 * Appends text to already existing text.
	 * @param info
	 */
	public void execute(NameBuilderInfo info);
}
