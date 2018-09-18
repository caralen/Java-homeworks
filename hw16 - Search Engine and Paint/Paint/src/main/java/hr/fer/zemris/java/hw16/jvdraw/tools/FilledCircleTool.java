package hr.fer.zemris.java.hw16.jvdraw.tools;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

/**
 * The Class FilledCircleTool.
 * This tool is used to create a FilledCircle object.
 */
public class FilledCircleTool implements Tool {
	
	/** The foreground color provider. */
	private IColorProvider fgProvider;
	
	/** The background color provider. */
	private IColorProvider bgProvider;
	
	/** The drawing model. */
	private DrawingModel model;
	
	/** The center. */
	private Point2D center;
	
	/** The radius. */
	private double radius;
	
	/** The circle. */
	private FilledCircle circle;

	/** The number of clicks. */
	private int clicks = 0;
	
	
	/**
	 * Instantiates a new filled circle tool.
	 *
	 * @param fgProvider the foreground color provider
	 * @param bgProvider the background color provider
	 * @param model the drawing model
	 */
	public FilledCircleTool(IColorProvider fgProvider, IColorProvider bgProvider, DrawingModel model) {
		this.fgProvider = fgProvider;
		this.bgProvider = bgProvider;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * After the first click filled circle object is created and added to the model.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		clicks++;
		
		if(clicks % 2 != 0) {
			center = new Point2D(e.getX(), e.getY());
			circle = new FilledCircle(fgProvider, bgProvider, center, radius);
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
