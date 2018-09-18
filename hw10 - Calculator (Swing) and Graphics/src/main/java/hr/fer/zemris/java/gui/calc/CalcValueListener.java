package hr.fer.zemris.java.gui.calc;

/**
 * The listener interface for receiving calcValue events.
 * The class that is interested in processing a calcValue
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCalcValueListener<code> method. When
 * the calcValue event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CalcValueEvent
 */
public interface CalcValueListener {
	
	/**
	 * Value changed.
	 *
	 * @param model the model for calculator
	 */
	void valueChanged(CalcModel model);
}