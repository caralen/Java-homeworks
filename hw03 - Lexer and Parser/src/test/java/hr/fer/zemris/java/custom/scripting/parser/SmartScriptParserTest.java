package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * This class is used to test functionality of the <code>SmartScriptPaser</code>
 * @author Alen Carin
 *
 */
public class SmartScriptParserTest {
	
	@Test(expected = SmartScriptParserException.class)
	public void testNull() {
		new SmartScriptParser(null);
	}
	
	@Test
	public void testNumberOfChildren1() {
		SmartScriptParser parser = new SmartScriptParser(getEntry("examples/doc1.txt"));
		
		assertEquals(parser.getDocumentNode().numberOfChildren(), 4);
	}
	
	@Test
	public void testNumberOfChildren2() {
		SmartScriptParser parser = new SmartScriptParser(getEntry("examples/doc1.txt"));
		
		assertEquals(parser.getDocumentNode().getChild(3).numberOfChildren(), 5);
	}
	
	@Test
	public void testNumberOfChildren3() {
		SmartScriptParser parser = new SmartScriptParser("");
		
		assertEquals(parser.getDocumentNode().numberOfChildren(), 0);
	}
	
	@Test
	public void testFirstChild() {
		SmartScriptParser parser = new SmartScriptParser(getEntry("examples/doc1.txt"));
		
		assertEquals(parser.getDocumentNode().getChild(0).getClass(), TextNode.class);
	}
	
	@Test
	public void testLastChild() {
		SmartScriptParser parser = new SmartScriptParser(getEntry("examples/doc1.txt"));
		
		assertEquals(parser.getDocumentNode().getChild(3).getClass(), ForLoopNode.class);
	}
	
	@Test
	public void addChild() {
		SmartScriptParser parser = new SmartScriptParser(getEntry("examples/doc1.txt"));
		parser.getDocumentNode().addChild(new Node());
		
		assertEquals(parser.getDocumentNode().numberOfChildren(), 5);
	}
	
	@Test
	public void testEchoNodeElement() {
		SmartScriptParser parser = new SmartScriptParser(getEntry("examples/doc1.txt"));
		EchoNode echoNode = (EchoNode) parser.getDocumentNode().getChild(3).getChild(3);
		
		assertEquals(echoNode.getElements().length, 6);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void forLoopNotClosed() {
		new SmartScriptParser(getEntry("examples/invalid1.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void tagNotClosed() {
		new SmartScriptParser(getEntry("examples/invalid2.txt"));
	}

	private static String getEntry(String entry) {
		try {
			return new String(
						Files.readAllBytes(Paths.get(entry))
						,StandardCharsets.UTF_8
						);
		} catch (IOException e) {
			System.out.println("Cannot read from the file");
			System.exit(-1);
		}
		return null;
	}
}
