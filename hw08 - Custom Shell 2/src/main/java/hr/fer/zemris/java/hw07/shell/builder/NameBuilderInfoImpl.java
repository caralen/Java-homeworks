package hr.fer.zemris.java.hw07.shell.builder;

import java.util.regex.Matcher;

/**
 * Implementation of the <code>NameBuilderInfo</code>.
 * @author Alen Carin
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {
	
	/** Matches the given string. */
	private Matcher matcher;
	
	/** Represents text which represents a new name for the file. */
	private StringBuilder builder;

	
	/**
	 * Instantiates a new name builder info impl.
	 *
	 * @param matcher {@link #matcher}
	 */
	public NameBuilderInfoImpl(Matcher matcher) {
		builder = new StringBuilder();
		this.matcher = matcher;
	}

	@Override
	public StringBuilder getStringBuilder() {
		return builder;
	}

	@Override
	public String getGroup(int index) {
		return matcher.group(index);
	}

}
