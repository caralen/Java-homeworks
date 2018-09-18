package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * The Class CalcModelImpl is an implementation of a model used for calculator.
 */
public class CalcModelImpl implements CalcModel {
	
	/** The value. */
	String value = null;
	
	/** The active operand. */
	String activeOperand = null;
	
	/** The pending operation. */
	DoubleBinaryOperator pendingOperation = null;
	
	/** A list of listeners. */
	List<CalcValueListener> listeners = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	@Override
	public String toString() {
		return value == null ? "0" : value;
	}

	@Override
	public double getValue() {
		return value == null ? 0.0 : Double.parseDouble(value);
	}
	
	/**
	 * Sets the value to be the new value passed through arguments.
	 * Value must not be NaN nor infinity.
	 * Notifies listeners about the change
	 */
	@Override
	public void setValue(double value) {
		if(Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Value mustn't be NaN nor infinite");
		}
		this.value = String.valueOf(value);
		
		notifyListeners();
	}

	@Override
	public void clear() {
		value = null;
		
		notifyListeners();
	}

	/**
	 * Sets value and active operand to null.
	 */
	@Override
	public void clearAll() {
		clear();
		activeOperand = null;
		pendingOperation = null;
	}
	
	@Override
	public void swapSign() {
		if(value != null) {
			if(value.contains("-")) {
				value = value.replaceAll("-", "");
			} else {
				value = "-" + value;
			}
			notifyListeners();
		}
	}

	@Override
	public void insertDecimalPoint() {
		if (value == null) {
			value = "0.";
		} else if (!value.contains(".")) {
			value += ".";
		}
	}

	/**
	 * Inserts a new digit in value, notifies listeners.
	 */
	@Override
	public void insertDigit(int digit) {
		if (value == null || value.equals("0") || value.equals("0.0")) {
			value = String.valueOf(digit);
		} else if (!(Double.parseDouble(value + digit) > Double.MAX_VALUE)) {
			value = value + digit;
		}
		
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand == null ? false : true;
	}

	@Override
	public double getActiveOperand() {
		if (activeOperand == null) {
			throw new IllegalStateException("Active operand is not yet set.");
		} else {
			return Double.parseDouble(activeOperand);
		}
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = String.valueOf(activeOperand);
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}
	
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}
	
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	/**
	 * Notifies all listeners in the internal list of listeners that the value changed.
	 */
	private void notifyListeners() {
		for(CalcValueListener listener : listeners){
			listener.valueChanged(this);
		}
	}
}
