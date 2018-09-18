package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class which handles storage of turtle states.
 * It uses a stack to store states.
 * @author Alen Carin
 *
 */
public class Context {
	
	private ObjectStack stack;

	/**
	 * Default constructor which instantiates stack.
	 */
	public Context() {
		super();
		stack = new ObjectStack();
	}
	
	/**
	 * Returns the state which is currently at the top of the stack.
	 * @return current state at the top of the stack.
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}
	
	/**
	 * Pushes the given state to the stack.
	 * @param state is a reference to the TurtleState object which will be pushed on the stack.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Pops a state at the top of the stack.
	 */
	public void popState() {
		stack.pop();
	}
}
