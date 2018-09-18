package hr.fer.zemris.java.math;

import static java.lang.Math.sqrt;

import java.util.Objects;


/**
 * Class that represents a single 3D vector, with x, y, and z components.
 * @author Alen Carin
 *
 */
public final class Vector3 {

	/** x component of the 3D vector. */
	private double x;
	
	/** y component of the 3D vector. */
	private double y;
	
	/** z component of the 3D vector. */
	private double z;
	
	
	/**
	 * Instantiates a new vector 3.
	 *
	 * @param x component of the 3D vector
	 * @param y component of the 3D vector
	 * @param z component of the 3D vector
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns a norm of this vector.
	 *
	 * @return the double of the norm of this vector
	 */
	public double norm() {
		return sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Returns a new instance of 3D vector which is this vector, but normalized.
	 *
	 * @return normalized 3D vector
	 */
	public Vector3 normalized() {
		if(this.norm() == 0) {
			return this;
		}
		else {
			return this.scale(1 / this.norm());
		}
	}
	
	/**
	 * Adds the given vector with this vector and returns a new instance of 3D vector that is the result.
	 *
	 * @param other 3D vector that will be added to this vector
	 * @return new instance of 3D vector that is the result of addition
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other);
		
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	
	/**
	 * Subtracts the given vector from this vector and returns a new 3D vector which is the result of subtraction.
	 *
	 * @param other 3D vector that will be subtracted from this vector
	 * @return new instance of 3D vector that is the result of subtraction
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other);
		
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}
	
	/**
	 * Calculates the dot product of this and given vector.
	 *
	 * @param other is a 3D vector that will be used in dot product.
	 * @return result of the dot product
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other);
		
		return this.x*other.x + this.y*other.y + this.z*other.z;
	}
	
	/**
	 * Calculates cross product of this and vector passed through arguments,
	 * the result is a new 3D vector which is vertical on both vectors.
	 *
	 * @param other is a 3D vector that will be used in cross product.
	 * @return new 3D vector which is the result of cross product
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other);
		
		double iComponent = this.y*other.z - this.z*other.y;
		double jComponent = this.z*other.x - this.x*other.z;
		double kComponent = this.x*other.y - this.y*other.x;
		
		return new Vector3(iComponent, jComponent, kComponent);
	}
	
	/**
	 * Multiplies this vector by the double value passed through arguments.
	 *
	 * @param scaler is a value which the vector will be scaled with
	 * @return new 3D vector which is the result of scaling
	 */
	public Vector3 scale(double scaler) {
		return new Vector3(this.x*scaler, this.y*scaler, this.z*scaler);
	}
	
	/**
	 * Calculates the angle between this vector and the one passed through arguments.
	 *
	 * @param other is a 3D vector.
	 * @return value of the angle between two vectors
	 */
	public double cosAngle(Vector3 other) {
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Gets the x component.
	 *
	 * @return the x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the y component.
	 *
	 * @return the y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Gets the z component.
	 *
	 * @return the z component
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns this vector as a three field array of doubles, 
	 * each field represents a single component of the vector.
	 *
	 * @return array of doubles representing this vector
	 */
	public double[] toArray() {
		return new double[]{x, y, z};
	}

	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3 other = (Vector3) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
}
