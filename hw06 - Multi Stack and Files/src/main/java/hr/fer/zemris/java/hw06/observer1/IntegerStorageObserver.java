package hr.fer.zemris.java.hw06.observer1;

/**
 * This interface represents an Observer of the Observer Pattern.
 * Each time the value is changed observer does some kind of action.
 * @author Alen Carin
 *
 */
public interface IntegerStorageObserver {

	/**
	 * When the value is changed this method is called.
	 * The observer then has to do some action.
	 * @param istorage is a storage of integers.
	 */
	public void valueChanged(IntegerStorage istorage);
}
