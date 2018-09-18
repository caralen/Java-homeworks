package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

/**
 * The Class GeometricalObjectPainter is a visitor which is used for painting of each of the geometrical objects.
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	
	/** The graphics 2d. */
	private Graphics2D g2d;
	
	/**
	 * Instantiates a new geometrical object painter.
	 *
	 * @param g2d the graphics 2d
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	/**
	 * Paints the line object
	 */
	@Override
	public void visit(Line line) {
		Point2D start = line.getStartPoint();
		Point2D end = line.getEndPoint();
		g2d.setColor(line.getColor());
		g2d.drawLine((int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY());
	}

	/**
	 * Paints the circle object
	 */
	@Override
	public void visit(Circle circle) {
		Point2D center = circle.getCenter();
		double radius = circle.getRadius();
		g2d.setColor(circle.getColor());
		g2d.drawOval((int)(center.getX()-radius), (int)(center.getY()-radius), (int)radius*2, (int)radius*2);
	}

	/**
	 * Paints the filled circle object
	 */
	@Override
	public void visit(FilledCircle circle) {
		Point2D center = circle.getCenter();
		double radius = circle.getRadius();
		g2d.setColor(circle.getOutlineColor());
		g2d.drawOval((int)(center.getX()-radius), (int)(center.getY()-radius), (int)radius*2, (int)radius*2);
		g2d.setColor(circle.getAreaColor());
		g2d.fillOval((int)(center.getX()-radius), (int)(center.getY()-radius), (int)radius*2, (int)radius*2);
	}

}
