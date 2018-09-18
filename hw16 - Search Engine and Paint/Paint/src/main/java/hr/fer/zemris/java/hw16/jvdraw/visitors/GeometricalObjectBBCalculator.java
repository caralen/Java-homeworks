package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

/**
 * The Class GeometricalObjectBBCalculator is a visitor which is used for calculating the coordinates
 * of the bounding box of the painting.
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	
	/** The min x coordinate. */
	private int minX = Integer.MAX_VALUE;
	
	/** The max x coordinate. */
	private int maxX = Integer.MIN_VALUE;
	
	/** The min y coordinate. */
	private int minY = Integer.MAX_VALUE;
	
	/** The max y coordinate. */
	private int maxY = Integer.MIN_VALUE;

	/**
	 * Checks if the coordinates of the line object are smaller than the minimal coordinates of the bounding box
	 * or if the coordinates are bigger than the maximal coordinates of the bounding box.
	 */
	@Override
	public void visit(Line line) {
		Point2D start = line.getStartPoint();
		Point2D end = line.getEndPoint();
		
		minX = (int) (start.getX() < minX ? start.getX() : minX);
		minX = (int) (end.getX() < minX ? end.getX() : minX);
		maxX = (int) (start.getX() > maxX ? start.getX() : maxX);
		maxX = (int) (end.getX() > maxX ? end.getX() : maxX);
		
		minY = (int) (start.getY() < minY ? start.getY() : minY);
		minY = (int) (end.getY() < minY ? end.getY() : minY);
		maxY = (int) (start.getY() > maxY ? start.getY() : maxY);
		maxY = (int) (end.getY() > maxY ? end.getY() : maxY);
	}

	/**
	 * Checks if the coordinates of the circle object are smaller than the minimal coordinates of the bounding box
	 * or if the coordinates are bigger than the maximal coordinates of the bounding box.
	 */
	@Override
	public void visit(Circle circle) {
		Point2D center = circle.getCenter();
		double radius = circle.getRadius();
		
		minX = (int) (center.getX() - radius) < minX ? (int) (center.getX() - radius) : minX;
		maxX = (int) (center.getX() + radius) > maxX ? (int) (center.getX() + radius) : maxX;
		minY = (int) (center.getY() - radius) < minY ? (int) (center.getY() - radius) : minY;
		maxY = (int) (center.getY() + radius) > maxY ? (int) (center.getY() + radius) : maxY;
	}

	/**
	 * Checks if the coordinates of the filled circle object are smaller than the minimal coordinates of the bounding box
	 * or if the coordinates are bigger than the maximal coordinates of the bounding box.
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		visit(new Circle(new JColorArea(filledCircle.getOutlineColor()), filledCircle.getCenter(), filledCircle.getRadius()));
	}
	
	/**
	 * Returns the bounding box of the picture.
	 *
	 * @return the bounding box
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, maxX-minX, maxY-minY);
	}
}
