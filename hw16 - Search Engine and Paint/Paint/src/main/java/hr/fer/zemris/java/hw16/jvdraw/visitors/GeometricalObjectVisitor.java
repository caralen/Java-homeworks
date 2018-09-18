package hr.fer.zemris.java.hw16.jvdraw.visitors;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * The Interface GeometricalObjectVisitor is used for implementing the Visitor design pattern.
 * 
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Visit method for the line object.
	 *
	 * @param line the line object
	 */
	public abstract void visit(Line line);

	/**
	 * Visit method for the circle object.
	 *
	 * @param circle the circle object
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visit method for the filled circle object.
	 *
	 * @param filledCircle the filled circle object
	 */
	public abstract void visit(FilledCircle filledCircle);
}