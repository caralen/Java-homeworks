package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class represents one token from the given source code.
 * @author Alen Carin
 *
 */
public class Token {

	private TokenType type;
	private Object value;
	
	/**
	 * Constructor. Takes two parameters.
	 * @param type
	 * 			of this token
	 * @param value
	 * 			of this token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns value.
	 * @return value of the token.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns type,
	 * @return type of the token
	 */
	public TokenType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
