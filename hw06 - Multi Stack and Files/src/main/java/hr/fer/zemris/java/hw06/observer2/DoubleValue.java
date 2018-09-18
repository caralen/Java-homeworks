package hr.fer.zemris.java.hw06.observer2;

/**
 * Implementation of the <code>IntegerStorageObserver</code>.
 * First n times the value has been changed this observer should
 * print out the doubled new value. 
 * After n times of being called the observer should no longer be notified.
 * @author Alen Carin
 */
public class DoubleValue implements IntegerStorageObserver {
	
	/**Number of times this observer should react.*/
	int numberOfIterations;
	
	/**Starting number of iterations to which this observer should react. */
	int startingOfNumberOfIterations;
	
	/**
	 * Constructor which sets field values to values passed through arguments.
	 * @param numberOfIterations {@link #numberOfIterations}.
	 */
	public DoubleValue(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
		this.startingOfNumberOfIterations = numberOfIterations;
	}

	/**
	 * Prints out the doubled value that is stored in the integer storage, 
	 * but only for the first {@link #startingOfNumberOfIterations} times the change has happened.
	 * @param istorageChange contains reference to integer storage, old value and new value.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		if (numberOfIterations > 0) {
			System.out.println("Double value: " + istorageChange.getNewValue() * 2);
		} else {
			throw new IndexOutOfBoundsException("Alredy done this action " + startingOfNumberOfIterations + " times!");
		}
		numberOfIterations--;
	}
}
