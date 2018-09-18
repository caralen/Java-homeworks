package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Demo class that is used to try the functionality of the ComplexNumber class
 * @author Alen Carin
 *
 */
public class ComplexDemo {

	/**
	 * Method that is called upon start of the programme
	 * @param args - array of arguments, not used in this task
	 */
	public static void main(String[] args) {

		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("  2.5   -3 i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}

}
