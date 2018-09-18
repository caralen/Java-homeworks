package hr.fer.zemris.java.hw02;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Class that represents a complex number. It consists of real and imaginary part.
 * <br>It provides static factory methods, 
 * methods for information retrieval and methods which allow calculation.
 * 
 * @author Alen Carin
 *
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;

	/**
	 * Constructor which takes two parameters, real and imaginary part and creates a complex number.
	 * @param real part of the complex number
	 * @param imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Creates a new complex number with only real part. Imaginary part is set to zero.
	 * @param real part of the complex number
	 * @return new complex number created from the given argument
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Creates a new complex number with only imaginary part. Real part is set to zero.
	 * @param imaginary part of the complex number
	 * @return new complex number created from the given argument
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Calculates real and imaginary part from magnitude and angle.
	 * Creates a new complex number with calculated parameters.
	 * @param magnitude
	 * @param angle
	 * @return new instance of complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude*cos((angle % (2 * PI))), magnitude*sin(angle % (2 * PI)));
	}
	
	/**
	 * Static method which is used to parse the given string which contains complex number.
	 * @param string which should be parsed to create complex number
	 * @return new instance of complex number parsed from string
	 */
	public static ComplexNumber parse(String s) {
		String[] parts;
		s.trim();
		
		if(s.equals("i")) {
			return new ComplexNumber(0, 1);
		}
//		Za oblik tipa: x-yi
		else if(s.contains("-") && !s.startsWith("-")) {
			s = s.replace("i", "");
			parts = s.split("-");
			try {
				return new ComplexNumber(Double.parseDouble(parts[0]), Double.parseDouble("-" + parts[1]));
			} catch(NumberFormatException ex) {
				System.out.println("Invalid arguments for method parse");
			}
		}
		else if(s.contains("-") && s.contains("i")) {
			s = s.replace("i", "");
			parts = s.split("-");
			
//			za oblik tipa -x-yi
			if(s.split("-").length == 3) {
				return new ComplexNumber(Double.parseDouble("-" + parts[1]), Double.parseDouble("-" + parts[2]));
			}
			
//			za oblik tipa -x+yi
			else if(s.contains("-") && s.contains("+")) {
				return new ComplexNumber(Double.parseDouble("-" + parts[0]), Double.parseDouble(parts[1]));
			}
			
//			za oblik tipa -yi
			else {
				return new ComplexNumber(0, Double.parseDouble("-" + parts[1]));
			}
		}
//		za oblik tipa: x+yi
		else if(s.contains("+")) {
			s = s.replace("i", "");
			parts = s.split("\\+");
			try {
				return new ComplexNumber(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
			} catch(NumberFormatException ex) {
				System.out.println("Invalid arguments for method parse");
			}
		}
//		za oblik tipa yi
		else if(s.contains("i")) {
			s = s.replace("i", "");
			try {
				return new ComplexNumber(0, Double.parseDouble(s));
			} catch(NumberFormatException ex) {
				System.out.println("Invalid arguments for method parse");
			}
		}
//		za oblik tipa x
		else {
			try {
				return new ComplexNumber(Double.parseDouble(s), 0);
			} catch (NumberFormatException ex) {
				System.out.println("Invalid arguments for method parse");
			}
		}
		throw new IllegalArgumentException("Illegal arguments are passed to parse() method");
	}
	
	/**
	 * Returns real part of the complex number.
	 * @return real part
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns imaginary part of the complex number.
	 * @return imaginary part
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns magnitude of the complex number
	 * @return magnitude
	 */
	public double getMagnitude() {
		return sqrt(pow(real, 2) + pow(imaginary, 2));
	}
	
	/**
	 * Returns angle of the complex number
	 * @return angle
	 */
	public double getAngle() {
		return atan2(imaginary, real);
	}
	
	/**
	 * Adds complex number on which the method is called with complex number that is passed by argument.
	 * @param complex number which should be added
	 * @return new complex number which is sum of the two given numbers
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Subtracts the complex number which is passed as argument from the one on which the method is called
	 * @param subtracting complex number
	 * @return resulting complex number
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Multiplies complex numbers.
	 * @param multiplier
	 * @return complex number which is the result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double realPart = this.real*c.real - this.imaginary*c.imaginary;
		double imaginaryPart = this.real*c.imaginary + this.imaginary*c.real;
		
		return new ComplexNumber(realPart, imaginaryPart);
	}
	
	/**
	 * Divides two complex numbers
	 * @param divisor
	 * @return complex number which is the result of division
	 */
	public ComplexNumber div(ComplexNumber c) {
		double realDenominator = this.real*c.real + this.imaginary*c.imaginary;
		double imaginaryDenominator = this.imaginary*c.real - this.real * c.imaginary;
		double numerator = pow(c.real, 2) + pow(c.imaginary, 2);
		
		return new ComplexNumber(realDenominator/numerator, imaginaryDenominator/numerator);
	}
	
	/**
	 * Evaluates the complex number to the power of the passed argument.
	 * @param exponent value
	 * @return new complex number which is the result of the operation
	 */
	public ComplexNumber power(int n) {
		double powerMagnitude = pow(getMagnitude(), n);
		
		return new ComplexNumber(powerMagnitude * cos(n*getAngle()),  powerMagnitude * sin(n*getAngle()));
	}
	
	/**
	 * Computes n-th root of the complex number. Returns array of roots.
	 * @param root value
	 * @return array of roots
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("For root to be retrieved, argument must be greater than 0");
		}
		ComplexNumber[] roots = new ComplexNumber[n];
		double rootMagnitude = pow(getMagnitude(), 1.0/n);
		
		for(int k = 0; k < n; k++) {
			double rootReal = rootMagnitude * cos((getAngle() + 2*PI*k) / n);
			double rootImaginary = rootMagnitude * sin((getAngle() + 2*PI*k) / n);
			roots[k] = new ComplexNumber(rootReal, rootImaginary);
		}
		return roots;
	}
	
	@Override
	public String toString() {
		return new String("(" + real + ", " + imaginary + "i)");
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
		ComplexNumber other = (ComplexNumber) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}
	public static int findMinuses(String str) {
		String findString = "-";
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){
		    lastIndex = str.indexOf(findString,lastIndex);
		    
		    if(lastIndex != -1){
		        count ++;
		        lastIndex += findString.length();
		    }
		}
		return count;
	}
	
}
