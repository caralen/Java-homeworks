package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.ClearButton;
import hr.fer.zemris.java.gui.calc.buttons.DecimalButton;
import hr.fer.zemris.java.gui.calc.buttons.EqualsButton;
import hr.fer.zemris.java.gui.calc.buttons.NumberButton;
import hr.fer.zemris.java.gui.calc.buttons.SignButton;
import hr.fer.zemris.java.gui.calc.buttons.StackButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperationButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * The Class Calculator which is a program that implements a simple on-screen calculator.
 */
public class Calculator extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new calculator.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(20, 20);
		setMinimumSize(new Dimension(750, 350));
		setSize(600, 350);
		initGUI();
	}

	/**
	 * Inits the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		
		CalcModelImpl model = new CalcModelImpl();
		
		JLabel label = new JLabel("0", SwingConstants.RIGHT);
		model.addCalcValueListener(e -> label.setText(String.valueOf(model.toString())));
		
		JCheckBox inversion = setupCheckBox(cp);
		createButtons(cp, model, inversion);
		colorAndFont(cp);
		setupLabel(cp, label);
	}
	
	/**
	 * Sets up check box.
	 *
	 * @param cp the container
	 * @return the new instance of check box
	 */
	private JCheckBox setupCheckBox(Container cp) {
		JCheckBox inversion = new JCheckBox("Inv");
		inversion.setSelected(false);
		cp.add(inversion, new RCPosition(5, 7));
		return inversion;
	}

	/**
	 * Sets up label.
	 *
	 * @param cp the container
	 * @param label the new label
	 */
	private void setupLabel(Container cp, JLabel label) {
		label.setOpaque(true);
		label.setFont(new Font("Arial", Font.PLAIN, 25));
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		label.setBackground(Color.decode("#ffcc00"));
		cp.add(label, new RCPosition(1, 1));
	}

	/**
	 * Sets color and font for all components in container.
	 *
	 * @param cp the container
	 */
	private void colorAndFont(Container cp) {
		Component[] components = cp.getComponents();
		for(Component c : components) {
			c.setFont(new Font("Arial", Font.PLAIN, 20));
			c.setBackground(Color.decode("#66b3ff"));
		}
	}

	/**
	 * Creates all buttons.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 * @param inversion the inversion check box
	 */
	private void createButtons(Container cp, CalcModelImpl model, JCheckBox inversion) {
		setupButtonNumbers(cp, model);
		setupBinaryButtons(cp, model, inversion);
		setupEqualsButton(cp, model);
		setupClearButtons(cp, model);
		setupStackButtons(cp, model);
		setupDecimalButton(cp, model);
		setupSignButton(cp, model);
		setupUnaryButtons(cp, model, inversion);
	}

	/**
	 * Sets up sign button.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 */
	private void setupSignButton(Container cp, CalcModelImpl model) {
		cp.add(new SignButton("+/-", model), new RCPosition(5, 4));
	}

	/**
	 * Sets up unary buttons.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 * @param inversion the inversion check box
	 */
	private void setupUnaryButtons(Container cp, CalcModelImpl model, JCheckBox inversion) {
		cp.add(new UnaryOperationButton("sin", model, inversion), new RCPosition(2, 2));
		cp.add(new UnaryOperationButton("cos", model, inversion), new RCPosition(3, 2));
		cp.add(new UnaryOperationButton("tan", model, inversion), new RCPosition(4, 2));
		cp.add(new UnaryOperationButton("ctg", model, inversion), new RCPosition(5, 2));
		
		cp.add(new UnaryOperationButton("log", model, inversion), new RCPosition(3, 1));
		cp.add(new UnaryOperationButton("ln", model, inversion), new RCPosition(4, 1));
		
		cp.add(new UnaryOperationButton("1/x", model, inversion), new RCPosition(2, 1));
	}

	/**
	 * Sets up decimal button.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 */
	private void setupDecimalButton(Container cp, CalcModelImpl model) {
		cp.add(new DecimalButton(".", model), new RCPosition(5, 5));
	}

	/**
	 * Sets up stack buttons.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 */
	private void setupStackButtons(Container cp, CalcModelImpl model) {
		Stack<Double> stack = new Stack<>();
		
		cp.add(new StackButton("push", model, stack), new RCPosition(3, 7));
		cp.add(new StackButton("pop", model, stack), new RCPosition(4, 7));
	}

	/**
	 * Sets up clear buttons.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 */
	private void setupClearButtons(Container cp, CalcModelImpl model) {
		cp.add(new ClearButton("clr", model), new RCPosition(1, 7));
		cp.add(new ClearButton("res", model), new RCPosition(2, 7));
	}

	/**
	 * Sets up equals button.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 */
	private void setupEqualsButton(Container cp, CalcModelImpl model) {
		cp.add(new EqualsButton("=", model), new RCPosition(1, 6));
	}

	/**
	 * Sets up binary buttons.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 * @param inversion the inversion check box
	 */
	private void setupBinaryButtons(Container cp, CalcModelImpl model, JCheckBox inversion) {
		cp.add(new BinaryOperationButton("+", model, null), new RCPosition(5, 6));
		cp.add(new BinaryOperationButton("-", model, null), new RCPosition(4, 6));
		cp.add(new BinaryOperationButton("*", model, null), new RCPosition(3, 6));
		cp.add(new BinaryOperationButton("/", model, null), new RCPosition(2, 6));
		
		cp.add(new BinaryOperationButton("x^n", model, inversion), new RCPosition(5, 1));
	}

	/**
	 * Sets up button numbers.
	 *
	 * @param cp the container
	 * @param model the model for the calculator
	 */
	private void setupButtonNumbers(Container cp, CalcModelImpl model) {
		int[] rowArray = {5, 4, 4, 4, 3, 3, 3, 2, 2, 2};
		int[] columnArray = {3, 3, 4, 5, 3, 4, 5, 3, 4, 5};
		
		NumberButton[] buttons = new NumberButton[10];
		for(int i = 0; i < 10; i++) {
			buttons[i] = new NumberButton(String.valueOf(i), model);
			cp.add(buttons[i], new RCPosition(rowArray[i], columnArray[i]));
		}
	}
	
	/**
	 * The main method which is called upon the start of the program.
	 * Instantiates a new calculator.
	 *
	 * @param args the command line arguments, not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Calculator calculator = new Calculator();
				calculator.setVisible(true);
			}
		});
	}
}
