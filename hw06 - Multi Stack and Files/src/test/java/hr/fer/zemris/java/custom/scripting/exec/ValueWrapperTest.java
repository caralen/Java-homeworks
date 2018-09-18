package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class used for testing the functionality of the ValueWrapper methods.
 * @author Alen Carin
 *
 */
public class ValueWrapperTest {
	
	public static double TRESHOLD = 1E-6;
	
	@Test
	public void addNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void addStringAndInt() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		
		assertEquals(13.0, (Double) v3.getValue(), TRESHOLD);
		assertEquals(1, v4.getValue());
	}

	@Test
	public void addStringAndInt2() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		
		assertEquals(13, v5.getValue());
		assertEquals(1, v6.getValue());
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddAnkica() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue());
	}
	
	@Test
	public void subtractNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testSubtractStrings() {
		ValueWrapper v1 = new ValueWrapper("13");
		ValueWrapper v2 = new ValueWrapper("1.1E1");
		v1.subtract(v2.getValue());
		
		assertEquals(2.0, (Double) v1.getValue(), TRESHOLD);
		assertEquals("1.1E1", v2.getValue());
	}
	
	@Test
	public void testSubtractDoubleAndInt() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(8.3));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));
		v1.subtract(v2.getValue());
		
		assertEquals(3.3, (Double) v1.getValue(), TRESHOLD);
		assertEquals(5, v2.getValue());
	}
	
	@Test
	public void testMultiplyDoubleAndInt() {
		double result = 8.3 * 5;
		
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(8.3));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));
		v1.multiply(v2.getValue());
		
		assertEquals(result, (Double) v1.getValue(), TRESHOLD);
		assertEquals(5, v2.getValue());
	}
	
	@Test
	public void testMultiplyTwoInts() {
		int result = 2 * 5;
		
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(2));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));
		v1.multiply(v2.getValue());
		
		assertEquals(result, v1.getValue());
		assertEquals(5, v2.getValue());
	}
	
	@Test
	public void testDivideDoubleAndInt() {
		double result = 8.4 / 2;
		
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(8.4));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		v1.divide(v2.getValue());
		
		assertEquals(result, (Double) v1.getValue(), TRESHOLD);
		assertEquals(2, v2.getValue());
	}
	
	@Test
	public void testDivideTwoInts() {
		int result = 10 / 2;
		
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(10));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		v1.divide(v2.getValue());
		
		assertEquals(result, v1.getValue());
		assertEquals(2, v2.getValue());
	}
	
	@Test(expected = ArithmeticException.class)
	public void testDivisionWithZero() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(10));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(0));
		v1.divide(v2.getValue());
	}
	
	@Test
	public void testnumCompare() {
		ValueWrapper v1 = new ValueWrapper("8.2");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		v1.numCompare(v2.getValue());
		
		assertTrue(v1.numCompare(v2.getValue()) > 0);
	}
	
	@Test
	public void testnumCompare2() {
		ValueWrapper v1 = new ValueWrapper("8.2");
		ValueWrapper v2 = new ValueWrapper("1.2E1");
		v1.numCompare(v2.getValue());
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
	}
	
	@Test
	public void testnumCompare3() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.numCompare(v2.getValue());
		
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
}
