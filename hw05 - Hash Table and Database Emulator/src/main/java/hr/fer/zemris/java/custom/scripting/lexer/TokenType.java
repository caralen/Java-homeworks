package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum which represents type of the token. 
 * @author Alen Carin
 *
 */
public enum TokenType {
	
	/**
	 * Indicates that there are no more tokens.
	 */
	EOF,

	/**
	 * Any attribute from the student record.
	 */
	ATTRIBUTE_NAME,
	
	/**
	 * Conditional operator.
	 */
	OPERATOR,
	
	/**
	 * String literal - any sequence of characters closed in quotation marks.
	 */
	LITERAL,
	
	/**
	 * Logical operator used for chaining conditional expressions.
	 */
	LOGICAL_OPERATOR
}
