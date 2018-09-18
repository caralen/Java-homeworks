package hr.fer.zemris.java.fractals;

import java.util.concurrent.Callable;

import hr.fer.zemris.java.math.Complex;
import hr.fer.zemris.java.math.ComplexPolynomial;
import hr.fer.zemris.java.math.ComplexRootedPolynomial;

/**
 * This is a static class which represents a single job where calculation is done.
 * @author Alen Carin
 *
 */
public class Job implements Callable<Void> {
	
	/** The real min. */
	private double reMin;
	
	/** The real max. */
	private double reMax;
	
	/** The imaginary min. */
	private double imMin;
	
	/** The imaginary max. */
	private double imMax;
	
	/** The width. */
	private int width;
	
	/** The height. */
	private int height;
	
	/** The y min. */
	private int yMin;
	
	/** The y max. */
	private int yMax;
	
	/** The m. */
	private int m;
	
	/** The data array. */
	private short[] data;
	
	/** Complex rooted polynomial for which the calculation is done. */
	private ComplexRootedPolynomial rootedPolynomial;
	
	/** Complex polynomial for which the calculation is done*/
	private ComplexPolynomial polynomial;
	
	/** The greatest value the module can be, upper limit for the module. */
	private double moduleLimit;

	/**
	 * Instantiates a new job.
	 *
	 * @param reMin {@link #reMin}
	 * @param reMax {@link #reMax}
	 * @param imMin {@link #imMin}
	 * @param imMax {@link #imMax}
	 * @param width {@link #width}
	 * @param height {@link #height}
	 * @param yMin {@link #yMin}
	 * @param yMax {@link #yMax}
	 * @param m {@link #m}
	 * @param data {@link #data}
	 */
	public Job(double reMin, double reMax, double imMin,
			double imMax, int width, int height, int yMin, int yMax, 
			int m, short[] data, ComplexRootedPolynomial rootedPolynomial, 
			ComplexPolynomial polynomial, double moduleLimit) {
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.m = m;
		this.data = data;
		this.rootedPolynomial = rootedPolynomial;
		this.polynomial = polynomial;
		this.moduleLimit = moduleLimit;
	}
	
	/**
	 * Method where calculations of iterations are done, data array is filled with indexes of closest roots.
	 */
	@Override
	public Void call() {
		
		int offset = yMin * width;
		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
				double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
				Complex zn = new Complex(cre, cim);
				double module;
				int iters = 0;
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = polynomial.derive().apply(zn);
					Complex fraction = numerator.divide(denominator);
					Complex zn1 = zn.sub(fraction);
					module = zn1.sub(zn).module();
					zn = zn1;
					iters++;
				} while (iters < m && module > moduleLimit);
				
				int index = rootedPolynomial.indexOfClosestRootFor(zn, moduleLimit);
				if(index == -1 || iters >= m) {
					data[offset] = (short) 0;
				} else {
					data[offset] = (short) (index);
				}
				offset++;
			}
		}
		System.out.println("Job is done. Let's notify observer i.e. GUI!");
		
		return null;
	}
}
