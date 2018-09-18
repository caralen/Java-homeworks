package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * The Class SignButton represents a button for changing sign.
 */
public class SignButton extends JButton {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The model. */
	private CalcModel model;

	/**
	 * Instantiates a new sign button.
	 *
	 * @param name the name of the button
	 * @param model the model
	 */
	public SignButton(String name, CalcModel model) {
		super(name);
		this.model = model;
		addListener();
	}

	/**
	 * Adds action listener to this button, when the button is clicked certain operation will be performed.
	 */
	private void addListener() {
		addActionListener(e -> model.swapSign());
	}
}
