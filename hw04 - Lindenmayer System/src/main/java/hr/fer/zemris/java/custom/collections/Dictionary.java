package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Data structure for storing Objects containing a pair of key and value.
 * It is implemented as an adapter on <code>ArrayIndexedCollection</code>.
 * @author Alen Carin
 *
 */
public class Dictionary {
	
	private ArrayIndexedCollection collection;
	
	/**
	 * Default constructor which creates an instance of <code>ArrayIndexedCollection</code>.
	 */
	public Dictionary() {
		this.collection = new ArrayIndexedCollection();
	}

	/**
	 * Private class which represents a data structure containing 
	 * a pair of key and value of type Object.
	 * Key is a data that helps value to be easily found.
	 * Key must not be null.
	 * @author Alen Carin
	 *
	 */
	private class Tuple{
		private Object key;
		private Object value;
		
		/**
		 * Constructor which takes two values and stores them.
		 * @param key is an data by which the value is searched for.
		 * @param value that contains valuable information.
		 */
		public Tuple(Object key, Object value) {
			super();
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
		}

		/**
		 * Returns key of this tuple
		 * @return key
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * Returns value of this tuple
		 * @return value
		 */
		public Object getValue() {
			return value;
		}
	}
	
	/**
	 * Checks if the <code>Dictionary</code> is empty or not.
	 * @return true if it is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Counts the Objects in the structure and returns how many are there.
	 * @return number of Objects stored in the <code>Dictionary</code>.
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Removes all the data stored in this data structure.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 * Puts the pair of key and value in the data structure.
	 * @param key is data by which the value is searched for.
	 * @param value that contains valuable information.
	 */
	public void put(Object key, Object value) {
		int index = indexOf(key);
		if(index != -1) {
			collection.remove(index);
		}
		collection.add(new Tuple(key, value));
	}
	
	/**
	 * Returns value which is stored with the given key.
	 * If there is no data with the given key, null is returned.
	 * @param key is data by which the value is searched for.
	 * @return value stored with the given key or null if there is no object with the given key.
	 */
	public Object get(Object key) {
		int index = indexOf(key);
		if(index == -1) {
			return null;
		}
		return ((Tuple)collection.get(index)).getValue();
	}
	
	/**
	 * Private method which goes through the structure and returns index
	 * at which the tuple containing key is stored. Returns -1 if the key is not found.
	 * @param key is data by which the value is searched for.
	 * @return index of the tuple in which the given key is stored or null if not found.
	 */
	private int indexOf(Object key) {
		if(key == null) {
			return -1;
		}
		for(int i = 0; i < collection.size(); i++) {
			if(key.equals(((Tuple)collection.get(i)).getKey())) {
				return i;
			}
		}
		return -1;
	}
}
