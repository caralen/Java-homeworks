package hr.fer.zemris.java.hw06.observer2;

/**
 * Implementation of the <code>IntegerStorageObserver</code>.
 * Each time the value is changed this observer should print out the squared new value.
 * @author Alen Carin
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints out the original and squared value that is stored in the integer storage.
	 * @param istorageChange contains reference to integer storage, old value and new value.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		int value = istorageChange.getNewValue();
		int squaredValue = value * value;
		System.out.println("Provided new value: " + value + ", square is " + squaredValue);
	}
}
