package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents mathematic function.
 * @author Alen Carin
 *
 */
public class ElementFunction extends Element {

	private String name;

	/**
	 * Constructor. Takes one parameter, string value of the function name.
	 * @param name
	 * 			of the function
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	/**
	 * Returns name of the function.
	 * @return function name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * String object containing function name.
	 */
	@Override
	public String asText() {
		return "@" + name;
	}
}
