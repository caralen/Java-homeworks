	package hr.fer.zemris.java.math;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The class that represents a single complex number and performs operations on that number.
 */
public class Complex {

	/** The real part of the complex number. */
	private double real;
	
	/** The imaginary part of the complex number. */
	private double imaginary;
	
	/** The Constant that represents a complex number with both parts equal to zero. */
	public static final Complex ZERO = new Complex(0,0);
	
	/** The Constant that represents a complex number that has real part equal to 1 and imaginary equal to 0. */
	public static final Complex ONE = new Complex(1,0);
	
	/** The Constant that represents a complex number that has real part equal to -1 and imaginary equal to 0. */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**  The Constant that represents a complex number that has real part equal to 0 and imaginary equal to 1. */
	public static final Complex IM = new Complex(0,1);
	
	/** The Constant that represents a complex number that has real part equal to 0 and imaginary equal to -1. */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Gets the real component of the complex number.
	 *
	 * @return the real part of the complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Gets the imaginary component of the complex number.
	 *
	 * @return the imaginary part of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Instantiates a new complex number.
	 *
	 * @param real the real part of the complex number
	 * @param imaginary the imaginary part of the complex number
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Returns module of complex number.
	 *
	 * @return module of the complex number
	 */
	public double module() {
		return sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Returns this complex number multiplied by the complex number given in arguments.
	 *
	 * @param c the complex number for multiplication
	 * @return new instance of complex number that is this complex number multiplied by the given complex number
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);
		
		double realPart = this.real*c.real - this.imaginary*c.imaginary;
		double imaginaryPart = this.real*c.imaginary + this.imaginary*c.real;
		
		return new Complex(realPart, imaginaryPart);
	}
	
	/**
	 * Returns this complex number divided by the complex number given in arguments.
	 *
	 * @param c the complex number for multiplication
	 * @return new instance of complex number that is this complex number divided by the given complex number
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c);
		
		double realNumerator = this.real*c.real + this.imaginary*c.imaginary;
		double imaginaryNumerator = this.imaginary*c.real - this.real * c.imaginary;
		double denominator = pow(c.real, 2) + pow(c.imaginary, 2);
		
		return new Complex(realNumerator/denominator, imaginaryNumerator/denominator);
	}
	
	/**
	 * Returns this complex number summed with the complex number given in arguments.
	 *
	 * @param c the complex number for addition
	 * @return new instance of complex number that is this complex number summed with the given complex number
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);
		
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Returns this complex number subtracted by the complex number given in arguments.
	 *
	 * @param c the complex number for subtraction
	 * @return new instance of complex number that is this complex number subtracted by the given complex number
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);
		
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Returns this complex number negated.
	 *
	 * @return new instance of complex number that is this complex number negated
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}
	
	/**
	 * Returns this number to the n-th power, n is a number passed in arguments.
	 *
	 * @param n is a number that indicates the power of complex number
	 * @return new instance of complex number that is this complex number to the n-th power
	 */
	public Complex power(int n) {
		
		Complex result = Complex.ONE;
		
		for(int i = 0; i < n; i++) {
			result = result.multiply(this);
		}
		return result;
	}
	
	/**
	 * Returns a list of n roots of this complex number, n is a number passed in arguments.
	 *
	 * @param n is a number that indicates the number of roots of the complex number
	 * @return list of roots of this complex number
	 */
	public List<Complex> root(int n) {
		List<Complex> complexNumbers = new ArrayList<>();
		double rootModule = pow(module(), 1.0/n);
		
		for(int k = 0; k < n; k++) {
			double rootReal = rootModule * cos((getAngle() + 2*PI*k) / n);
			double rootImaginary = rootModule * sin((getAngle() + 2*PI*k) / n);
			complexNumbers.add(new Complex(rootReal, rootImaginary));
		}
		return complexNumbers;
	}
	
	@Override
	public String toString() {
		if(this.imaginary < 0) {
			return new String("(" + real + "-" + imaginary + "i" + ")");
		}
		return new String("(" + real + "+" + imaginary + "i" + ")");
	}
	
	/**
	 * Returns the angle of this complex number.
	 *
	 * @return the angle of this complex number
	 */
	private double getAngle() {
		return atan2(imaginary, real);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}
	
	/**
	 * Parses the given complex number.
	 *
	 * @param s is the given string which should be complex number in format a+ib
	 * @return the complex which is generated from the given string after parsing
	 */
	public static Complex parse(String s) {
		s = s.replaceAll("\\s+", "");
		
		if (Pattern.matches("[-]\\d[+][i]\\d", s)) {
			s = s.replace("i", "");
			return new Complex(Integer.parseInt(s.split("\\+")[0]), Integer.parseInt(s.split("\\+")[1]));

		} else if (Pattern.matches("[-]\\d[-][i]\\d", s)) {
			s = s.replace("i", "");
			s = s.substring(1);
			return new Complex(Integer.parseInt("-" + s.split("-")[0]), Integer.parseInt("-" + s.split("-")[1]));

		} else if (Pattern.matches("\\d[+][i]\\d", s)) {
			s = s.replace("i", "");
			return new Complex(Integer.parseInt(s.split("\\+")[0]), Integer.parseInt(s.split("\\+")[1]));

		} else if (Pattern.matches("[-]\\d[-][i]", s)) {
			s = s.replace("i", "");
			s = s.substring(1);
			return new Complex(Integer.parseInt("-" + s.split("-")[0]), Integer.parseInt("-1"));

		} else if (Pattern.matches("\\d[+][i]", s)) {
			s = s.replace("i", "");
			return new Complex(Integer.parseInt(s.split("\\+")[0]), Integer.parseInt("1"));

		} else if (Pattern.matches("[-]\\d[+][i]", s)) {
			return new Complex(Integer.parseInt(s.split("\\+")[0]), Integer.parseInt("1"));

		}else if (Pattern.matches("\\d[-][i]\\d", s)) {
			s = s.replace("i", "");
			return new Complex(Integer.parseInt(s.split("-")[0]), Integer.parseInt("-" + s.split("-")[1]));

		} else if (Pattern.matches("\\d[-][i]", s)) {
			s = s.replace("i", "");
			return new Complex(Integer.parseInt(s.split("-")[0]), Integer.parseInt("-1"));

		} else if (Pattern.matches("[i]\\d", s)) {
			s = s.replace("i", "");
			return new Complex(0, Integer.parseInt(s));

		} else if (Pattern.matches("[-][i]\\d", s)) {
			s = s.replace("i", "");
			return new Complex(0, Integer.parseInt(s));

		} else if (Pattern.matches("\\d", s)) {
			return new Complex(Integer.parseInt(s), 0);

		} else if (Pattern.matches("[-]\\d", s)) {
			return new Complex(Integer.parseInt(s), 0);

		} else if (Pattern.matches("[i]", s)) {
			return new Complex(0, 1);

		} else if (Pattern.matches("[-][i]", s)) {
			return new Complex(0, -1);

		} else {
			throw new IllegalArgumentException("Invalid complex number format, should be: a+ib");
		}
	}
}
