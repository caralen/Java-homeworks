package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of hash table which uses a table of slots to store table entries.
 * Entries which belong in the same slot are put in a list to avoid overflow.
 * Static class <code>TableEntry</code> is used for representing table entries.
 * This class implements interface Iterable so the user can iterate through the 
 * elements of the table.
 * @author Alen Carin
 *
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class SimpleHashTable<K, V> implements Iterable<SimpleHashTable.TableEntry<K, V>> {
	
	/**Stores size to which the table should be initially set.*/
	private static final int INITIAL_SIZE = 16;
	
	/**The threshold to which the table can be filled*/
	private static final double FULLNESS_THRESHOLD = 0.75;
	
	/**Number of entries modified*/
	private int modificationCount;
	
	/**Number of non empty slots in the table*/
	private int nonEmptySlotsNumber;
	
	/**Number of pair entries stored in this hash table.*/
	private int size;
	
	/**An array of table entries, it represents the hash table.*/
	private TableEntry<K, V>[] table;
	
	/**
	 * Static class which represents a single entry for the hash table.
	 * It has three fields: key, value and a reference to the next <code>TableEntry</code> in the list.
	 * @author Alen Carin
	 *
	 * @param <K> the type of the key.
	 * @param <V> the type of the value.
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Constructor which sets the passed arguments as field values.
		 * @param key which helps to find a specific value.
		 * @param value which is valuable information.
		 * @param next is a reference to the next entry in the list.
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns the current key.
		 * @return key for this object.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Returns the current value.
		 * @return value for this object.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Sets the value of this object to the value passed through argument.
		 * @param value which should be set for this key.
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}

	/**
	 * Default constructor which initialises field <code>table</code>
	 * to an array of TableEntries of size {@link #INITIAL_SIZE initial size}.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable() {
		table = (TableEntry<K, V>[]) new TableEntry[INITIAL_SIZE];
	}

	/**
	 * Constructor which initialises field <code>table</code> to the
	 * next value's power of two which is bigger than 
	 * two to the power of value passed through arguments.
	 * @param initialSize
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int initialSize) {
		if(initialSize < 1) {
			throw new IllegalArgumentException("Initial size of table of entries must not be less than 1");
		}
		int nextValidSize = nextPowerOfTwo(initialSize);
		table = (TableEntry<K, V>[]) new TableEntry[nextValidSize];
	}
	
	/**
	 * Creates a new TableEntry from the given key and value and puts it in this hash table.
	 * Key must not be null. Value can be null.
	 * @param key which will be stored in the hash table, must not be null.
	 * @param value which will be stored in the hash table
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key, "Cannot put entry in the table when the key is null");
		
		int hashValue = modifyHashCode(key.hashCode());
		TableEntry<K, V> tempHead = table[hashValue];
		
		if (table[hashValue] == null) {
			table[hashValue] = new TableEntry<K, V>(key, value, null);
			size++;
			nonEmptySlotsNumber++;
			modificationCount++;
		} 
		else if (this.containsKey(key)) {
			findKeyAndChangeValue(hashValue, key, value);
		} 
		else {
			iterateToLastAtom(hashValue);
			table[hashValue].next = new TableEntry<K, V>(key, value, null);
			size++;
			modificationCount++;
		}
		if (tempHead != null) {
			table[hashValue] = tempHead;
		}
		if(calculateFullness() > FULLNESS_THRESHOLD) {
			reallocTable();
			modificationCount++;
		}
	}
	
	/**
	 * Calculates the fullness of the current hash table.
	 * @return fullness of the current hash table.
	 */
	private double calculateFullness() {
		return nonEmptySlotsNumber / (double)table.length;
	}

	/**
	 * Creates a new table with doubled number of slots than the previous one.
	 * Entries are moved from the old table to the appropriate slot in the new table.
	 */
	@SuppressWarnings("unchecked")
	private void reallocTable() {
		TableEntry<K, V>[] oldTable = table;
		table = (TableEntry<K, V>[]) new TableEntry[table.length*2];
		size = 0;
		nonEmptySlotsNumber = 0;
		
		for (int i = 0; i < oldTable.length; i++) {
			TableEntry<K, V> tempHead = oldTable[i];
			for (; tempHead != null; tempHead = tempHead.next) {
				this.put(tempHead.getKey(), tempHead.getValue());
			}
		}
	}

	/**
	 * Iterates through list to the last atom. After this method is done, 
	 * reference in the current slot of table is pointing to the last atom in list.
	 * @param hashValue is the number of the slot we are iterating through.
	 */
	private void iterateToLastAtom(int hashValue) {
		for (; table[hashValue].next != null; table[hashValue] = table[hashValue].next);
	}

	/**
	 * Finds an entry with the same key as the one passed through arguments
	 * and sets the value of that entry to the new value passed through arguments.
	 * @param hashValue is the number of the slot we are iterating through.
	 * @param key which is searched for in the table.
	 * @param value that will be put in pair with the key we are searching for.
	 */
	private void findKeyAndChangeValue(int hashValue, K key, V value) {
		for (; table[hashValue] != null; table[hashValue] = table[hashValue].next) {
			if(table[hashValue].getKey().equals(key)) {
				table[hashValue].setValue(value);
			}
		}
	}

	/**
	 * Returns value which is stored in pair with the given key.
	 * @param key which is unique and is stored in pair with value.
	 * @return value stored in pair with the key passed in arguments.
	 */
	public V get(Object key) {
		
		int hashValue = modifyHashCode(key.hashCode());
		TableEntry<K, V> entry = table[hashValue];

		for (; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Returns the number of entry pairs stored in this hash table.
	 * @return number of pairs stored in hash table.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns true if this hash table contains the given key, false otherwise.
	 * @param key which is unique and is stored in pair with value.
	 * @return true if contains the given key.
	 */
	public boolean containsKey(Object key) {
		
		int hashValue = modifyHashCode(key.hashCode());
		TableEntry<K, V> entry = table[hashValue];
		
		for (; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if this hash table contains the given value, false otherwise.
	 * @param value stored in pair with a key.
	 * @return true if contains the given value.
	 */
	public boolean containsValue(Object value) {
		
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];
			
			for (; entry != null; entry = entry.next) {
				if (entry.getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Removes the table entry from the hash table which contains the given key.
	 * @param key which is unique and is stored in pair with value.
	 */
	public void remove(Object key) {
		if(key == null) return;
		
		int hashValue = modifyHashCode(key.hashCode());
		
		if(table[hashValue] == null) return;
		
		findAndRemoveEntry(hashValue, key);
	}
	
	/**
	 * Finds if there is an entry with given key and deletes it.
	 * @param currentSlot is the number of the slot we are iterating through.
	 * @param key which is searched for in the table.
	 */
	private void findAndRemoveEntry(int currentSlot, Object key) {
		boolean isRemoved = false;
		
//		Remove first element in the list
		if(table[currentSlot].getKey().equals(key)) {
			table[currentSlot] = table[currentSlot].next;
			isRemoved = true;
		}
//		Remove element in the list that is not first
		else {
			TableEntry<K, V> tempHead = table[currentSlot];
			for (; table[currentSlot] != null; table[currentSlot] = table[currentSlot].next) {
				if(table[currentSlot].next != null && table[currentSlot].next.getKey().equals(key)) {
					table[currentSlot].next = table[currentSlot].next.next;
					isRemoved = true;
					break;
				}
			}
			table[currentSlot] = tempHead;
		}
		if(isRemoved) {
			size--;
			modificationCount++;
		}
	}

	/**
	 * Method which returns a string representation of the whole hash table.
	 * Starts with the first slot and goes through list of its entries and
	 * then moves on to the next slot.
	 */
	@Override
	public String toString() {
		if(isEmpty()) return "";
		
		String tableToString = "[";
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> tempHead = table[i];
			
			for (; tempHead != null; tempHead = tempHead.next) {
				tableToString = tableToString + tempHead.getKey() + "=" + tempHead.getValue() + ", ";
			}
		}
		tableToString = tableToString.substring(0, tableToString.length()-2);
		tableToString += "]";
		return tableToString;
	}
	
	/**
	 * Returns true if there is no entry stored in this hash table.
	 * @return true if the table is empty.
	 */
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}
	
	/**
	 * Clears all elements from the table and sets the size to zero.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		nonEmptySlotsNumber = 0;
	}

	/**
	 * Private method used to calculate next power of two for the given argument.
	 * If the initialSize is a power of two then it is returned,
	 * otherwise the next number which is power of two is returned.
	 * @param initialSize is an integer number.
	 * @return next number which is power of two from the given initialSize.
	 */
	private int nextPowerOfTwo(int initialSize) {
		return initialSize == 1 ? 1 : Integer.highestOneBit(initialSize-1) * 2;
	}
	
	/**
	 * Modifies hashCode so it would fit the length of the hash table.
	 * @param hashCode is a number generated from a certain key.
	 * @return new value of hashCode adapted for this hash table.
	 */
	private int modifyHashCode(int hashCode) {
		if(hashCode < 0) {
			hashCode *= -1;
		}
		return hashCode % table.length;
	}

	/**
	 * This method is a factory of Iterators.
	 * Returns a new instance of <code>IteratorImpl</code>
	 */
	@Override
	public Iterator<SimpleHashTable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * This class implements an Iterator. It enables iteration through the hash table
	 * using methods <code>hasNext()</code> and <code>next()</code>.
	 * Also there is a method <code>remove()</code> which enables removing
	 * current table entry in the iteration.
	 * @author Alen Carin
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashTable.TableEntry<K, V>>{
		
		/**The number of elements which are not yet iterated.*/
		private int remainingSize;
		
		/**Copy of the modificationCount variable. It is used 
		 * to check if there has been a change in the hash table.*/
		private int modificationCountCopy;
		
		/**Number of the slot in the table where iterator is at the moment.*/
		private int currentSlot;
		
		/**Flag used for checking if the current element has already been removed.*/
		private boolean alreadyRemoved;
		
		/**Flag for checking if the element is at the start of the list*/
		private boolean isFirstElement;
		
		/**Represents an entry in the table which was last requested*/
		private TableEntry<K, V> currentHead;

		/**
		 * Default constructor which sets fields to specific values.
		 */
		public IteratorImpl() {
			remainingSize = size;
			modificationCountCopy = modificationCount;
			currentSlot = 0;
			isFirstElement = true;
			currentHead = table[currentSlot];
		}

		/**
		 * Returns true if there is next element to iterate.
		 * @throws ConcurrentModificationException 
		 * 					if the table has been changed outside this iterator.
		 */
		@Override
		public boolean hasNext() throws ConcurrentModificationException {
			checkIfModified();
			return remainingSize > 0;
		}

		/**
		 * Returns the next table entry in the iteration.
		 * @throws NoSuchElementException 
		 * 					if there is no more elements to return.
		 * @throws ConcurrentModificationException 
		 * 					if the table has been modified outside this iterator.
		 */
		@Override
		public TableEntry<K, V> next() {
			
			checkIfModified();
			if(remainingSize < 1) {
				throw new NoSuchElementException("There is no next element");
			}
			if(!isFirstElement) {
				currentHead = currentHead.next;
			}
			if(currentHead == null) {
				currentSlot++;
				currentHead = table[currentSlot];
			}
			while(table[currentSlot] == null) {
				currentSlot++;
				currentHead = table[currentSlot];
			}
			remainingSize--;
			alreadyRemoved = false;
			isFirstElement = false;
			return currentHead;
		}
		
		/**
		 * Removes the last element which was returned by the <code>next()</code> method.
		 * @throws IllegalStateException
		 * 					if this element has already been removed.
		 * @throws ConcurrentModificationException
		 * 					if the table has been modified outside this iterator.
		 */
		public void remove() {
			checkIfModified();
			
			if(alreadyRemoved) {
				throw new IllegalStateException("Cannot remove the same element twice");
			}
			
			int hashValue = modifyHashCode(currentHead.getKey().hashCode());
			findAndRemoveEntry(hashValue, currentHead.getKey());
			
			modificationCountCopy++;
			alreadyRemoved = true;
		}
		
		/**
		 * Checks if the table has been modified outside this iterator
		 * @throws ConcurrentModificationException
		 * 					if the table has been modified outside this iterator.
		 */
		private void checkIfModified() {
			if(modificationCountCopy != modificationCount) {
				throw new ConcurrentModificationException("Table has been changed outside the iterator");
			}
		}
		
	}
}
