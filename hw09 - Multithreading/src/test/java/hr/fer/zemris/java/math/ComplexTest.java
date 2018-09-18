package hr.fer.zemris.java.math;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hr.fer.zemris.java.math.Complex;

/**
 * Class used for testing the implementation and functionalities of the Complex class.
 * @author Alen Carin
 *
 */
public class ComplexTest {

	private static double THRESHOLD = 1E-6;
	
	@Test
	public void testModule() {
		assertEquals(2, new Complex(sqrt(2), sqrt(2)).module(), THRESHOLD);
	}
	
	@Test
	public void testMultiply() {
		assertEquals(new Complex(0, 0), new Complex(2, 1).multiply(Complex.ZERO));
		assertEquals(new Complex(2, 1), new Complex(2, 1).multiply(Complex.ONE));
		assertEquals(new Complex(-2, -1), new Complex(2, 1).multiply(Complex.ONE_NEG));
		assertEquals(new Complex(-1, 2), new Complex(2, 1).multiply(Complex.IM));
		assertEquals(new Complex(1, -2), new Complex(2, 1).multiply(Complex.IM_NEG));
		assertEquals(new Complex(2, 6), new Complex(2, 1).multiply(new Complex(2, 2)));
	}
	
	@Test 
	public void testDivide() {
		assertEquals(new Complex(2, 1), new Complex(2, 1).divide(Complex.ONE));
		assertEquals(new Complex(0.5, 0), new Complex(2, 1).divide(new Complex(4, 2)));
	}
	
	@Test
	public void testAdd() {
		assertEquals(new Complex(9, 9), new Complex(4, 5).add(new Complex(5,4)));
		assertEquals(new Complex(11, 4), new Complex(43, -2).add(new Complex(-32,6)));
		assertEquals(new Complex(55, 61), new Complex(32, 54).add(new Complex(23,7)));
	}
	
	@Test
	public void testSub() {
		assertEquals(new Complex(-1, 1), new Complex(4, 5).sub(new Complex(5,4)));
		assertEquals(new Complex(75, -8), new Complex(43, -2).sub(new Complex(-32,6)));
		assertEquals(new Complex(9, 47), new Complex(32, 54).sub(new Complex(23,7)));
	}
	
	@Test
	public void testNegate() {
		assertEquals(new Complex(-54, -12), new Complex(54, 12).negate());
		assertEquals(new Complex(3, 26), new Complex(-3, -26).negate());
	}
	
	@Test
	public void testPower() {
		assertEquals(new Complex(-5, 12).getReal(), new Complex(2, 3).power(2).getReal(), THRESHOLD);
		assertEquals(new Complex(-5, 12).getImaginary(), new Complex(2, 3).power(2).getImaginary(), THRESHOLD);
		
		assertEquals(new Complex(-198, -10).getReal(), new Complex(3, -5).power(3).getReal(), THRESHOLD);
		assertEquals(new Complex(-198, -10).getImaginary(), new Complex(3, -5).power(3).getImaginary(), THRESHOLD);
	}
	
	@Test
	public void testRoot() {
		Complex c = new Complex(8, -6);
		
		assertTrue(c.root(2).contains(new Complex(3, -1)));
	}
}
