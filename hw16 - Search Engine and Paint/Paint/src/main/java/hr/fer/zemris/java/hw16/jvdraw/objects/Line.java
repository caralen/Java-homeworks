package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * The Class Line represents a straight geometrical object.
 */
public class Line extends GeometricalObject {
	
	/** The start point of the line. */
	private Point2D startPoint;
	
	/** The end point of the line. */
	private Point2D endPoint;
	
	/** The color of the line. */
	private Color color;
	
	/**
	 * Instantiates a new line.
	 *
	 * @param provider the color provider
	 * @param startPoint the start point of the line
	 * @param endPoint the end point of the line
	 */
	public Line(IColorProvider provider, Point2D startPoint, Point2D endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.color = provider.getCurrentColor();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		if(endPoint == null) return;
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	/**
	 * Returns the start point.
	 *
	 * @return the start point
	 */
	public Point2D getStartPoint() {
		return startPoint;
	}

	/**
	 * Sets the start point.
	 * Fires notifications to the observers.
	 *
	 * @param startPoint the new start point
	 */
	public void setStartPoint(Point2D startPoint) {
		this.startPoint = startPoint;
		fire(this);
	}

	/**
	 * Returns the end point.
	 *
	 * @return the end point
	 */
	public Point2D getEndPoint() {
		return endPoint;
	}

	/**
	 * Sets the end point.
	 * Fires notifications to the observers.
	 *
	 * @param endPoint the new end point
	 */
	public void setEndPoint(Point2D endPoint) {
		this.endPoint = endPoint;
		fire(this);
	}

	/**
	 * Returns the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color.
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
		return String.format("Line (%d,%d)-(%d,%d)", (int)startPoint.getX(), (int)startPoint.getY(), (int)endPoint.getX(), (int)endPoint.getY());
	}
}
