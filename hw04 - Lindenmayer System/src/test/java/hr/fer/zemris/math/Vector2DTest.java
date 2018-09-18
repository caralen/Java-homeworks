package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class used for testing functionalities of the <code>Vector2D</code>.
 * @author Alen Carin
 *
 */
public class Vector2DTest {
	
	public static double OFFSET = 1E-4;

	@Test
	public void testConstrucorAndGetters() {
		Vector2D vector = new Vector2D(3.2, 2.1);
		
		assertEquals(3.2, vector.getX(), OFFSET);
		assertEquals(2.1, vector.getY(), OFFSET);
	}
	
	@Test
	public void testTranslation() {
		Vector2D vector1 = new Vector2D(3.2, 2.1);
		Vector2D vector2 = new Vector2D(5.8, -1.1);
		vector1.translate(vector2);
		
		assertEquals(9, vector1.getX(), OFFSET);
		assertEquals(1, vector1.getY(), OFFSET);
	}
	
	@Test
	public void testTranslated() {
		Vector2D vector1 = new Vector2D(3.2, 2.1);
		Vector2D vector2 = new Vector2D(5.8, -1.1);
		Vector2D vector3 = vector1.translated(vector2);
		
		assertTrue(vector1 != vector3);
		assertEquals(9, vector3.getX(), OFFSET);
		assertEquals(1, vector3.getY(), OFFSET);
	}
	
	
	@Test
	public void testRotate90() {
		Vector2D vector = new Vector2D(5, 0);
		vector.rotate(90);
		
		assertEquals(0, vector.getX(), OFFSET);
		assertEquals(5, vector.getY(), OFFSET);
	}
	
	@Test
	public void testRotate180() {
		Vector2D vector = new Vector2D(5, 10);
		vector.rotate(180);
		
		assertEquals(-5, vector.getX(), OFFSET);
		assertEquals(-10, vector.getY(), OFFSET);
	}
	
	@Test
	public void testRotate360() {
		Vector2D vector = new Vector2D(5, 0);
		vector.rotate(360);
		
		assertEquals(5, vector.getX(), OFFSET);
		assertEquals(0, vector.getY(), OFFSET);
	}
	@Test
	public void testRotateNegative90() {
		Vector2D vector = new Vector2D(5, 0);
		
		vector.rotate(-90);
		assertEquals(0, vector.getX(), OFFSET);
		assertEquals(-5, vector.getY(), OFFSET);
	}
	
	@Test
	public void testRotated() {
		Vector2D vector1 = new Vector2D(5, 0);
		Vector2D vector2 = vector1.rotated(90);
		
		assertTrue(vector1 != vector2);
		assertEquals(0, vector2.getX(), OFFSET);
		assertEquals(5, vector2.getY(), OFFSET);
	}
	
	@Test
	public void testScaling() {
		Vector2D vector = new Vector2D(5, 5);
		vector.scale(5);
		
		assertEquals(25, vector.getX(), OFFSET);
		assertEquals(25, vector.getY(), OFFSET);
	}
	
	@Test
	public void testScaled() {
		Vector2D vector1 = new Vector2D(5, 0);
		Vector2D vector2 = vector1.scaled(5);
		
		assertTrue(vector1 != vector2);
		assertEquals(25, vector2.getX(), OFFSET);
		assertEquals(0, vector2.getY(), OFFSET);
	}
	
	@Test
	public void testCopy() {
		Vector2D vector1 = new Vector2D(5, 2);
		Vector2D vector2 = vector1.copy();
		
		assertTrue(vector1 != vector2);
		assertEquals(5, vector2.getX(), OFFSET);
		assertEquals(2, vector2.getY(), OFFSET);
	}
}
