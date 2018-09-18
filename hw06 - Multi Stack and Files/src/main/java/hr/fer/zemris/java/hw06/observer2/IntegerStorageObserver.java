package hr.fer.zemris.java.hw06.observer2;

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
	 * @param istorageChange contains reference to integer storage, old value and new value.
	 */
	public void valueChanged(IntegerStorageChange istorageChange);
}
