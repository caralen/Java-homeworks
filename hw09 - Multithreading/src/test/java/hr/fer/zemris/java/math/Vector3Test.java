package hr.fer.zemris.java.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static java.lang.Math.sqrt;

import org.junit.Test;

import hr.fer.zemris.java.math.Vector3;

/**
 * Class used for testing functionalities of the class Vector3.
 * @author Alen Carin
 *
 */
public class Vector3Test {
	
	private static double THRESHOLD = 1E-6;
	
	@Test
	public void testNorm() {
		assertEquals(sqrt(3), new Vector3(1, 1, 1).norm(), THRESHOLD);
		assertEquals(3, new Vector3(sqrt(3), sqrt(3), sqrt(3)).norm(), THRESHOLD);
	}
	
	@Test
	public void testNormalized() {
		Vector3 v = new Vector3(2, 3, 4);
		
		assertTrue(v.normalized().norm() == 1);
		assertTrue(v.cosAngle(v.normalized()) == 1);
	}
	
	@Test
	public void testAdd() {
		assertEquals(new Vector3(4, 4, 4), new Vector3(1, 2, 3).add(new Vector3(3, 2, 1)));
		assertEquals(new Vector3(128, 1066, 36), new Vector3(5, 1043, 32).add(new Vector3(123, 23, 4)));
		assertEquals(new Vector3(311, 50, 474), new Vector3(11, 22, 332).add(new Vector3(300, 28, 142)));
	}
	
	@Test
	public void testSub() {
		assertEquals(new Vector3(4, 0, 4), new Vector3(7, 2, 5).sub(new Vector3(3, 2, 1)));
		assertEquals(new Vector3(-118, 1020, 28), new Vector3(5, 1043, 32).sub(new Vector3(123, 23, 4)));
		assertEquals(new Vector3(-289, -6, 190), new Vector3(11, 22, 332).sub(new Vector3(300, 28, 142)));
	}
	
	@Test
	public void testDot() {
		assertEquals(30, new Vector3(7, 2, 5).dot(new Vector3(3, 2, 1)), THRESHOLD);
	}
	
	@Test
	public void testCross() {
		assertEquals(new Vector3(0, 0, 1), new Vector3(1, 0, 0).cross(new Vector3(0, 1, 0)));
	}
	
	@Test
	public void testScale() {
		assertEquals(new Vector3(14, 4, 10), new Vector3(7, 2, 5).scale(2));
	}
	
	@Test
	public void testCosAngle() {
		assertEquals(0, new Vector3(1, 0, 0).cosAngle(new Vector3(0, 1, 0)), THRESHOLD);
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNull() {
		new Vector3(1, 1, 1).add(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testSubNull() {
		new Vector3(1, 1, 1).sub(null);
	}
	
	@Test
	public void testNormalizeZero() {
		assertEquals(new Vector3(0, 0, 0), new Vector3(0, 0, 0).normalized());
	}
}
