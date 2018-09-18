package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a Subject of the Observer Pattern. 
 * It stores an integer and notifies observers when the integer is changed.
 * @author Alen Carin
 *
 */
public class IntegerStorage {

	/**Value of the integer which is stored in this class.*/
	private int value;
	
	/**List of observers which have to be notified when the integer is changed.*/
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Constructor which sets the value of the field integer to the value passed through arguments.
	 * @param value of the integer that will be stored.
	 */
	public IntegerStorage(int value) {
		this.value = value;
		observers = new ArrayList<>();
	}
	
	/**
	 * Adds the given observer to the inner list of observers.
	 * @param observer is an implementation of the <code>IntegerStorageObserver</code>.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Removes the observer from the inner list of observers.
	 * @param observer is an implementation of the <code>IntegerStorageObserver</code>.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Removes all observers from the inner list of observers.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Returns the integer value that is stored in this class.
	 * @return {@link #value}.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the value of the integer which is stored in this class 
	 * and notifies observers about the change.
	 * @param value of the integer which is stored in this class.
	 */
	public void setValue(int value) {
		if(this.value != value) {
			IntegerStorageChange istorageChange = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			
			if(observers != null) {
				Iterator<IntegerStorageObserver> iterator = observers.iterator();
				
				while(iterator.hasNext()) {
					IntegerStorageObserver observer = iterator.next();
					
					try {
						observer.valueChanged(istorageChange);
					} catch(IndexOutOfBoundsException e) {
						iterator.remove();
					}
				}
			}
		}
	}
}
