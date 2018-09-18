package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Represents a state of the turtle which keeps information about the turtle.
 * @author Alen Carin
 *
 */
public class TurtleState {

	Vector2D currentPosition;
	Vector2D direction;
	Color color;
	double unitLength;
	
	/**
	 * Constructor. Sets field values to the given argument values.
	 * @param currentPosition
	 * @param direction
	 * @param color
	 * @param unitLength
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double unitLength) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.unitLength = unitLength;
	}
	
	/**
	 * Returns current position of the turtle. 
	 * Current position is a 2D vector that represents 
	 * distance from the origin of the coordinate system.
	 * @return current position of the turtle
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Returns direction in which the turtle is looking.
	 * Direction is a 2D vector with length equal to 1.
	 * @return
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Returns the color turtle is currently using for drawing.
	 * @return color that is currently in use.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns a double value of unit length.
	 * @return value of unit length.
	 */
	public double getUnitLength() {
		return unitLength;
	}

	/**
	 * Returns a copy of the current turtle state.
	 * @return newly instantiated copy of the current turtle state.
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), direction.copy(), color, unitLength);
	}
}
