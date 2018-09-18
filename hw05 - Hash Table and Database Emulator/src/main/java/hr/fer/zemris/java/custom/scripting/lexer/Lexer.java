package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;


/**
 * Class which takes program written in a simple programming language 
 * and splits it into tokens.
 * @author Alen Carin
 *
 */
public class Lexer {

	/**
	 * Array of characters which represents source code of the program.
	 */
	private char[] data;
	/**
	 * The last generated token
	 */
	private Token token;
	/**
	 * Index of the first not processed character in the array <code>data</code>.
	 */
	private int currentIndex;
	
	
	/**
	 * Constructor. Takes String object containing source code, must not be null.
	 * @param text 
	 * 			source code
	 * @throws NullPointerException if source code is null
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);
		
		data = text.toCharArray();
		currentIndex = 0;
	}
	
	/**
	 * Returns newly generated token from the given source code.
	 * @return token
	 * 			generated from source code
	 * @throws LexerException if the token cannot be generated
	 */
	public Token nextToken() throws LexerException{
		extractNextToken();
		return getToken();
	}
	
	/**
	 * Method which extracts next token from the array of characters 
	 * or throws exception if it is not possible.
	 * @throws LexerException
	 * 			if the end of file was already reached
	 */
	private void extractNextToken() throws LexerException {
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Already reached the end of file!");
		}
		skipSpaces();
		
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		if(isAttributeName()) {
			createAttributeName();
		}else if(isOperator()) {
			createOperator();
		}else if(isLiteral()) {
			createLiteral();
		}else if(isLogicalOperator()) {
			createLogicalOperator();
		}
		else {
			throw new LexerException("Query is invalid");
		}
	}

	/**
	 * Creates an attribute name token.
	 */
	private void createAttributeName() {
		int length;
		if(data[currentIndex] == 'f') {
			length = 9;
		}else if(data[currentIndex] == 'l') {
			length = 8;
		}else {
			length = 5;
		}
		String attributeName = new String();
		
		for(int i = 0; i < length; i++) {
			attributeName += data[currentIndex++];
		}
		
		token = new Token(TokenType.ATTRIBUTE_NAME, attributeName);
	}

	/**
	 * Checks if there is an attribute name in the next sequence of characters.
	 * @return true if there is an attribute name.
	 */
	private boolean isAttributeName() {
		String[] attributeNames = new String[] {
				"firstName",
				"lastName",
				"jmbag"
		};
		
		for(int i = 0; i < attributeNames.length; i++) {
			boolean isValid = true;
			for(int j = 0; j < attributeNames[i].length(); j++) {
				if(data[currentIndex + j] != attributeNames[i].charAt(j)) {
					isValid = false;
					break;
				}
			}
			if(isValid) return true;
		}
		return false;
	}

	/**
	 * Creates an operator token.
	 */
	private void createOperator() {
		String operator = new String();
		
		if (data[currentIndex] == '<') {
			if (data[currentIndex + 1] == '=') {
				operator = "" + 
						data[currentIndex++] + 
						data[currentIndex++];
			} else {
				operator = "" + data[currentIndex++];
			}
		} else if (data[currentIndex] == '>') {
			if (data[currentIndex + 1] == '=') {
				operator = "" + 
						data[currentIndex++] + 
						data[currentIndex++];
			} else {
				operator = "" + data[currentIndex++];
			}
		} else if (data[currentIndex] == '=') {
			operator = "" + data[currentIndex++];
		} else if (data[currentIndex] == '!') {
			operator = "" + 
					data[currentIndex++] + 
					data[currentIndex++];
		} else {
			operator = "" + 
					data[currentIndex++] + 
					data[currentIndex++] + 
					data[currentIndex++] + 
					data[currentIndex++];
		}
		token = new Token(TokenType.OPERATOR, operator);
	}

	/**
	 * Checks if there is an operator in the next sequence of characters.
	 * @return true if there is an operator in the next sequence of characters.
	 */
	private boolean isOperator() {
		switch(data[currentIndex]) {
		
		case '<':
			return true;
			
		case '>':
			return true;
			
		case '=':
			return true;
		}
		if (data[currentIndex] == 'L' && data[currentIndex + 1] == 'I' && data[currentIndex + 2] == 'K'
				&& data[currentIndex + 3] == 'E') {
			return true;
		} else if (data[currentIndex] == '!' && data[currentIndex + 1] == '=') {
			return true;
		}
		return false;
	}

	/**
	 * Creates an string literal token.
	 */
	private void createLiteral() {
		String literal = new String();
		currentIndex++;
		
		while(data[currentIndex] != '"') {
			literal += data[currentIndex++];
		}
		currentIndex++;
		token = new Token(TokenType.LITERAL, literal);
	}

	/**
	 * Checks if there is a valid literal behind quotation mark.
	 * @return true if literal is valid.
	 */
	private boolean isLiteral() {
		if(data[currentIndex] != '"') return false;
		int tempIndex = currentIndex + 1;
		
		while(tempIndex < data.length) {
			if (Character.isWhitespace(data[tempIndex])) {
				return false;
			} else if (data[tempIndex] != '"') {
				tempIndex++;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a logical operator token.
	 */
	private void createLogicalOperator() {
		String logicalOperator = "" + data[currentIndex++] + data[currentIndex++] + data[currentIndex++];
		token = new Token(TokenType.LOGICAL_OPERATOR, logicalOperator);
	}

	private boolean isLogicalOperator() {
		if(data[currentIndex] != 'A' && data[currentIndex] != 'a') {
			return false;
		}
		if(data[currentIndex+1] != 'N' && data[currentIndex+1] != 'n') {
			return false;
		}
		if(data[currentIndex+2] != 'D' && data[currentIndex+2] != 'd') {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the currently processed character is a whitespace.
	 * @return
	 */
	private boolean checkIfSpace() {
		return Character.isWhitespace(data[currentIndex]);
	}
	
	/**
	 * Skips all blank spaces before the next character.
	 */
	private void skipSpaces() {
		while(currentIndex < data.length) {
			if(checkIfSpace()) {
				currentIndex++;
			}
			else {
				break;
			}
		}
	}

	/**
	 * Returns last generated token.
	 * @return token
	 * 				which was last generated.
	 * @throws LexerException
	 * 				if the token was not yet generated
	 */
	public Token getToken() throws LexerException {
		Objects.requireNonNull(token, "Cannot call getToken() when token is not yet initialised");
		return token;
	}
}