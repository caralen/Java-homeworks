package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of resizable linked list-backed collection of objects.
 * <br>Duplicate elements are allowed, storage of null references is not allowed.
 * @author Alen Carin
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Class which represents node in the list data structure.
	 * The node has reference to previous and next nodes in the list, 
	 * as well as the value of the current node.
	 * @author Alen Carin
	 *
	 */
	private static class ListNode{
		ListNode previous;
		ListNode next;
		Object value;
		
		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * Default constructor which sets references of the first and last node to null
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
	}
	
	/**
	 * Constructor which copies all of the elements from the passed collection to this 
	 * newly created collection. The other collection is not modified.
	 * @param other collection which should be copied
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(other);
	}
	
	/**
	 * Adds the given object at the end of this collection.
	 * @param value that should be inserted in collection, must not be null.
	 */
	public void add(Object value) {
		insert(value, size);
	}
	
	/**
	 * Returns the object that is stored in the collection at the given index.
	 * @param index must be between 0 and size-1
	 * @return object at the given index
	 */
	public Object get(int index) {
		checkBoundaries(index, 0, size-1);
		
		if(index > size/2) {
			ListNode temp = last;
			for(int i = 0; i < size-index-1; i++) {
				temp = temp.previous;
			}
			return temp.value;
		}
		else {
			ListNode temp = first;
			for(int i = 0; i < index; i++) {
				temp = temp.next;
			}
			return temp.value;
		}
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	public void clear() {
		first = last = null;
		size = 0;
	}
	
	/**
	 * Inserts the given value at the given position in collection.
	 * Elements starting from position are shifted one position.
	 * @param value
	 * @param position must be between 0 and collection size
	 */
	public void insert(Object value, int position) {
		if(value.equals(null)) {
			throw new NullPointerException
			(
					"LinkedListIndexedCollection does not permit adding null reference"
			);
		}
		checkBoundaries(position, 0, size);
		
		if(size == 0) {
			ListNode newNode = new ListNode();
			newNode.value = value;
			first = newNode;
			last = newNode;
			newNode.previous = newNode.next = null;
		}
		else if(position == 0) {
			ListNode newNode = new ListNode();
			newNode.value = value;
			first.previous = newNode;
			newNode.next = first;
			newNode.previous = null;
			first = newNode;
		}
		else if(position == size) {
			ListNode newNode = new ListNode();
			newNode.value = value;
			last.next = newNode;
			newNode.previous = last;
			newNode.next = null;
			last = newNode;
		}
		else if(position > size/2) {
			ListNode temp = last;
			for(int i = 0; i < size-position-1; i++) {
				temp = temp.previous;
			}
			ListNode newNode = new ListNode();
			newNode.value = value;
			
//			Preusmjeravanje pokazivaca cvorova
			temp.previous.next = newNode;
			newNode.previous = temp.previous;
			newNode.next = temp;
			temp.previous = newNode;
			
		}
		else {
			ListNode temp = first;
			for(int i = 0; i < position-1; i++) {
				temp = temp.next;
			}
			ListNode newNode = new ListNode();
			newNode.value = value;
			
//			Preusmjeravanje pokazivaca cvorova
			temp.next.previous = newNode;
			newNode.next = temp.next;
			newNode.previous = temp;
			temp.next = newNode;
		}
		size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value.
	 * If the value is not found returns -1.
	 * @param value which will be searched in the collection
	 * @return index of the first occurrence of the given value or -1 if the value is not found
	 */
	public int indexOf(Object value) {
		if(value.equals(null)) {
			return -1;
		}
		ListNode temp = first;
		for(int i = 0; i < size; i++) {
			if(temp.value.equals(value)) {
				return i;
			}
			temp = temp.next;
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. 
	 * Element that was previously at location index+1 after this operation is on location index.
	 * @param index at which the element will be removed, must be between 0 and size of the collection - 1
	 */
	public void remove(int index) {
		checkBoundaries(index, 0, size-1);
		
		if(index > size/2) {
			ListNode temp = last;
			for(int i = 0; i < size-index; i++) {
				temp = temp.previous;
			}
			temp.previous.previous.next = temp;
			temp.previous = temp.previous.previous;
		}
		else {
			ListNode temp = first;
			for(int i = 0; i < index; i++) {
				temp = temp.next;
			}
			temp.next.next.previous = temp;
			temp.next = temp.next.next;
		}
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value) {
		if(value.equals(null)) {
			return false;
		}
		ListNode temp = first;
		for(int i = 0; i < size; i++) {
			if(temp.value.equals(value)) {
				return true;
			}
			temp = temp.next;
		}
		return false;
	}

	@Override
	public Object[] toArray() throws UnsupportedOperationException {
		Object[] newArray = new Object[size()];
		ListNode temp = first;
		for(int i = 0; i < size; i++) {
			newArray[i] = temp;
			temp = temp.next;
		}
		return newArray;
	}

	@Override
	public void forEach(Processor processor) {
		ListNode temp = first;
		for(int i = 0; i < size; i++) {
			processor.process(temp);
			temp = temp.next;
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

	/**
	 * Checks if the given index is inside the right interval.
	 * @param index
	 * @param lower boundary
	 * @param upper boundary
	 * @throws IndexOutOfBoundsException if index is not in the right interval
	 */
	public static void checkBoundaries(int index, int lower, int upper) throws IndexOutOfBoundsException {
		if(index < lower || index > upper) {
			throw new IndexOutOfBoundsException
			(
					"Index must be greater than or equal to " + lower
					+ ", and less than or equal to " + upper + ". Was " + index
			);
		}
	}
}
