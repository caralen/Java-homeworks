package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

/**
 * The Class LineTool.
 * This tool is used to create a Line object.
 */
public class LineTool implements Tool {
	
	/** The color provider. */
	private IColorProvider colorProvider;
	
	/** The model. */
	private DrawingModel model;
	
	/** The start point. */
	private Point2D start;
	
	/** The end point. */
	private Point2D end;
	
	/** The line. */
	private Line line;
	
	/** The number of clicks. */
	private int clicks = 0;

	/**
	 * Instantiates a new line tool.
	 *
	 * @param colorProvider the color provider
	 * @param model the drawing model
	 */
	public LineTool(IColorProvider colorProvider, DrawingModel model) {
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
	 * After the first click line object is created and added to the model.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		clicks++;
		
		if(clicks % 2 != 0) {
			start = new Point2D(e.getX(), e.getY());
			end = new Point2D(e.getX(), e.getY());
			line = new Line(colorProvider, start, end);
			model.add(line);
		}
	}

	/**
	 * As the mouse moves, end point is calculated and stored in the line object.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if(clicks % 2 != 0) {
			end = new Point2D(e.getX(), e.getY());
			line.setEndPoint(end);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
	}

}
