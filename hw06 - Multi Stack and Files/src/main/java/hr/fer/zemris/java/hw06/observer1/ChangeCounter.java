package hr.fer.zemris.java.hw06.observer1;

/**
 * Implementation of the <code>IntegerStorageObserver</code>.
 * Each time the value is changed this observer should print out 
 * the number of times the value has been changed up until now.
 * @author Alen Carin
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	/**Counter of the times the value has been changed.*/
	private int counter;

	/**
	 * Prints out the number of times this method has been called.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of values changed since tracking " + counter);
	}
}
