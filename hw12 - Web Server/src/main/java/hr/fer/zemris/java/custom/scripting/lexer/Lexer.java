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
	 * State defines the way lexer should process source code.
	 */
	private LexerState state;
	
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
		state = LexerState.IN_DOCUMENT_TEXT;
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
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		if(state == LexerState.IN_DOCUMENT_TEXT) {
			if(checkIfTagStart()) {
				int numberOfChars = 2;
				generateToken(TokenType.TAG_START, numberOfChars);
				state = LexerState.IN_TAG;
			}
			else if(checkIfSpace()) {
				generateSpaceToken();
			}
			else if(data[currentIndex] == '\\') {
				generateWordToken(TokenType.WORD);
			}
			else {
				generateWordToken(TokenType.WORD);
			}
		}
		
		else if(state == LexerState.IN_TAG) {
			skipSpaces();
			
			if(checkIfKeyWord()) {
				int numberOfChars = 3;
				generateToken(TokenType.KEYWORD, numberOfChars);
			}
			else if(checkIfVariable(currentIndex)) {
				generateWordToken(TokenType.VARIABLE);
			}
			else if(checkIfFunction()) {
				generateWordToken(TokenType.FUNCTION);
			}
			else if(checkIfNumber()) {
				generateNumberToken();
			}
			else if(checkIfOperator()) {
				if(ifMinusPartOfNumber()) {
					generateNumberToken();
				}
				else {
					int numberOfChars = 1;
					generateToken(TokenType.OPERATOR, numberOfChars);
				}
			}
			else if(checkIfTagEnd()) {
				int numberOfChars = 2;
				generateToken(TokenType.TAG_END, numberOfChars);
				state = LexerState.IN_DOCUMENT_TEXT;
			}
			else if(data[currentIndex] == '"') {
				if(checkIfString()) {
					generateWordToken(TokenType.STRING);
				}
				else {
					throw new LexerException("Invalid string argument");
				}
			}
			else {
				int numberOfChars = 1;
				generateToken(TokenType.SYMBOL, numberOfChars);
			}
		}
		else {
			if(checkIfString()) {
				generateWordToken(TokenType.STRING);
			}
			else {
				throw new LexerException("Invalid string argument");
			}
		}
		
		
	}

	/**
	 * Checks if there is a valid string behind quotation mark.
	 * @return
	 * 		true if string is valid.
	 */
	private boolean checkIfString() {
		if(data[currentIndex] != '"') return false;
		int counter = currentIndex+1;
		
		while(counter < data.length) {
			if(data[counter] == '\\') {
				if(!testEscapeValidity(counter)) return false;
			}
			else if(data[counter] == '"') return true;
			counter++;
		}
		return false;
	}

	/**
	 * Checks if a minus is part of the number or just a symbol.
	 * @return true if minus is part of the number
	 */
	private boolean ifMinusPartOfNumber() {
		if(data[currentIndex] == '-') {
			if(currentIndex+1 < data.length && Character.isDigit(data[currentIndex+1])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if next characters are a valid function name.
	 * @return true if function name is valid
	 */
	private boolean checkIfFunction() {
		if(data[currentIndex] != '@') return false;
		
		return checkIfVariable(currentIndex+1);
	}

	/**
	 * Check if next characters are a valid variable name.
	 * @param index 
	 * 			at which the function should start in array of characters
	 * @return true if there is a valid variable name
	 */
	private boolean checkIfVariable(int index) {
		if(!checkIfLetter(index)) return false;
		for(int i = index; i < data.length; i++) {
			if(Character.isWhitespace(data[i]) || data[i] == '$') break;
			else if(!Character.isLetter(data[i]) && !Character.isDigit(data[i]) && !(data[i] == '_')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Generates new token of type that is passed through arguments.
	 * @param type
	 * 			of the token that will be generated
	 * @param numberOfChars
	 * 			number of characters which token includes
	 */
	private void generateToken(TokenType type, int numberOfChars) {
		StringBuilder tokenString = new StringBuilder();
		
		for(int i = 0; i < numberOfChars; i++) {
			tokenString.append(data[currentIndex]);
			currentIndex++;
		}
		token = new Token(type, tokenString);
	}

	/**
	 * Checks if next two characters are a valid start of the tag.
	 * @return true if there is a valid tag start
	 */
	private boolean checkIfTagStart() {
		if(data[currentIndex] == '{') {
			if(currentIndex+1 < data.length && data[currentIndex+1] == '$') {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if next two characters are a valid end of the tag.
	 * @return true if there is a valid tag end
	 */
	private boolean checkIfTagEnd() {
		if(data[currentIndex] == '$') {
			if(currentIndex+1 < data.length && data[currentIndex+1] == '}') {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generates a new Token of type <code>SPACE</code>.
	 * @throws LexerException
	 */
	private void generateSpaceToken() throws LexerException {
		String space = "";
		
		while(currentIndex < data.length){
			
			if(checkIfSpace()) {
				space += data[currentIndex];
			}
			else {
				break;
			}
			currentIndex++;
		}
		
		if(!space.equals("")) {
			token = new Token(TokenType.WHITESPACE, space);
		}
		else {
			throw new LexerException("Invalid input");
		}
	}

	
	/**
	 * Generates a new Token of type <code>WORD</code>.
	 * @throws LexerException
	 * 				if the token cannot be generated
	 */
	private void generateWordToken(TokenType type) throws LexerException {
		String word = "";
		boolean seenEnd = false;
		
		while(currentIndex < data.length){
			if (data[currentIndex] == '{') {
				if (currentIndex + 1 < data.length && data[currentIndex + 1] == '$') break;
			} else if (data[currentIndex] == '$') {
				if (currentIndex + 1 < data.length && data[currentIndex + 1] == '}') break;
			}
			if (state == LexerState.IN_DOCUMENT_TEXT && testEscapeValidity(currentIndex)) {
				currentIndex++;
				word += data[currentIndex];
			} else if (!checkBlank() || (word.contains("\"") && !seenEnd)) {
				if (!word.equals("") && data[currentIndex] == '"') {
					seenEnd = true;
				}
				word += data[currentIndex];
			} else {
				break;
			}
			currentIndex++;
		}
		
		if(!word.equals("")) {
			token = new Token(type, word);
		}
		else {
			throw new LexerException("Invalid input");
		}
	}
	
	/**
	 * Generates a new Token from a number. 
	 * Number is made of characters in a row which are valid digits.
	 * @throws LexerException
	 * 				if the number is bigger than Long max value
	 */
	private void generateNumberToken() throws LexerException {
		String number = "" + data[currentIndex];
		Long tokenValue = null;
		
		while(currentIndex < data.length){
			currentIndex++;
			if(currentIndex == data.length) {
				break;
			}
			else if(checkIfNumber() || data[currentIndex] == '.') {
				number += data[currentIndex];
			}
			else {
				break;
			}
		}
		try {
			tokenValue = Long.parseLong(number);
		} catch(NumberFormatException ex) {
			throw new LexerException("Input is not valid");
		}
		token = new Token(TokenType.NUMBER, tokenValue);
	}
	
	/**
	 * Tests if the current character is escape character and is it valid escape character.
	 */
	private boolean testEscapeValidity(int index) {
		if(state == LexerState.IN_DOCUMENT_TEXT && data[index] == '\\'){
			if((index+1) >= data.length) throw new LexerException("Invalid escaping");
			if(data[index+1] == '{') return true;
			if(data[index+1] == '\\') return true;
			throw new LexerException("Invalid escaping");
		}
		else if(state == LexerState.IN_TAG && data[index] == '\\') {
			if((index+1) >= data.length) throw new LexerException("Invalid escaping");
			if(data[index+1] == '"') return true;
			if(data[index+1] == '\\') return true;
			if(Character.isLetter(data[index+1])) return true;
			if(Character.isDigit(data[index+1])) return true;
			throw new LexerException("Invalid escaping");
		}
		return false;
	}
	
	/**
	 * Checks if next characters are a valid keyword.
	 * @return true if it is a valid keyword
	 */
	private boolean checkIfKeyWord() {
		char[] testFor = {'F', 'O', 'R'};
		char[] testEnd = {'E', 'N', 'D'};
		if(testKeyword(testFor)) return true;
		else if(testKeyword(testEnd)) return true;
		else return false;
	}
	

	/**
	 * Checks if next characters are a valid keyword.
	 * @param testWord
	 * 			keyword which method is testing
	 * @return true if characters are the same as given keyword
	 */
	private boolean testKeyword(char[] testWord) {
		for(int i = 0; i < testWord.length; i++) {
			if(currentIndex+i == data.length) return false;
			if(Character.toUpperCase(data[currentIndex+i]) != testWord[i]) return false;
		}
		return true;
	}

	/**
	 * Checks if the current character is an operator.
	 * @return true if character is from the list of valid operators
	 */
	private boolean checkIfOperator() {
		switch(data[currentIndex]) {
		
		case '+':
			return true;
			
		case '-':
			return true;
			
		case '*':
			return true;
			
		case '/':
			return true;
			
		case '^':
			return true;
			
		default:
			return false;
		}
	}

	/**
	 * Checks if the currently processed character is a whitespace.
	 * @return
	 */
	private boolean checkIfSpace() {
		return Character.isWhitespace(data[currentIndex]);
	}

	/**
	 * Checks if the currently processed character is a letter.
	 * @return true if it is a letter
	 */
	private boolean checkIfLetter(int index) {
		return Character.isLetter(data[index]);
	}


	/**
	 * Checks if the currently processed character is a number
	 * @return true if it is a number
	 */
	private boolean checkIfNumber() {
		return Character.isDigit(data[currentIndex]);
	}
	
	/**
	 * Skips all blank spaces before the next character.
	 */
	private void skipSpaces() {
		while(currentIndex < data.length) {
			if(checkBlank()) {
				currentIndex++;
			}
			else {
				break;
			}
		}
	}

	/**
	 * Checks if a character is a blank space.
	 * @return true if next character to be processed is blank space
	 */
	private boolean checkBlank() {
		switch(data[currentIndex]) {
		
		case ' ':
			return true;
			
		case '\t':
			return true;
			
		case '\r':
			return true;
			
		case '\n':
			return true;
			
			
		default:
			return false;
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
	
	/**
	 * Sets the lexer in the desired method of processing.
	 * @param state
	 * 			in which the lexer should process source code.
	 * @throws NullPointerException
	 * 			if given state is null
	 */
	public void setState(LexerState state) throws NullPointerException {
		Objects.requireNonNull(state);
		this.state = state;
	}
}