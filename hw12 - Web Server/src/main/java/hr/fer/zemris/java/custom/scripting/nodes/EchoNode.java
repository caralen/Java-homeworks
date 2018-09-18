package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically.
 * Extends <code>Node</code>.
 * @author Alen Carin
 *
 */
public class EchoNode extends Node {

	private Element[] elements;
	
	/**
	 * Constructor which takes one argument, array of objects of type <code>Element</code>.
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Returns elements saved in this node.
	 * @return array of objects of type Element
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
