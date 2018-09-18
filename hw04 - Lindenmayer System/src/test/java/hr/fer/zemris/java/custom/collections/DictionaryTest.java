package hr.fer.zemris.java.custom.collections;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hr.fer.zemris.java.custom.collections.Dictionary;

/**
 * Test class used for testing Dictionary class.
 * @author Alen Carin
 *
 */
public class DictionaryTest {
	
	@Test
	public void testEmpty() {
		Dictionary dictionary = new Dictionary();
		
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testSizeAndPut() {
		Dictionary dictionary = new Dictionary();
		assertEquals(0, dictionary.size());
		dictionary = addElements(dictionary);
		
		assertEquals(2, dictionary.size());
	}
	
	@Test
	public void testClear() {
		Dictionary dictionary = new Dictionary();
		dictionary = addElements(dictionary);
		assertEquals(2, dictionary.size());
		dictionary.clear();
		
		assertEquals(0, dictionary.size());
	}
	

	@Test(expected = NullPointerException.class)
	public void testPutKeyNull() {
		Dictionary dictionary = new Dictionary();
		dictionary.put(null, null);
	}
	
	@Test
	public void testDuplicate() {
		Dictionary dictionary = new Dictionary();
		dictionary = addElements(dictionary);
		dictionary.put("Key", "Ivana");
		
		assertEquals(2, dictionary.size());
		assertEquals("Ivana", dictionary.get("Key"));
	}
	
	@Test
	public void testGet() {
		Dictionary dictionary = new Dictionary();
		dictionary = addElements(dictionary);
		
		assertEquals("Marija", dictionary.get("Key2"));
	}
	
	private Dictionary addElements(Dictionary dictionary) {
		dictionary.put("Key", "Ana");
		dictionary.put("Key2", "Marija");
		return dictionary;
	}
}
