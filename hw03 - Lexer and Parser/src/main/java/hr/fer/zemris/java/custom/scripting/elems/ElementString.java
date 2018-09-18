package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents text between quotation marks.
 * @author Alen Carin
 *
 */
public class ElementString extends Element {

	private String value;
	
	/**
	 * Constructor. Takes string value of text.
	 * @param value
	 * 			of text between quotation marks
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	/**
	 * Returns text value.
	 * @return text value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns String object of text value.
	 */
	@Override
	public String asText() {
		return "\"" + value + "\"";
	}
}
