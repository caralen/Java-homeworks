package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * The Class FilledCircle represents a round geometrical object with the filled area.
 */
public class FilledCircle extends GeometricalObject {
	
	/** The center of the circle. */
	private Point2D center;
	
	/** The radius of the circle. */
	private double radius;
	
	/** The color of the outline. */
	private Color outlineColor;
	
	/** The color of the area inside circle. */
	private Color areaColor;
	
	/**
	 * Instantiates a new filled circle.
	 *
	 * @param fgProvider the foreground provider
	 * @param bgProvider the background provider
	 * @param center the center of the circle
	 * @param radius the radius of the circle
	 */
	public FilledCircle(IColorProvider fgProvider, IColorProvider bgProvider, Point2D center, double radius) {
		this.outlineColor = fgProvider.getCurrentColor();
		this.areaColor = bgProvider.getCurrentColor();
		this.center = center;
		this.radius = radius;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}
	
	/**
	 * Returns the center.
	 *
	 * @return the center
	 */
	public Point2D getCenter() {
		return center;
	}

	/**
	 * Sets the center.
	 * Fires notifications to the observers.
	 *
	 * @param center the new center
	 */
	public void setCenter(Point2D center) {
		this.center = center;
		fire(this);
	}

	/**
	 * Returns the radius.
	 *
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Sets the radius.
	 * Fires notifications to the observers.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
		fire(this);
	}

	/**
	 * Returns the outline color.
	 *
	 * @return the outline color
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * Sets the outline color.
	 * Fires notifications to the observers.
	 *
	 * @param outlineColor the new outline color
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
		fire(this);
	}

	/**
	 * Returns the area color.
	 *
	 * @return the area color
	 */
	public Color getAreaColor() {
		return areaColor;
	}

	/**
	 * Sets the area color.
	 * Fires notifications to the observers.
	 *
	 * @param areaColor the new area color
	 */
	public void setAreaColor(Color areaColor) {
		this.areaColor = areaColor;
		fire(this);
	}

	@Override
	public String toString() {
		return String.format("Filled circle (%d,%d), %d, #%02x%02x%02x"
				, (int)center.getX(), (int)center.getY(), (int)radius
				, areaColor.getRed(), areaColor.getGreen(), areaColor.getBlue());
	}
}
