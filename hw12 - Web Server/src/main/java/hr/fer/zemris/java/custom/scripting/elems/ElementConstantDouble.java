package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents Element containing constant double value.
 * @author Alen Carin
 *
 */
public class ElementConstantDouble extends Element {

	private double value;
	
	/**
	 * Constructor. Takes one double value.
	 * @param value
	 * 			of this element, must be double
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	/**
	 * Returns double value of this element.
	 * @return double value
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns a string representation of double value.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
