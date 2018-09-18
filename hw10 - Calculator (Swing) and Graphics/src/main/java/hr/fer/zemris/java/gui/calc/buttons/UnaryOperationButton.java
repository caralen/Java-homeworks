package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;
import static java.lang.Math.asin;
import static java.lang.Math.acos;
import static java.lang.Math.atan;
import static java.lang.Math.log10;
import static java.lang.Math.log;
import static java.lang.Math.pow;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * The Class UnaryOperationButton represents all unary operations in button.
 */
public class UnaryOperationButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sin operation. */
	private static final DoubleUnaryOperator SIN = e -> sin(e);
	
	/** The cos operation. */
	private static final DoubleUnaryOperator COS = e -> cos(e);
	
	/** The tan operation. */
	private static final DoubleUnaryOperator TAN = e -> tan(e);
	
	/** The ctg operation. */
	private static final DoubleUnaryOperator CTG = e -> 1 / tan(e);
	
	/** The asin operation. */
	private static final DoubleUnaryOperator ASIN = e -> asin(e);
	
	/** The acos operation. */
	private static final DoubleUnaryOperator ACOS = e -> acos(e);
	
	/** The atan operation. */
	private static final DoubleUnaryOperator ATAN = e -> atan(e);
	
	/** The actg operation. */
	private static final DoubleUnaryOperator ACTG = e -> 1 / atan(e);
	
	/** The log operation. */
	private static final DoubleUnaryOperator LOG = e -> log10(e);
	
	/** The log inversed operation. */
	private static final DoubleUnaryOperator LOG_INV = e -> pow(10, e);
	
	/** The ln operation. */
	private static final DoubleUnaryOperator LN = e -> log(e);
	
	/** The ln inversed operation. */
	private static final DoubleUnaryOperator LN_INV = e -> pow(Math.E, e);
	
	/** The x inversed operation. */
	private static final DoubleUnaryOperator X_INV = e -> 1 / e;
	
	/** The name of the button. */
	private String name;
	
	/** The model. */
	private CalcModel model;
	
	/** The inversion check box. */
	private JCheckBox inversion;

	/**
	 * Instantiates a new unary operation button.
	 *
	 * @param name the name of the button
	 * @param model the model
	 * @param inversion the inversion
	 */
	public UnaryOperationButton(String name, CalcModel model, JCheckBox inversion) {
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
		OperatorPair operators = chooseOperators(name);
		
		addActionListener(e -> {
			try {
				if (inversion.isSelected()) {
					model.setValue(operators.inversed.applyAsDouble(model.getValue()));
				} else {
					model.setValue(operators.original.applyAsDouble(model.getValue()));
				}
			} catch(IllegalArgumentException ex) {
				model.clearAll();
				popupMessage("Cannot perform that operation.");
			}
		});
	}

	/**
	 * Creates a popup dialog box and shows message.
	 *
	 * @param message the message
	 */
	private void popupMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Determines which operation should be used by the button name.
	 *
	 * @param name the name of the button
	 * @return the operator pair
	 */
	private OperatorPair chooseOperators(String name) {
		
		switch(name) {
		
		case "sin":
			return new OperatorPair(SIN, ASIN);
			
		case "cos":
			return new OperatorPair(COS, ACOS);
			
		case "tan":
			return new OperatorPair(TAN, ATAN);
			
		case "ctg":
			return new OperatorPair(CTG, ACTG);
			
		case "log":
			return new OperatorPair(LOG, LOG_INV);
			
		case "ln":
			return new OperatorPair(LN, LN_INV);
		
		case "1/x":
			return new OperatorPair(X_INV, X_INV);
			
		default:
			throw new IllegalStateException("Invalid unary operator");
		}
	}
	
	/**
	 * Static class which represents a pair of inversed operators.
	 */
	private static class OperatorPair {
		
		/** The original operator. */
		private DoubleUnaryOperator original;
		
		/** The inversed operator. */
		private DoubleUnaryOperator inversed;
		
		/**
		 * Instantiates a new operator pair.
		 *
		 * @param original the original operator
		 * @param inversed the inversed operator
		 */
		public OperatorPair(DoubleUnaryOperator original, DoubleUnaryOperator inversed) {
			this.original = original;
			this.inversed = inversed;
		}
	}
}
