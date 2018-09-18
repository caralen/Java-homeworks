package hr.fer.zemris.java.hw16.jvdraw.utils;

/**
 * The Class Point2D represents a single 2d point with x and y values.
 */
public class Point2D {

	/** The x. */
	private double x;
	
	/** The y. */
	private double y;
	
	/**
	 * Instantiates a new point 2 D.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	
}
