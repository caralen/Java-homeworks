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
	 * sequence of characters.
	 */
	WORD,
	
	/**
	 * sequence of digits.
	 */
	NUMBER,
	
	/**
	 * Everything that is not word, nor number.
	 */
	SYMBOL,
	
	/**
	 * Character representing mathematical operator.
	 */
	OPERATOR,
	
	/**
	 * Special word that always has the same meaning.
	 */
	KEYWORD,
	
	/**
	 * A word starting with letter and can consist numbers and special characters.
	 */
	VARIABLE,
	
	/**
	 * Word that represents mathematical function, starts with @ symbol.
	 */
	FUNCTION,
	
	/**
	 * Word or number surrounded with quotation marks.
	 */
	STRING,
	
	/**
	 * Any kind of blank space.
	 */
	WHITESPACE,
	
	/**
	 * A sequence of two special characters, "{$"
	 */
	TAG_START,
	
	/**
	 * A sequence of two special characters, "$}"
	 */
	TAG_END
}
