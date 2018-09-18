package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Class that is used for testing functionality of <code>Lexer</code>
 * @author Alen Carin
 *
 */
public class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull("Must not be null.", lexer.nextToken());
	}
	
	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals("EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test(expected = NullPointerException.class)
	public void getTokenWhenNotInitialised() {
		new Lexer("").getToken();
	}
	
	@Test
	public void testText() {
		Lexer lexer = new Lexer("This is sample text");
		
		assertEquals("EOF token.", TokenType.WORD, lexer.nextToken().getType());
	}
	
	@Test
	public void testFunction() {
		Lexer lexer = new Lexer("{$= @sin$}");
		
		assertEquals(TokenType.TAG_START, lexer.nextToken().getType());
		assertEquals(TokenType.SYMBOL, lexer.nextToken().getType());
		assertEquals(TokenType.FUNCTION, lexer.nextToken().getType());
		assertEquals(TokenType.TAG_END, lexer.nextToken().getType());
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testVariable() {
		Lexer lexer = new Lexer("{$= i$}");
		
		lexer.nextToken();
		lexer.nextToken();
		assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
	}
	
	@Test
	public void testOperator() {
		Lexer lexer = new Lexer("{$= +$}");
		
		lexer.nextToken();
		lexer.nextToken();
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
	}
	
	@Test
	public void testEscape() {
		Lexer lexer = new Lexer("\\{Test");
		assertEquals(TokenType.WORD, lexer.nextToken().getType());
		assertEquals("{Test", lexer.getToken().getValue().toString());
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testNull() {
		new Lexer(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullState() {
		new Lexer("").setState(null);
	}
}
