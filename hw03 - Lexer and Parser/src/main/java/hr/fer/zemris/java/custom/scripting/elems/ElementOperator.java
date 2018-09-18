package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents mathematic operator.
 * @author Alen Carin
 *
 */
public class ElementOperator extends Element {

	private String symbol;
	
	/**
	 * Constructor. Takes String object containing symbol of operation.
	 * @param symbol
	 * 			which represents mathematic operation
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * Returns symbol of mathematic operation
	 * @return symbol representing mathematic operation
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * String value of symbol for mathematic operation.
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
