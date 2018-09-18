package hr.fer.zemris.java.hw05.db;

/**
 * Expression which contains a student record field getter, string literal
 * and comparison operator. These 3 fields together represent a conditional expression.
 * @author Alen Carin
 *
 */
public class ConditionalExpression {

	/**Reference to an implementation of getter for certain student record field*/
	private IFieldValueGetter fieldGetter;
	
	/**A string containing characters and can contain a single * symbol.*/
	private String stringLiteral;
	
	/**Reference to an implementation of the comparison operator*/
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor. Sets field values to the values passed through arguments.
	 * @param fieldGetter {@link #fieldGetter}
	 * @param stringLiteral {@link #stringLiteral}
	 * @param comparisonOperator {@link #comparisonOperator}
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Returns a reference to the fieldGetter implementation
	 * @return {@link #fieldGetter}
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns a string literal
	 * @return {@link #stringLiteral}
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns a reference to the comparison operator implementation
	 * @return {@link #comparisonOperator}
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
