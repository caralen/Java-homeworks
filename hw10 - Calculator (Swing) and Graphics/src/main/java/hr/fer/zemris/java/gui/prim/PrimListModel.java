package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class that represents a model for list of prime numbers.
 * It keeps a list of prime numbers, and notifies listeners when there is a change in the list.
 * @author Alen Carin
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/** Prime number that was last generated or the first prime number. */
	private int currentPrime;
	
	/** A list of integer elements. */
	private List<Integer> elements = new ArrayList<>();
	
	/** A list of listeners. */
	private List<ListDataListener> listeners = new ArrayList<>();
	
	public PrimListModel() {
		currentPrime = 1;
		elements.add(currentPrime++);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Generates new prim number and adds it to the internal list of elements.
	 * Notifies listeners about the change.
	 * @param element is an integer value
	 */
	public void next() {
		int pos = elements.size();
		elements.add(nextPrime());
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}
	
	/**
	 * Searches for the next prime number.
	 *
	 * @return the next prime number
	 */
	public Integer nextPrime() {
		while(!isPrime(currentPrime)) {
			currentPrime++;
		}
		return currentPrime++;
	}

	/**
	 * Checks if the passed number is a prime number.
	 * @param number that has to be checked if it is a prime number.
	 * @return true if the passed number is a prime number.
	 */
	private boolean isPrime(int number) {
		if(number == 2) return true;
		if(number == 3) return true;
		
	    for(int i = 2; i < number; ++i) {
	        if (number % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}

}
