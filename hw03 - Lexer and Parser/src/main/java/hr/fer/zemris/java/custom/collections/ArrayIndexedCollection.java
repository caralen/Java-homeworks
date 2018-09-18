package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of resizable array-backed collection of objects.
 * Duplicate elements are allowed, storage of null references is not allowed.
 * Extends class Collection.
 * 
 * @author Alen Carin
 *
 */
public class ArrayIndexedCollection extends Collection {
	
	static final int INITIAL_SIZE = 16;
	
	private int size;
	private int capacity;
	private Object[] elements;
	

	/**
	 * Default constructor, sets collection capacity to 16.
	 */
	public ArrayIndexedCollection() {
		this(INITIAL_SIZE);
	}
	
	/**
	 * Sets the collection capacity to given initialCapacity.
	 * @param initialCapacity
	 * integer number to which the collection capacity should be set
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException
			(
					"The given argument initial capacity must not be less than 1. It was " + initialCapacity
			);
		}
		this.capacity = initialCapacity;
		this.elements = new Object[this.capacity];
	}
	
	/**
	 * Receives object of type collection and copies all of the elements to this collection.
	 * If object size is greater than 16, then its capacity will become this collection's capacity,
	 * otherwise it will be set to 16.
	 * @param other
	 * 				object of type Collection, its elements will be copied to this collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, INITIAL_SIZE);
	}
	
	/**
	 * Receives object of type collection and copies all of the elements to this collection.
	 * If object size is greater than the given initialCapacity, 
	 * then its capacity will become this collection's capacity,
	 * otherwise it will be set to the given argument initialCapacity.
	 * @param other
	 * 				object of type Collection, its elements will be copied to this collection
	 * @param initialCapacity
	 * 				integer value to which the capacity should be set
	 * @throws NullPointerException
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) throws NullPointerException {
		if(other.equals(null)) {
			throw new NullPointerException
			(
					"ArrayIndexedCollection must not be instantiated with null reference"
			);
		}
		if(initialCapacity < other.size()) {
			this.elements = new Object[other.size()];
		}
		addAll(other);
	}

	/**
	 * Adds the given object into this collection at the first empty space.
	 * Null values are not allowed.
	 */
	@Override
	public void add(Object value) throws NullPointerException {
		insert(value, size);
	}
	
	/**
	 * Returns the object that is stored in collection at the given index.
	 *
	 * @param index of the requested element, 
	 * must be greater than or equal to 0 and smaller than the collection size
	 * @return requested element from the collection
	 * @throws IndexOutOfBoundsException if the index is not in the required interval
	 */
	public Object get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException
			(
					"Index must be greater than or equal to 0, and less than size. Was " + index
			);
		}
		return elements[index];
	}
	
	
	/**
	 * Inserts the given value at the given position in collection.
	 *
	 * @param value that should be inserted in the collection
	 * @param position at which the value should be inserted
	 */
	public void insert(Object value, int position) {
		if(value.equals(null)) {
			throw new NullPointerException
			(
					"ArrayIndexedCollection does not permit adding null reference"
			);
		}
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException
			(
					"Index must be greater than 0, and less than size-1. Was " + position
			);
		}
		
		if(size == capacity) {
			capacity *= 2;
			Object[] temp = new Object[capacity];
			for(int i = 0; i < elements.length; i++) {
				temp[i] = elements[i];
			}
			elements = temp;
		}
		for(int i = size; i > position; i--) {
			elements[i] = elements[i-1];
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Searches the collection for the given value and returns index at which the value is stored.
	 *
	 * @param value you are searching for in the collection
	 * @return index at which the value is found, or -1 if it is not found
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		for(int i = 0; i < size; i++) {
			if(value.equals(elements[i])) return i;
		}
		return -1;
	}

	/**
	 * Removes the element at specified index from the collection.
	 *
	 * @param index must be greater than 0, and less than size of the collection-1
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException
			(
					"Index must be greater than 0, and less than " + size + ". Was " + index
			);
		}
		for(int i = index; i < size-1; i++) {
			elements[i] = elements[i+1];
		}
		elements[size-1] = null;
		size--;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) return true;
		}
		return false;
	}

	@Override
	public Object[] toArray() throws UnsupportedOperationException {
		Object[] newArray = new Object[size()];
		for(int i = 0; i < size(); i++) {
			newArray[i] = elements[i];
		}
		return newArray;
	}

	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size(); i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public void clear() {
		int initialSize = size;
		for(int i = 0; i < initialSize; i++) {
			elements[i] = null;
			size--;
		}
	}
	
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index != -1) {
			remove(index);
			return true;
		}
		return false;
	}
}
