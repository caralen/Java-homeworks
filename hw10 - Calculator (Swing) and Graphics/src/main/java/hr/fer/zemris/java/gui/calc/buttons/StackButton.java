package hr.fer.zemris.java.gui.calc.buttons;

import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * The Class StackButton represents a stack or pop button.
 */
public class StackButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The name of the button. */
	private String name;
	
	/** The model. */
	private CalcModel model;
	
	/** The stack for storing values. */
	private Stack<Double> stack;

	/**
	 * Instantiates a new stack button.
	 *
	 * @param name the name of the button
	 * @param model the model
	 * @param stack the stack
	 */
	public StackButton(String name, CalcModel model, Stack<Double> stack) {
		super(name);
		this.name = name;
		this.model = model;
		this.stack = stack;
		addListener();
	}

	/**
	 * Adds action listener to this button, when the button is clicked certain operation will be performed.
	 */
	private void addListener() {
			addActionListener(e -> {
				if(name.equals("push")) {
					stack.push(model.getValue());
				} else {
					try {
						model.setValue(stack.pop());
					} catch(EmptyStackException ex) {
						popupMessage("Cannot pop on an empty stack.");
					}
				}
			});
	}

	/**
	 * Creates a popup dialog box and shows message.
	 *
	 * @param message the message
	 */
	private void popupMessage(String message) {
		JOptionPane.showMessageDialog(
				this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
