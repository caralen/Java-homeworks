package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

/**
 * The Class CircleTool is part of the State design pattern.
 * This tool is used to create a Circle object.
 */
public class CircleTool implements Tool {
	
	/** The color provider. */
	private IColorProvider colorProvider;
	
	/** The model. */
	private DrawingModel model;
	
	/** The center. */
	private Point2D center;
	
	/** The radius. */
	private double radius;
	
	/** The circle. */
	private Circle circle;
	
	/** The number of clicks. */
	private int clicks = 0;
	
	/**
	 * Instantiates a new circle tool.
	 *
	 * @param colorProvider the color provider
	 * @param model the drawing model
	 */
	public CircleTool(IColorProvider colorProvider, DrawingModel model) {
		this.colorProvider = colorProvider;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * After the first click circle object is created and added to the model.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		clicks++;
		
		if(clicks % 2 != 0) {
			center = new Point2D(e.getX(), e.getY());
			circle = new Circle(colorProvider, center, radius);
			model.add(circle);
		}
	}

	/**
	 * As the mouse moves, radius of the circle is calculated and stored in the circle object.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if(clicks % 2 != 0) {
			radius = sqrt(pow(center.getX() - e.getX(), 2) + pow(center.getY() - e.getY(), 2));
			circle.setRadius(radius);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
	}

}
