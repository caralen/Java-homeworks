package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModel;

import static java.lang.Math.pow;

/**
 * The Class BinaryOperationButton is an implementation of button for buttons that use binary operations.
 */
public class BinaryOperationButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant ADD, adds two numbers. */
	private static final DoubleBinaryOperator ADD = (e1, e2) -> e1 + e2;
	
	/** The Constant SUB, subs two numbers. */
	private static final DoubleBinaryOperator SUB = (e1, e2) -> e1 - e2;
	
	/** The Constant MULTIPLY, multiplies two numbers. */
	private static final DoubleBinaryOperator MULTIPLY = (e1, e2) -> e1 * e2;
	
	/** The Constant DIVIDE, divides two numbers. */
	private static final DoubleBinaryOperator DIVIDE = (e1, e2) -> e1 / e2;
	
	/** The Constant POW, first to the power of second number. */
	private static final DoubleBinaryOperator POW = (e1, e2) -> pow(e1, e2);
	
	/** The Constant POW_INV, first number's root of the second number. */
	private static final DoubleBinaryOperator POW_INV = (e1, e2) -> pow(e1, 1/e2);
	
	/** The name of the button. */
	private String name;
	
	/** The model used in calculator. */
	private CalcModel model;
	
	/** The inversion check box. */
	private JCheckBox inversion;

	/**
	 * Instantiates a new binary operation button.
	 *
	 * @param name the name of the button
	 * @param model the model used in calculator
	 * @param inversion the inversion check box
	 */
	public BinaryOperationButton(String name, CalcModel model, JCheckBox inversion) {
		super(name);
		this.name = name;
		this.model = model;
		this.inversion = inversion;
		addListener();
	}

	/**
	 * Adds action listener to this button, when the button is clicked certain operation will be performed.
	 */
	private void addListener() {
		addActionListener(e -> {
			DoubleBinaryOperator operator = chooseOperator();
			
			if(operator == POW && inversion.isSelected()) {
				operator = POW_INV;
			}
			
			if (model.isActiveOperandSet()) {
				model.setActiveOperand(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.setPendingBinaryOperation(operator);
				model.clear();
			} else {
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(operator);
				model.clear();
			}
			
		});
	}

	/**
	 * Returns double binary operator which suits the button name.
	 *
	 * @return the double binary operator
	 */
	private DoubleBinaryOperator chooseOperator() {
		switch(name) {
		case "+":
			return ADD;
			
		case "-":
			return SUB;
			
		case "*":
			return MULTIPLY;
			
		case "/":
			return DIVIDE;
			
		case "x^n":
			return POW;
			
		default:
			throw new IllegalStateException("Invalid binary operator");
		}
	}
}
