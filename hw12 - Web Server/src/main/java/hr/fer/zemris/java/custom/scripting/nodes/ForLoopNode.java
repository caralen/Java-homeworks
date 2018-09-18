package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. Extends <code>Node</code>.
 * @author Alen Carin
 *
 */
public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;	//can be null
	
	
	/**
	 * Constructor which takes three arguments, stepExpression is then initialised to null.
	 * @param variable
	 * 			which must be at the beginning of for loop
	 * @param startExpression
	 * 			expression which represents start of the for loop
	 * @param endExpression
	 * 			expression which represents end of the for loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}

	/**
	 * Constructor which takes four arguments.
	 * @param variable
	 * 			which must be at the beginning of for loop
	 * @param startExpression
	 * 			expression which represents start of the for loop
	 * @param endExpression
	 * 			expression which represents end of the for loop
	 * @param stepExpression
	 * 			expression which represents step of the for loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Returns variable of this for loop node.
	 * @return variable at the beginning of for loop
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Returns start expression of this for loop node.
	 * @return start expression, can be number or string
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Returns end expression of this for loop node.
	 * @return end expression, can be number or string
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Returns step expression of this for loop node.
	 * @return step expression, can be number or string
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
