package hr.fer.zemris.java.custom.collections;

/**
 * Class which is used to simulate stack collection.
 * Stack is a collection where last in goes first out.
 * 
 * @author Alen Carin
 *
 */
public class ObjectStack {
	
	private ArrayIndexedCollection collection;
	
	public ObjectStack() {
		collection = new ArrayIndexedCollection();
	}
	
	
	/**
	 * Returns true if collection contains no objects and false otherwise.
	 * 
	 * @return true, if it is empty
	 */
	public boolean isEmpty() {
		if(collection.size() == 0) return true;
		return false;
	}
	
	/**
	 * Returns the number of currently stored objects in this collections.
	 * 
	 * @return size of the collection
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Adds one element at the top of stack.
	 * 
	 * @param value which should be pushed
	 */
	public void push(Object value) {
		collection.add(value);
	}
	
	/**
	 * Return the element which is currently at the top of the stack.
	 * Element is then removed from the stack.
	 * 
	 * @return element from the stack
	 */
	public Object pop() {
		if(collection.size() == 0) {
			throw new EmptyStackException("You cannot call pop() on an empty stack");
		}
		Object newObject = peek();
		collection.remove(collection.size()-1);
		return newObject;
	}
	
	/**
	 * Returns the element which is currently at the top of the stack.
	 * 
	 * @return element from the stack
	 */
	public Object peek() {
		if(collection.size() == 0) {
			throw new EmptyStackException("You cannot call peek() on an empty stack");
		}
		return collection.get(collection.size()-1);
	}
	
	/**
	 * Removes all elements from the stack
	 */
	public void clear() {
		collection.clear();
	}
}
