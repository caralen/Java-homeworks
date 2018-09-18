package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node which represents a piece of textual data. Extends <code>Node</code>.
 * @author Alen Carin
 *
 */
public class TextNode extends Node {

	private String text;
	

	/**
	 * Constructor which takes one argument, String object.
	 * @param text
	 * 			which this TextNode will keep
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}


	/**
	 * Returns String object
	 * @return text which is object of type String
	 */
	public String getText() {
		return text;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
