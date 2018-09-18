package hr.fer.zemris.java.hw07.shell.builder;

/**
 * Implementation of the NameBuilder which contains a constant text.
 * @author Alen Carin
 *
 */
public class ConstNameBuilder implements NameBuilder {

	/** The text. */
	private String text;
	
	/**
	 * Instantiates a new const name builder.
	 *
	 * @param text {@link #text}
	 */
	public ConstNameBuilder(String text) {
		this.text = text;
	}

	/**
	 * Appends the field value {@link #text} to the string builder in NameBuilderInfo.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(text);
	}

}
