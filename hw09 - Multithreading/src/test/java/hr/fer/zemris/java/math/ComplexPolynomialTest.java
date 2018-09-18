package hr.fer.zemris.java.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.math.Complex;
import hr.fer.zemris.java.math.ComplexPolynomial;
import hr.fer.zemris.java.math.ComplexRootedPolynomial;

/**
 * Class used for testing the functionalities of the <code>ComplexPolynomial</code>.
 * @author Alen Carin
 *
 */
public class ComplexPolynomialTest {


	@Test
	public void test1() {
		Complex[] roots = new Complex[] {Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG};
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(roots);
		ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
		
		Complex[] expectedFactors = new Complex[] {
				Complex.ONE, 
				Complex.ZERO, 
				Complex.ZERO, 
				Complex.ZERO, 
				Complex.ONE_NEG};
		
		Complex[] expectedFactorsDerived = new Complex[] {
				new Complex(4, 0), 
				Complex.ZERO, 
				Complex.ZERO, 
				Complex.ZERO};
		
		assertEquals(new ComplexPolynomial(expectedFactors), polynomial);
		assertEquals(new ComplexPolynomial(expectedFactorsDerived), polynomial.derive());
	}
}
