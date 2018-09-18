package hr.fer.zemris.java.hw16.trazilica;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.sqrt;


/**
 * Class that represents a single 3D vector, with x, y, and z components.
 * @author Alen Carin
 *
 */
public final class Vector {
	
	private List<Double> words;
	
	public Vector(int dimensions) {
		words = new ArrayList<>();
		for(int i = 0; i < dimensions; i++) {
			words.add(0.0);
		}
	}

	/**
	 * Increments vector's value at the given index.
	 * @param index of the value that will be incremented
	 */
	public void increment(int index) {
		words.set(index, words.get(index) + 1);
	}
	
	/**
	 * Sets vector's value at the given index
	 * @param index of the value that will be incremented
	 * @param value that will be stored in vector instead of the one which was stored before at the given index
	 */
	public void setValue(int index, double value) {
		words.set(index, value);
	}
	
	/**
	 * Returns a norm of this vector.
	 *
	 * @return the double of the norm of this vector
	 */
	public double norm() {
		double sum = 0;
		for(Double value : words) {
			sum += value*value;
		}
		return sqrt(sum);
	}
	
	/**
	 * Multiplies elements of this and given vector.
	 * Elements at the same index are multiplied.
	 * Given vector must have the same length as this vector.
	 * @param other vector
	 */
	public void multiplyElements(Vector other) {
		Objects.requireNonNull(other);
		
		if(other.size() != this.size()) {
			throw new IllegalArgumentException("Vectors must be of the same length");
		}
		
		for(int i = 0; i < this.size(); i++) {
			words.set(i, words.get(i) * other.get(i));
		}
	}
	
	
	/**
	 * Calculates the dot product of this and given vector.
	 *
	 * @param other is a 3D vector that will be used in dot product.
	 * @return result of the dot product
	 */
	public double dot(Vector other) {
		Objects.requireNonNull(other);
		
		double sum = 0;
		int lengthThis = this.size();
		int lengthOther = other.size();
		
		if(lengthOther != lengthThis) {
			throw new IllegalArgumentException("Vectors must be of the same dimension!");
		}
		for(int i = 0; i < lengthThis; i++) {
			sum += this.get(i) * other.get(i);
		}
		
		return sum;
	}
	
	
	/**
	 * Calculates the angle between this vector and the one passed through arguments.
	 *
	 * @param other is a 3D vector.
	 * @return value of the angle between two vectors
	 */
	public double cosAngle(Vector other) {
		return this.dot(other) / (this.norm() * other.norm());
	}
	
	public double get(int index) {
		return words.get(index);
	}
	
	public int size() {
		return words.size();
	}

	/**
	 * Returns this vector as a three field array of doubles, 
	 * each field represents a single component of the vector.
	 *
	 * @return array of doubles representing this vector
	 */
	public double[] toArray() {
		int size = words.size();
		double[] array = new double[size];
		
		for(int i = 0; i < size; i++) {
			array[i] = words.get(i);
		}
		return array;
	}

	@Override
	public String toString() {
		return words.toString();
	}

}
