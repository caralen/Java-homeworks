package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class which is used for testing <code>SmartScriptParser</code>
 * @author Alen Carin
 *
 */
public class SmartScriptTester {

	/**
	 * Method which is called upon start of the program.
	 * @param args
	 * 			arguments passed through command line, 
	 * 			should be only one argument which is path to txt file
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("You should provide only one command-line argument");
			System.exit(-1);
		}
		
		String docBody = null;
		SmartScriptParser parser = null;
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get(args[0])),
					StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			System.out.println("Cannot read from the file");
			System.exit(-1);
		}
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException ex) {
			System.out.println(ex.getMessage());
			System.exit(-1);
		} catch(Exception ex) {
			System.out.println("Exception which is not of type SmartScriptException");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		StringBuilder originalDocumentBody = new StringBuilder(); 
		try {
			originalDocumentBody = createOriginalDocumentBody(document, originalDocumentBody);
		} catch(SmartScriptParserException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(originalDocumentBody);
		System.out.println();
		
		
		String originalString = new String(originalDocumentBody);
		SmartScriptParser parser2 = new SmartScriptParser(originalString);
		DocumentNode document2 = parser2.getDocumentNode();
		
		StringBuilder originalDocumentBody2 = new StringBuilder(); 
		try {
			originalDocumentBody = createOriginalDocumentBody(document2, originalDocumentBody2);
		} catch(SmartScriptParserException ex) {
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
		System.out.println(originalDocumentBody2);
		
		System.out.println("\nEqual document bodies? " + originalDocumentBody.equals(originalDocumentBody2));
	}

	/**
	 * Static method used for creating document body (text) from given DocumentNode.
	 * @param node
	 * 			DocumentNode which is root of every syntactic tree made by <code>SmartScriptParser</code>
	 * @param originalDocumentBody
	 * 			StringBuilder which is used to save text from nodes
	 * @return text from nodes which parser generated, should be the same as original text
	 */
	private static StringBuilder createOriginalDocumentBody(Node node
			, StringBuilder originalDocumentBody) {
		
		if(node.getClass().equals(ForLoopNode.class)) {
			createForLoopBody(node, originalDocumentBody);
		}
		else if(node.getClass().equals(EchoNode.class)) {
			createEchoBody(node, originalDocumentBody);
		}
		else if(node.getClass().equals(TextNode.class)) {
			createTextBody(node, originalDocumentBody);
		}
		
		int numOfChildren = node.numberOfChildren();
		for(int i = 0; i < numOfChildren; i++) {
			createOriginalDocumentBody(node.getChild(i), originalDocumentBody);
		}
		checkIfForLoopEnd(node, originalDocumentBody);
		
		return originalDocumentBody;
	}

	/**
	 * Appends "{$END$}" on given text if for loop should be closed.
	 * @param node
	 * 			which is currently processed
	 * @param originalDocumentBody
	 * 			text to which this method appends string
	 */
	private static void checkIfForLoopEnd(Node node, StringBuilder originalDocumentBody) {
		if(node.getClass().equals(ForLoopNode.class)) {
			originalDocumentBody.append("{$END$}");
		}
	}

	/**
	 * Appends text from the given TextNode.
	 * @param node
	 * 			from which method takes text, must be TextNode
	 * @param originalDocumentBody
	 * 			text to which this method appends string
	 */
	private static void createTextBody(Node node, StringBuilder originalDocumentBody) {
		TextNode textNode = (TextNode) node;
		originalDocumentBody.append(textNode.getText());
	}

	/**
	 * Appends text from the given ForLoopNode
	 * @param node
	 * 			from which method takes text, must be ForLoopNode
	 * @param originalDocumentBody
	 * 			text to which this method appends string
	 * @throws SmartScriptParserException
	 * 			if there is an invalid number of arguments
	 */
	private static void createForLoopBody(Node node, StringBuilder originalDocumentBody) 
			throws SmartScriptParserException {
		
		ForLoopNode forLoopNode = (ForLoopNode) node;
		originalDocumentBody.append("{$FOR ");
		try {
			originalDocumentBody.append(forLoopNode.getVariable().asText()).append(" ");
			originalDocumentBody.append(forLoopNode.getStartExpression().asText()).append(" ");
			originalDocumentBody.append(forLoopNode.getEndExpression().asText()).append(" ");
		} catch(NullPointerException ex) {
			throw new SmartScriptParserException("Invalid number of arguments in for loop");
		}
		if(forLoopNode.getStepExpression() != null) {
			originalDocumentBody.append(forLoopNode.getStepExpression().asText());
		}
		originalDocumentBody.append("$}");
	}

	/**
	 * Appends text from the given EchoNode
	 * @param node
	 * 			from which method takes text, must be EchoNode
	 * @param originalDocumentBody
	 * 			text to which this method appends string
	 */
	private static void createEchoBody(Node node, StringBuilder originalDocumentBody) {
		EchoNode echoNode = (EchoNode) node;
		originalDocumentBody.append("{$=");
		for(Element element : echoNode.getElements()) {
			originalDocumentBody.append(element.asText()).append(" ");
		}
		originalDocumentBody.append("$}");
	}
}
