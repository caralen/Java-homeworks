package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * The Interface CalcModel which represent a model for calculator.
 */
public interface CalcModel {
	
	/**
	 * Adds the calc value listener, listeners will be notified when there is a change.
	 *
	 * @param l the listener which will be added
	 */
	void addCalcValueListener(CalcValueListener l);
	
	/**
	 * Removes the calc value listener.
	 *
	 * @param l the listener which will be removed
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Returns a string representation of double value.
	 *
	 * @return the string value
	 */
	String toString();
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	double getValue();
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	void setValue(double value);
	
	/**
	 * Clears value, i.e. sets it to zero.
	 */
	void clear();
	
	/**
	 * Clear all.
	 */
	void clearAll();
	
	/**
	 * Swaps value's sign.
	 */
	void swapSign();
	
	/**
	 * Insert decimal point in value.
	 */
	void insertDecimalPoint();
	
	/**
	 * Insert new digit in value.
	 *
	 * @param digit the new digit in value
	 */
	void insertDigit(int digit);
	
	/**
	 * Checks if is active operand set.
	 *
	 * @return true, if is active operand set
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Gets the active operand.
	 *
	 * @return the active operand
	 */
	double getActiveOperand();
	
	/**
	 * Sets the active operand.
	 *
	 * @param activeOperand the new active operand
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Clear active operand.
	 */
	void clearActiveOperand();
	
	/**
	 * Gets the pending binary operation.
	 *
	 * @return the pending binary operation
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	
	/**
	 * Sets the pending binary operation.
	 *
	 * @param op the new pending binary operation
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}