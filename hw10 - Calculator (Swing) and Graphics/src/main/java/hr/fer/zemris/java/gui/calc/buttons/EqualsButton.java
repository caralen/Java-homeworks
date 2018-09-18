package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * The Class EqualsButton is an implementation of equals button.
 */
public class EqualsButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The model. */
	private CalcModel model;

	/**
	 * Instantiates a new equals button.
	 *
	 * @param name the name of the button
	 * @param model the model
	 */
	public EqualsButton(String name, CalcModel model) {
		super(name);
		this.model = model;
		addListener();
	}

	/**
	 * Adds action listener to this button, when the button is clicked certain operation will be performed.
	 */
	private void addListener() {
		addActionListener(e -> {
			
			try {
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				double value = model.getValue();
				model.clearAll();
				model.setValue(value);
			} catch (RuntimeException ex) {
				model.clearAll();
				popupMessage("Invalid arithmetic operation.");
			}
		});
	}

	/**
	 * Sets popup message.
	 *
	 * @param message the message that should be showed
	 */
	private void popupMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
