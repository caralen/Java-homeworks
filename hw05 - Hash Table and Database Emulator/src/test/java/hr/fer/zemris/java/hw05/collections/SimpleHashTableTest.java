package hr.fer.zemris.java.hw05.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public class SimpleHashTableTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor() {
		new SimpleHashTable<>(-5);
	}
	
	@Test
	public void testPut() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		
		assertEquals(4, examMarks.size());
	}
	
	@Test
	public void testPutSameKey() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		assertEquals(4, examMarks.size());
	}
	
	@Test
	public void testGet() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		assertTrue(examMarks.get("Ivana").equals(5));
	}
	
	@Test
	public void testContainsKey() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		assertTrue(examMarks.containsKey("Kristina"));
	}
	
	@Test
	public void testContainsValue() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Jasna", 3);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		assertTrue(examMarks.containsValue(3));
	}
	
	@Test
	public void testRemove() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.remove("Ivana");
		examMarks.remove("Jasna");
		
		assertTrue(examMarks.size() == 2);
	}
	
	@Test
	public void testIsEmpty() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		
		assertTrue(examMarks.isEmpty());
	}
	
	@Test
	public void testToString() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>();
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		
		assertEquals("[Ivana=2, Ante=2]", examMarks.toString());
	}
	
	@Test
	public void testIterator() {
		SimpleHashTable<String, Integer> examMarks = new SimpleHashTable<>(2);
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashTable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		
		assertTrue(examMarks.isEmpty());
	}
}
