package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node which represents an entire document. Extends <code>Node</code>
 * @author Alen Carin
 *
 */
public class DocumentNode extends Node {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

}
