package hr.fer.zemris.java.math;

import java.util.Objects;

/**
 * Class represents a polynomial which is defined with complex number roots.
 * @author Alen Carin
 *
 */
public class ComplexRootedPolynomial {
	
	/** Roots of a polynomial. */
	private Complex[] roots;

	/**
	 * Instantiates a new complex rooted polynomial.
	 *
	 * @param roots {@link #roots}
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		this.roots = roots;
	}
	
	/**
	 * Calculates the value of the polynomial by applying the complex number given in arguments
	 * into the rooted polynomial.
	 *
	 * @param z is a complex number which is applied in this polynomial
	 * @return the complex number which is the result of the z applied to this polynomial
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);
		Complex result = Complex.ONE;
		
		for (int i = 0; i < roots.length; i++) {
				result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}
	
	/**
	 * Converts the rooted complex polynomial to complex polynomial.
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);
		
		for(Complex root : roots) {
			result = result.multiply(new ComplexPolynomial(Complex.ONE, root.negate()));
		}
		return result;
	}
	
	
	/**
	 * Calculates the index of the closest root to the z passed through arguments.
	 *
	 * @param z is the complex number for which the closest root will be searched for
	 * @param threshold is the upper boundary
	 * @return the index of the root which is the closest
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		Objects.requireNonNull(z);
		
		int index = -1;
		int counter = 1;
		double minDistance = Double.MAX_VALUE;
		
		for(Complex root : roots) {
			double module = z.sub(root).module();
			
			if(module < threshold && module < minDistance) {
				index = counter;
				minDistance = module;
			}
			counter++;
		}
		return index;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < roots.length; i++) {
			sb.append(String.format("(z-%s)*", roots[i].toString()));
		}
		return sb.toString().substring(0, sb.length()-1);
	}
}
