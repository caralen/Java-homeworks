package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents general collection of objects.
 */
public class Collection {

	/**
	 * Instantiates a new collection.
	 */
	protected Collection() {
		super();
	}
	
	/**
	 * Returns true if collection contains no objects and false otherwise.
	 *
	 * @return true, if it is empty
	 */
	public boolean isEmpty() {
		if(size() == 0) return true;
		return false;
	}
	
	/**
	 * Returns the number of currently stored objects in this collections.
	 *
	 * @return size of the collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection.
	 *
	 * @param value that should be added
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Returns true only if the collection contains given value.
	 *
	 * @param value for which the collection will be searched
	 * @return true, if the value is already in collection
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes the element with the given value from the collection.
	 *
	 * @param value the value
	 * @return true, if collection contains given value
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collection, 
	 * fills it with collection content and returns the array.
	 *
	 * @return array of objects
	 */
	public Object[] toArray() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls processor.process() method for each object in the collection
	 *
	 * @param processor the processor
	 */
	public void forEach(Processor processor) {

	}
	
	/**
	 * Method adds into the current collection all elements from the given collection.
	 * Other collection remains unchanged.
	 *
	 * @param other collection which should be copied
	 */
	public void addAll(Collection other) {
		
		class AddingProcessor extends Processor{
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new AddingProcessor());
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		
	}
}
