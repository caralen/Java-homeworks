package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Class used for testing the functionality of the ObjectMultistack methods.
 * @author Alen Carin
 *
 */
public class ObjectMultistackTest {
	
	ObjectMultistack multistack;

	@Before
	public void initializeStack() {
		multistack = new ObjectMultistack();
	}
	
	@Test
	public void pushTest() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
	}
	
	@Test
	public void popTest() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		assertEquals(Integer.valueOf(2000), multistack.pop("year").getValue());
	}
	
	@Test(expected = NullPointerException.class)
	public void popOnEmptyStackTest() {
		
		multistack.pop("year");
	}
	
	@Test(expected = NullPointerException.class)
	public void peekOnEmptyStackTest() {
		
		multistack.peek("year");
	}
	
	@Test
	public void isEmptyTest() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		assertTrue(multistack.isEmpty("price"));
		assertFalse(multistack.isEmpty("year"));
	}
}
