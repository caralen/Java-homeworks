package hr.fer.zemris.java.hw03.prob1;

/**
 * Enum which represents type of the token. 
 * Can be <i>eof</i>, <i>word</i>, <i>number</i> or <i>symbol</i>.
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
	SYMBOL
}
