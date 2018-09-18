package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes
 * @author Alen Carin
 *
 */
public abstract class Node {

	private ArrayIndexedCollection collection;
	
	/**
	 * Adds given child to an internally managed collection of children.
	 * @param child
	 */
	public void addChild(Node child) {
		if(collection == null) {
			collection = new ArrayIndexedCollection();
		}
		collection.add(child);
	}
	
	/**
	 * Returns the number of direct children of this node.
	 * @return number of direct children
	 */
	public int numberOfChildren() {
		if(collection == null) return 0;
		return collection.size();
	}
	
	/**
	 * Returns node which is this node's child, at given index.
	 * @param index
	 * 			at which the child is kept in node
	 * @return child of this node at given index
	 */
	public Node getChild(int index) {
		return (Node) collection.get(index);
	}
	
	/**
	 * Calls the appropriate visit method for this node on the visitor.
	 * @param visitor is an instance of INodeVisitor
	 */
	public abstract void accept(INodeVisitor visitor);
}
