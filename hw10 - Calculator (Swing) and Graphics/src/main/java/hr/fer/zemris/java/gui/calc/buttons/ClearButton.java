package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * The Class ClearButton is an implementation of button that clears value of the calculator.
 */
public class ClearButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The name of the button. */
	private String name;
	
	/** The model used in calculator. */
	private CalcModel model;

	/**
	 * Instantiates a new clear button.
	 *
	 * @param name the name of the button
	 * @param model the model used in calculator
	 */
	public ClearButton(String name, CalcModel model) {
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
			if (name.equals("clr")) {
				model.clear();
			} else {
				model.clearAll();
			}
		});
	}
}
