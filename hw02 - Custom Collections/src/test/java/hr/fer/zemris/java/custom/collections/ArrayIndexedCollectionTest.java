package hr.fer.zemris.java.custom.collections;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class that is used for testing methods from the ArrayIndexedCollection class
 * @author Alen Carin
 *
 */
public class ArrayIndexedCollectionTest {

	@Test
	public void addingObjects() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		String test = new String("txt");
		
		int sizeBefore = collection.size();
		collection.add(test);
		int sizeAfter = collection.size();
		
		assertNotEquals(sizeBefore, sizeAfter);
	}
	
	@Test
	public void addNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		boolean exceptionThrown = false;
		try {
			collection.add(null);
		} catch(NullPointerException ex) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void getExistingElementIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(new String("test"));
		
		assertEquals((String) collection.get(0), "test");
	}
	
	@Test
	public void getNotExistingElementIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		boolean exceptionThrown = false;
		try {
			collection.get(5);
		} catch(IndexOutOfBoundsException ex) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("New York");
		collection.add("San Francisco");
		collection.clear();
		assertEquals(0, collection.size());
	}
	
	@Test
	public void insertLegalPosition() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.insert("New York", 0);
		
		assertEquals("New York", collection.get(0));
	}
	
	@Test
	public void testPositionShifting() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("New York");
		collection.add("San Francisco");
		collection.remove(0);
		
		assertEquals("San Francisco", collection.get(0));
	}
	
	@Test
	public void indexOfExistingObject() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("New York");
		collection.add("San Francisco");
		
		assertEquals(1, collection.indexOf("San Francisco"));
	}
	
	@Test
	public void indexOfNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		
		assertEquals(-1, collection.indexOf(null));
	}
	
	@Test
	public void indexOfNotExistingObject() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("New York");
		
		assertEquals(-1, collection.indexOf("Zagreb"));
	}
	
	@Test
	public void removeElement() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("New York");
		collection.remove(0);
		
		assertEquals(0, collection.size());
	}
}
