package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Class which represents a parser. Parser creates an instance of <code>lexer</code> and 
 * creates a tree structure of nodes from tokens received by the <code>lexer</code>.
 * @author Alen Carin
 *
 */
public class SmartScriptParser {
	
	private Lexer lexer;
	private ObjectStack stack;
	private Node node;

	/**
	 * Constructor. Instantiates a new Lexer object with given text in argument and starts parsing.
	 * @param text
	 * 			which represents source code that should be parsed
	 */
	public SmartScriptParser(String text){
		if(text == null) throw new SmartScriptParserException("Input text must not be null");
		lexer = new Lexer(text);
		stack = new ObjectStack();
		try {
			parse();
		} catch(LexerException ex) {
			throw new SmartScriptParserException("Error in lexer" + ex.getMessage());
		}
	}

	/**
	 * Main method which is used for creating a node tree structure.
	 * Checks which type is the next token and creates an appropriate node.
	 */
	private void parse() {
		if(lexer == null) {
			throw new SmartScriptParserException("Lexer is null");
		}
		node = new DocumentNode();
		stack.push(node);
		lexer.nextToken();
		
		do {
			lexer.getToken();
			
			if(lexer.getToken().getType() == TokenType.EOF) {
				break;
			}
			else if(lexer.getToken().getType() == TokenType.WORD 
					|| lexer.getToken().getType() == TokenType.WHITESPACE) {
				createTextNode();
			}
			else if(lexer.getToken().getType() == TokenType.TAG_START) {
				if(lexer.nextToken().getType() == TokenType.SYMBOL) {
					createNewEchoNode();
				}
				else if(lexer.getToken().getValue().toString().equals("FOR")) {
					createNewForLoopNode();
				}
				else if(lexer.getToken().getValue().toString().equals("END")) {
					resolveEnd();
				}
				else {
					throw new SmartScriptParserException("Unhandled type of Token");
				}
			}
		} while(lexer.getToken().getType() != TokenType.EOF);
		
		if(stack.size() != 1) {
			throw new SmartScriptParserException("For loop is not closed");
		}
	}
	
	/**
	 * Resolves end of the for loop, i.e. pops the ForLoopNode from the stack if that is its end.
	 * @throws SmartScriptParserException
	 * 				If the next token is not the end tag
	 */
	private void resolveEnd() throws SmartScriptParserException {
		try {
			stack.pop();
			node = (Node) stack.peek();
			skipToken();	//skip END
			if(lexer.getToken().getType() == TokenType.TAG_END) {
				skipToken();
			}
			else {
				throw new SmartScriptParserException("Tag not closed");
			}
		} catch(EmptyStackException ex) {
			throw new SmartScriptParserException(ex.getMessage() + " Odd number of tags");
		}
	}

	/**
	 * Test if the Node on the top of the stack is a document node.
	 * If it is returns it, else throws exception.
	 * @return node of type DocumentNode
	 * @throws SmartScriptParserException
	 * 				if the node at the top of the stack isn't DocumentNode
	 */
	public DocumentNode getDocumentNode() throws SmartScriptParserException {
		try {
			return (DocumentNode) node;
		} catch(Exception ex) {
			throw new SmartScriptParserException("Not all for tags are closed");
		}
	}

	/**
	 * Skips the next token.
	 */
	private void skipToken() {
		lexer.nextToken();
	}

	/**
	 * Creates new <code>ForLoopNode</code> from the tokens returned by the <code>lexer</code>.
	 * @throws SmartScriptParserException
	 * 				if the for loop is not defined correctly
	 */
	private void createNewForLoopNode() throws SmartScriptParserException {
		if(lexer.nextToken().getType() != TokenType.VARIABLE){
			throw new SmartScriptParserException("Invalid for loop arguments");
		}
		Element[] elements = new Element[4];
		int elementCounter = 0;
		elements[elementCounter++] = new ElementVariable(lexer.getToken().getValue().toString());
		
		while(lexer.nextToken().getType() != TokenType.TAG_END && elementCounter <= 3) {
			if(lexer.getToken().getType() == TokenType.VARIABLE) {
				elements[elementCounter++] = new ElementVariable(lexer.getToken().getValue().toString());
			}
			else if(lexer.getToken().getType() == TokenType.NUMBER) {
				elements[elementCounter++] = generateNumberElement();
			}
			else if(lexer.getToken().getValue().equals("\"")) {
				if(lexer.nextToken().getType() == TokenType.NUMBER) {
					elements[elementCounter++] = generateNumberElement();
				}
				if(!lexer.nextToken().getValue().equals("\"")) {
					throw new SmartScriptParserException("Invalid for loop arguments");
				}
			}
			else {
				throw new SmartScriptParserException("Invalid for loop arguments");
			}
		}
		if(lexer.getToken().getType() != TokenType.TAG_END) {
			throw new SmartScriptParserException("For loop didn't end with right tag");
		}
		skipToken();
		Node forLoopNode;
		if(elementCounter <= 3) {
			forLoopNode = 
					new ForLoopNode((ElementVariable) elements[0], elements[1], elements[2]);
		}
		else {
			forLoopNode = 
					new ForLoopNode((ElementVariable) elements[0], elements[1], elements[2], elements[3]);
		}
		node.addChild(forLoopNode);
		node = forLoopNode;
		stack.push(node);
	}

	/**
	 * Generates new <code>ElementConstantDouble</code> or <code>ElementConstantInteger</code>
	 * @return element of type Element which is generated
	 */
	private Element generateNumberElement() {
		if (lexer.getToken().getValue().toString().contains(".")) {
			return new ElementConstantDouble(Double.parseDouble(lexer.getToken().getValue().toString()));
		}
		else {
			return new ElementConstantInteger(Integer.parseInt(lexer.getToken().getValue().toString()));
		}
	}

	/**
	 * Creates new EchoNode from the tokens returned by the <code>lexer</code>.
	 * @throws SmartScriptParserException
	 * 				if an empty tag is not defined correctly
	 */
	private void createNewEchoNode() throws SmartScriptParserException {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
		while (lexer.nextToken().getType() != TokenType.TAG_END) {

			switch (lexer.getToken().getType()) {
			case VARIABLE:
				collection.add(new ElementVariable(lexer.getToken().getValue().toString()));
				break;

			case NUMBER:
				collection.add(generateNumberElement());
				break;

			case STRING:
				collection.add(new ElementString(lexer.getToken().getValue().toString().replace("\"", "")));
				break;

			case FUNCTION:
//				Throw out "@" sign
				collection.add(new ElementFunction(lexer.getToken().getValue().toString().substring(1)));
				break;

			case OPERATOR:
				collection.add(new ElementOperator(lexer.getToken().getValue().toString()));
				break;

			default:
				throw new SmartScriptParserException("Cannot create EchoNode");
			}
		}
		lexer.nextToken();
		Element[] elements = new Element[collection.size()];
		for(int i = 0; i < collection.size(); i++) {
			elements[i] = (Element) collection.get(i);
		}
		Node echoNode = new EchoNode(elements);
		node.addChild(echoNode);
	}

	/**
	 * Creates new TextNode from the tokens returned by the <code>lexer</code>.
	 */
	private void createTextNode() {
		String text = "" + lexer.getToken().getValue();
		while(lexer.nextToken().getType() == TokenType.WORD ||
			  lexer.getToken().getType() == TokenType.WHITESPACE) {
			
			text += lexer.getToken().getValue();
		}
		Node textNode = new TextNode(text);
		node.addChild(textNode);
	}
}
