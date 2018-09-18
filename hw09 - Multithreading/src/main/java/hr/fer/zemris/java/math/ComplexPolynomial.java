package hr.fer.zemris.java.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a complex polynomial.
 * @author Alen Carin
 *
 */
public class ComplexPolynomial {
	
	/** Array of factors, nth factor multiplies nth power of z. */
	private Complex[] factors;
	
	
	/**
	 * Instantiates a new complex polynomial.
	 *
	 * @param factors {@link #factors}
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}

	/**
	 * Order of the polynomial.
	 *
	 * @return value of the order of the polynomial
	 */
	public short order() {
		return (short) (factors.length-1);
	}

	/**
	 * Multiplies this polynomial with the polynomial passed through arguments.
	 *
	 * @param poly another polynomial which is used in multiplication
	 * @return the result of the multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial poly) {
		Objects.requireNonNull(poly);
		
		Complex[] newFactors = new Complex[factors.length + poly.factors.length - 1];
		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = Complex.ZERO;
		}

		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < poly.factors.length; j++) {
				newFactors[i + j] = newFactors[i + j].add((poly.factors[j]).multiply(factors[i]));
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Derives this polynomial.
	 *
	 * @return derivation of this polynomial
	 */
	public ComplexPolynomial derive() {
		
		Complex[] factorsArray = new Complex[factors.length-1];
		for(int i = 0; i < factors.length-1; i++) {
			factorsArray[i] = factors[i].multiply(new Complex(factors.length-1-i, 0));
		}
		
		return new ComplexPolynomial(factorsArray);
	}

	/**
	 * Calculates the value when the complex number is applied to the current polynomial.
	 *
	 * @param z is a complex number which is applied to this polynomial
	 * @return complex number that is the result of the polynomial with z applied
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		
		for(int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(factors.length-1-i)));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int counter = factors.length-1;
		
		for(Complex factor : factors) {
			sb.append(factor.toString() + "*z^" + counter + "+");
			counter--;
		}
		return sb.toString().substring(0, sb.length()-1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(factors);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		if (!Arrays.equals(factors, other.factors))
			return false;
		return true;
	}
	
	
}