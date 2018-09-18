package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Class that represents a single 2D vector.
 * @author Alen Carin
 *
 */
public class Vector2D {
	
	private double x;
	private double y;
	
	/**
	 * Constructor which creates a new 2D vector from the given arguments.
	 * @param x size of the vector on the X-axis.
	 * @param y size of the vector on the Y-axis.
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns X-axis value of the vector.
	 * @return X-axis value.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns Y-axis value of the vector.
	 * @return Y-axis value.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates the current vector for the offset vector given in arguments.
	 * @param offset for which the current vector will be translated.
	 */
	public void translate(Vector2D offset) {
		this.x = this.getX() + offset.getX();
		this.y = this.getY() + offset.getY();
	}
	
	/**
	 * Returns a new instance of 2D vector which is current vector 
	 * translated for the given offset.
	 * @param offset for which the current vector will be translated.
	 * @return new instance of <code>Vector2D</code> equal to 
	 * current vector translated for the given offset vector.
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D translatedVector = new Vector2D(this.getX(), this.getY());
		translatedVector.translate(offset);
		return translatedVector;
	}

	/**
	 * Rotates this vector counter-clockwise for the given angle.
	 * @param angle in degrees for which this vector will be rotated.
	 */
	public void rotate(double angle) {
		double newX = this.x * cos(toRadians(angle)) - this.y * sin(toRadians(angle));
		double newY = this.x * sin(toRadians(angle)) + this.y * cos(toRadians(angle));
		
		this.x = newX;
		this.y = newY;
	}
	
	/**
	 * Returns a new instance of 2D vector which is current vector 
	 * rotated counter-clockwise for the given angle in degrees.
	 * @param angle for which this vector will be rotated.
	 * @return new instance of <code>Vector2D</code> equal to 
	 * this vector rotated for the given angle.
	 */
	public Vector2D rotated(double angle) {
		Vector2D rotatedVector = new Vector2D(this.getX(), this.getY());
		rotatedVector.rotate(angle);
		return rotatedVector;
	}
	
	/**
	 * Scales this vector by the given scale factor.
	 * @param scaler is the value by which the vector will be scaled
	 */
	public void scale(double scaler) {
		this.x = this.getX() * scaler;
		this.y = this.getY() * scaler;
	}
	
	/**
	 * Returns a new instance of 2D vector which is current vector 
	 * scaled by the given scaler value.
	 * @param scaler is the value by which the vector will be scaled
	 * @return new instance of <code>Vector2D</code> equal to 
	 * this vector rotated for the given angle.
	 */
	public Vector2D scaled(double scaler) {
		Vector2D scaledVector = new Vector2D(this.getX(), this.getY());
		scaledVector.scale(scaler);
		return scaledVector;
	}

	/**
	 * Returns a new instance of 2D vector which is a copy of this vector.
	 * @return copy of the current vector.
	 */
	public Vector2D copy() {
		return new Vector2D(this.getX(), this.getY());
	}
}
