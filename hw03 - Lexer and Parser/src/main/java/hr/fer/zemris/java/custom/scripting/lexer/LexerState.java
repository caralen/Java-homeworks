package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enables user to choose between rules of lexical analysis.
 * @author Alen Carin
 *
 */
public enum LexerState {

	/**
	 * Method of processing which is characteristic for document text.
	 */
	IN_DOCUMENT_TEXT,
	
	/**
	 * Method of processing which is characteristic for tags.
	 */
	IN_TAG
}
