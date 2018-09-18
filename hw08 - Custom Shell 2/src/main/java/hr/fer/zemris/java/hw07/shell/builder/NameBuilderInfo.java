package hr.fer.zemris.java.hw07.shell.builder;

/**
 * Keeps a string builder to which the name builders append their text, that should be the new file name.
 * Keeps a reference to the current matcher.
 * @author Alen Carin
 *
 */
public interface NameBuilderInfo {

	/**
	 * Returns the string builder containing the given pattern.
	 * @return reference to the StringBuilder, that contains the new name for the file.
	 */
	StringBuilder getStringBuilder();
	
	/**
	 * Returns the group that is in the matcher at the given index.
	 * @param index of the group.
	 * @return String representation of the group at the given index.
	 */
	String getGroup(int index);
}
