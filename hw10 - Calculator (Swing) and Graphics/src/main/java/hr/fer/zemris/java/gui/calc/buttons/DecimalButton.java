package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * The Class DecimalButton is an implementation of button that adds decimal point to the value.
 */
public class DecimalButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The model used in calculator. */
	private CalcModel model;

	/**
	 * Instantiates a new decimal button.
	 *
	 * @param name the name of the button
	 * @param model the model used in calculator
	 */
	public DecimalButton(String name, CalcModel model) {
		super(name);
		this.model = model;
		addListener();
	}

	/**
	 * Adds action listener to this button, when the button is clicked certain operation will be performed.
	 */
	private void addListener() {
		addActionListener(e -> {
			model.insertDecimalPoint();
		});
	}
}
