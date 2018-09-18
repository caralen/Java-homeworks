package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Interface INodeVisitor defines a visit method for each node that is generated 
 * by SmartScriptParser while parsing the file.
 */
public interface INodeVisitor {

	/**
	 * Visits text node and collects its text.
	 *
	 * @param node is an instance of TextNode
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Visits for loop node and collects its text.
	 *
	 * @param node is an instance of ForLoopNode
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Visits echo node and collects its text.
	 *
	 * @param node is an instance of EchoNode
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Visit document node and collects its text.
	 *
	 * @param node is an instance of DocumentNode
	 */
	public void visitDocumentNode(DocumentNode node);
}
