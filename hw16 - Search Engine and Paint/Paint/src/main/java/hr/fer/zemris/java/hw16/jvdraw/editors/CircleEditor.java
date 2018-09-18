package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

/**
 * The Class CircleEditor is a subclass of JPanel.
 * It enables user to change information about the current circle object.
 */
public class CircleEditor extends GeometricalObjectEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The reference to the actual circle object. */
	private Circle circle;
	
	/** The center X. */
	private JTextArea centerX;
	
	/** The center Y. */
	private JTextArea centerY;
	
	/** The radius. */
	private JTextArea radius;
	
	/** The color provider. */
	private JColorArea color;
	
	/** The center. */
	private Point2D center;
	
	/** The r. */
	private double r;
	
	/** The color. */
	private Color col;

	/**
	 * Instantiates a new circle editor.
	 *
	 * @param circle the circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		
		centerX = new JTextArea(String.valueOf(circle.getCenter().getX()));
		centerY = new JTextArea(String.valueOf(circle.getCenter().getY()));
		radius = new JTextArea(String.valueOf((int)circle.getRadius()));
		color = new JColorArea(circle.getColor());
		
		setLayout(new GridLayout(4, 2));
		
		add(new JLabel("center x"), 0, 0);
		add(centerX, 0, 1);
		add(new JLabel("center y"), 1, 0);
		add(centerY, 1, 1);
		add(new JLabel("radius"), 2, 0);
		add(radius, 2, 1);
		add(new JLabel("color"), 3, 0);
		add(color, 3, 1);
	}

	@Override
	public void checkEditing() {
		try {
			double x = Double.parseDouble(centerX.getText());
			double y = Double.parseDouble(centerY.getText());
			center = new Point2D(x, y);
			r = Double.parseDouble(radius.getText());
			col = color.getCurrentColor();
			
		} catch (RuntimeException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public void acceptEditing() {
		circle.setCenter(center);
		circle.setRadius(r);
		circle.setColor(col);
	}

}
