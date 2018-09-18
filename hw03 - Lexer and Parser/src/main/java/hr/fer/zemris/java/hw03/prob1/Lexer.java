package hr.fer.zemris.java.hw03.prob1;

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
		state = LexerState.BASIC;
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
		
		if(state == LexerState.BASIC) {
			if(currentIndex == data.length) {
				token = new Token(TokenType.EOF, null);
			}
			else if(checkIfNumber()) {
				generateNumberToken();
			}
			else if(checkIfLetterOrEscapeChar()) {
				generateWordToken();
			}
			else {
				generateSymbolToken();
			}
		}
		else {
			if(currentIndex == data.length) {
				token = new Token(TokenType.EOF, null);
			}
			else if(checkIfNumber() || checkIfLetterOrEscapeChar()) {
				generateWordToken();
			}
			else {
				generateSymbolToken();
			}
		}
	}
	

	/**
	 * Generates a new Token from a symbol.
	 */
	private void generateSymbolToken() {
		checkHashSign();
		token = new Token(TokenType.SYMBOL, data[currentIndex]);
		currentIndex++;
	}

	/**
	 * Checks if the symbol is hash sign. If it is, changes the value of the state.
	 */
	private void checkHashSign() {
		if(data[currentIndex] == '#') {
			if(this.state == LexerState.BASIC) {
				setState(LexerState.EXTENDED);
			}
			else {
				setState(LexerState.BASIC);
			}
		}
	}

	/**
	 * Generates a new Token of type <code>WORD</code>.
	 * @throws LexerException
	 * 				if the token cannot be generated
	 */
	private void generateWordToken() throws LexerException {
		String word = "";
		
		if(this.state == LexerState.BASIC) {
			while(currentIndex < data.length){
				if(data[currentIndex] == '#') {
					break;
				}
				else if(testEscapeValidity()){
					currentIndex++;
					word += data[currentIndex];
				}
				else if(checkIfLetter()) {
					word += data[currentIndex];
				}
				else {
					break;
				}
				currentIndex++;
			}
		}
		else {
			while(currentIndex < data.length){
				if(data[currentIndex] == '#') {
					break;
				}
				if(!checkBlank()){
					word += data[currentIndex];
				}
				else {
					break;
				}
				currentIndex++;
			}
		}
		
		if(!word.equals("")) {
			token = new Token(TokenType.WORD, word);
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
			else if(checkIfNumber()) {
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
	private boolean testEscapeValidity() {
		if(data[currentIndex] == '\\'){
			if((currentIndex+1) >= data.length) return false;
			else if(Character.isLetter(data[currentIndex+1])) return false;
			
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks if the currently processed character is a letter or escape character.
	 * @return true if it is a letter
	 */
	private boolean checkIfLetterOrEscapeChar() {
		return Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\';
	}

	/**
	 * Checks if the currently processed character is a letter.
	 * @return true if it is a letter
	 */
	private boolean checkIfLetter() {
		return Character.isLetter(data[currentIndex]);
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
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets the lexer in the desired method of processing.
	 * @param state
	 * 			in which the lexer should process source code.
	 * @throws IllegalArgumentException
	 * 			if given state is null
	 */
	public void setState(LexerState state) throws IllegalArgumentException {
		Objects.requireNonNull(state);
		this.state = state;
	}
}
