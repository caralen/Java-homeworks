package hr.fer.zemris.java.hw16.jvdraw.visitors;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

/**
 * The Class GeometricalObjectSaver is a visitor which is used for saving each of the geometrical objects.
 */
public class GeometricalObjectSaver implements GeometricalObjectVisitor {
	
	/** The list of lines. */
	private List<String> lines = new ArrayList<>();

	/**
	 * Creates a correctly formatted line for the line object and stores it to the list.
	 */
	@Override
	public void visit(Line line) {
		Point2D start = line.getStartPoint();
		Point2D end = line.getEndPoint();
		Color color = line.getColor();
		
		lines.add(
				String.format("LINE %d %d %d %d %d %d %d", 
						(int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY(),
						(int)color.getRed(), (int)color.getGreen(), (int)color.getBlue()
						)
				);
	}

	/**
	 * Creates a correctly formatted line for the circle object and stores it to the list.
	 */
	@Override
	public void visit(Circle circle) {
		Point2D center = circle.getCenter();
		Color color = circle.getColor();
		double radius = circle.getRadius();
		
		lines.add(
				String.format("CIRCLE %d %d %d %d %d %d", 
						(int)center.getX(), (int)center.getY(), (int)radius, 
						(int)color.getRed(), (int)color.getGreen(), (int)color.getBlue()
						)
				);
	}

	/**
	 * Creates a correctly formatted line for the filled circle object and stores it to the list.
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		Point2D center = filledCircle.getCenter();
		Color outColor = filledCircle.getOutlineColor();
		Color areaColor = filledCircle.getAreaColor();
		double radius = filledCircle.getRadius();
		
		lines.add(
				String.format("FCIRCLE %d %d %d %d %d %d %d %d %d", 
						(int)center.getX(), (int)center.getY(), (int)radius, 
						(int)outColor.getRed(), (int)outColor.getGreen(), (int)outColor.getBlue(),
						(int)areaColor.getRed(), (int)areaColor.getGreen(), (int)areaColor.getBlue()
						)
				);

	}

	/**
	 * Returns a string containing all the formatted lines of every object that was visited.
	 *
	 * @return the lines containing information about objects.
	 */
	public String getLines(){
		StringBuilder sb = new StringBuilder();
		for(String line: lines) {
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}
}
