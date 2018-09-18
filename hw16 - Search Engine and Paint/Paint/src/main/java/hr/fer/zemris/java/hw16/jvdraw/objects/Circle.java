package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * The Class Circle represents a round geometrical object without the filled area.
 */
public class Circle extends GeometricalObject {

	/** The center of the circle. */
	private Point2D center;
	
	/** The radius of the circle. */
	private double radius;
	
	/** The color of the circle. */
	private Color color;

	/**
	 * Instantiates a new circle.
	 *
	 * @param provider the color provider
	 * @param center the center of the circle
	 * @param radius the radius of the circle
	 */
	public Circle(IColorProvider provider, Point2D center, double radius) {
		this.center = center;
		this.radius = radius;
		color = provider.getCurrentColor();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	/**
	 * Returns the center of the circle.
	 *
	 * @return the center
	 */
	public Point2D getCenter() {
		return center;
	}

	/**
	 * Sets the center of the circle.
	 * Fires notifications to the observers.
	 *
	 * @param center the new center
	 */
	public void setCenter(Point2D center) {
		this.center = center;
		fire(this);
	}

	/**
	 * Returns the radius of the circle.
	 *
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Sets the radius of the circle.
	 * Fires notifications to the observers.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
		fire(this);
	}

	/**
	 * Returns the color of the circle.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the circle.
	 * Fires notifications to the observers.
	 *
	 * @param color the new color
	 */
	public void setColor(Color color) {
		this.color = color;
		fire(this);
	}
	

	@Override
	public String toString() {
		return String.format("Circle (%d,%d), %d", (int)center.getX(), (int)center.getY(), (int)radius);
	}
}
