package hr.fer.zemris.java.hw06.observer1;

/**
 * Implementation of the <code>IntegerStorageObserver</code>.
 * Each time the value is changed this observer should print out the squared new value.
 * @author Alen Carin
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints out the original and squared value that is stored in the integer storage 
	 * which is passed through arguments.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		int squaredValue = istorage.getValue() * istorage.getValue();
		System.out.println("Provided new value: " + istorage.getValue() + ", square is " + squaredValue);
	}
}
