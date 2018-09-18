package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents Element containing constant int value.
 * @author Alen Carin
 *
 */
public class ElementConstantInteger extends Element {

	private int value;
	
	/**
	 * Constructor. Takes one int value.
	 * @param value
	 * 			of this element, must be int
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * Returns int value of this element.
	 * @return element value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns string representation of element's value.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
