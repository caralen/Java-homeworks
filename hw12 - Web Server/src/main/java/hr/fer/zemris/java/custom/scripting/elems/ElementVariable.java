package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents variable from the given source code.
 * @author Alen Carin
 *
 */
public class ElementVariable extends Element {

	private String name;
	
	/**
	 * Constructor. Takes string value of variable name.
	 * @param name
	 * 			of the variable
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}

	/**
	 * Returns name of the variable.
	 * @return variable name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * String value of variable name.
	 */
	@Override
	public String asText() {
		return name;
	}
}
