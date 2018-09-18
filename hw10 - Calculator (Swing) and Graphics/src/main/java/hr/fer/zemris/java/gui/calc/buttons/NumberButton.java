package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * The Class NumberButton represents a number button.
 */
public class NumberButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The name of the button. */
	private String name;
	
	/** The model. */
	private CalcModel model;

	/**
	 * Instantiates a new number button.
	 *
	 * @param name the name of the button
	 * @param model the model
	 */
	public NumberButton(String name, CalcModel model) {
		super(name);
		this.name = name;
		this.model = model;
		addListener();
	}

	/**
	 * Adds action listener to this button, when the button is clicked certain operation will be performed.
	 */
	private void addListener() {
		addActionListener(e -> {
			model.insertDigit(Integer.parseInt(name));
		});
	}
}
