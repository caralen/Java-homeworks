package hr.fer.zemris.java.hw06.observer2;

/**
 * Holds read-only properties of <code>IntegerStorage</code>, 
 * value before the change happened and the new value.
 * @author Alen Carin
 *
 */
public class IntegerStorageChange {

	/**Storage of integers.*/
	private IntegerStorage istorage;
	
	/**Value that was stored in the integer storage before the change happened.*/
	private int valueBeforeChange;
	
	/**The new value which is stored in the integer storage.*/
	private int newValue;
	
	/**
	 * Constructor which sets the field values to values passed through arguments.
	 * @param istorage {@link #istorage}
	 * @param valueBeforeChange {@link #valueBeforeChange}
	 * @param newValue {@link #newValue}
	 */
	public IntegerStorageChange(IntegerStorage istorage, int valueBeforeChange, int newValue) {
		this.istorage = istorage;
		this.valueBeforeChange = valueBeforeChange;
		this.newValue = newValue;
	}
	
	/**
	 * Returns a reference to the integer storage.
	 * @return {@link #istorage}
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}
	
	/**
	 * Returns the value which was stored in the integer storage before the change happened.
	 * @return {@link #valueBeforeChange}
	 */
	public int getValueBeforeChange() {
		return valueBeforeChange;
	}
	
	/**
	 * Returns the new value which is stored in the integer storage.
	 * @return {@link #newValue}
	 */
	public int getNewValue() {
		return newValue;
	}
}
