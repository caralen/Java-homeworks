package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class which is used to test methods for operations with complex numbers.
 * @author Alen Carin
 *
 */
public class ComplexNumberTest {
	
	public static double THRESHOLD = 1E-4;

	@Test
	public void createComplexFromReal() {
		ComplexNumber c = ComplexNumber.fromReal(4);
		
		assertEquals(new ComplexNumber(4, 0), c);
	}
	
	@Test
	public void createComplexFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(4);
		
		assertEquals(new ComplexNumber(0, 4), c);
	}
	
	@Test
	public void createComplexMagnitudeAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(2, 1);
		
		assertEquals(2, c.getMagnitude(), THRESHOLD);
		assertEquals(1, c.getAngle(), THRESHOLD);
	}
	
	@Test
	public void stringToComplex1() {
		ComplexNumber c = ComplexNumber.parse("-3.1i");
		assertEquals(new ComplexNumber(0, -3.1), c);
	}
	
	@Test
	public void stringToComplex2() {
		ComplexNumber c = ComplexNumber.parse("1+5i");
		assertEquals(new ComplexNumber(1, 5), c);
	}
	
	@Test
	public void stringToComplex3() {
		ComplexNumber c = ComplexNumber.parse("i");
		assertEquals(new ComplexNumber(0, 1), c);
	}
	
	@Test
	public void stringToComplex4() {
		ComplexNumber c = ComplexNumber.parse("1");
		assertEquals(new ComplexNumber(1, 0), c);
	}
	
	@Test
	public void stringToComplex5() {
		ComplexNumber c = ComplexNumber.parse("-2.71-3.15i");
		assertEquals(new ComplexNumber(-2.71, -3.15), c);
	}
	
	@Test
	public void stringToComplex6() {
		ComplexNumber c = ComplexNumber.parse("3.51");
		assertEquals(new ComplexNumber(3.51, 0), c);
	}
	
	@Test
	public void returnReal() {
		ComplexNumber c = new ComplexNumber(3, 4);
		assertEquals(3, c.getReal(), THRESHOLD);
	}
	
	@Test
	public void returnImaginary() {
		ComplexNumber c = new ComplexNumber(3, 4);
		assertEquals(4, c.getImaginary(), THRESHOLD);
	}
	
	@Test
	public void returnMagnitude() {
		ComplexNumber c = new ComplexNumber(1, 3);
		assertEquals(Math.sqrt(10), c.getMagnitude(), THRESHOLD);
	}
	
	@Test
	public void returnAngle() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(Math.PI/4, c.getAngle(), THRESHOLD);
	}
	
	@Test
	public void addComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1, 3);
		ComplexNumber c2 = new ComplexNumber(4, 2);
		ComplexNumber c3 = c1.add(c2);
		assertEquals(new ComplexNumber(5, 5), c3);
	}
	
	@Test
	public void subComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1, 3);
		ComplexNumber c2 = new ComplexNumber(4, 2);
		ComplexNumber c3 = c1.sub(c2);
		assertEquals(new ComplexNumber(-3, 1), c3);
	}
	
	@Test
	public void mulComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1, 3);
		ComplexNumber c2 = new ComplexNumber(4, 2);
		ComplexNumber c3 = c1.mul(c2);
		assertEquals(new ComplexNumber(-2, 14), c3);
	}
	
	@Test
	public void divComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(20, -4);
		ComplexNumber c2 = new ComplexNumber(3, 2);
		ComplexNumber c3 = c1.div(c2);
		assertEquals(new ComplexNumber(4, -4), c3);
	}
	
	@Test
	public void calculatePower() {
		ComplexNumber c = new ComplexNumber(3, 3);
		assertEquals(new ComplexNumber(-972,  -972).getReal(), c.power(5).getReal(), THRESHOLD);
		assertEquals(new ComplexNumber(-972,  -972).getImaginary(), c.power(5).getImaginary(), THRESHOLD);
	}
	
	@Test
	public void calculateRoot() {
		ComplexNumber c = new ComplexNumber(1, 0);
		assertEquals(new ComplexNumber(1, 0), c.root(2)[0]);
	}
}
