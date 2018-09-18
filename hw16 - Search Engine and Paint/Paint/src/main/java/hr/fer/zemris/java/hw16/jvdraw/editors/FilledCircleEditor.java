package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

/**
 * The Class FilledCircleEditor is a subclass of JPanel.
 * It enables user to change information about the current filled circle object.
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The circle. */
	private FilledCircle circle;
	
	/** The center X. */
	private JTextArea centerX;
	
	/** The center Y. */
	private JTextArea centerY;
	
	/** The radius. */
	private JTextArea radius;
	
	/** The outline color. */
	private JColorArea outlineColor;
	
	/** The area color. */
	private JColorArea areaColor;
	
	/** The center. */
	private Point2D center;
	
	/** The r. */
	private double r;
	
	/** The outline. */
	private Color outline;
	
	/** The area. */
	private Color area;
	
	/**
	 * Instantiates a new filled circle editor.
	 *
	 * @param circle the circle
	 */
	public FilledCircleEditor(FilledCircle circle) {
		this.circle = circle;
		
		centerX = new JTextArea(String.valueOf(circle.getCenter().getX()));
		centerY = new JTextArea(String.valueOf(circle.getCenter().getY()));
		radius = new JTextArea(String.valueOf((int)circle.getRadius()));
		outlineColor = new JColorArea(circle.getOutlineColor());
		areaColor = new JColorArea(circle.getAreaColor());
		
		setLayout(new GridLayout(5, 2));
		
		add(new JLabel("center x"), 0, 0);
		add(centerX, 0, 1);
		add(new JLabel("center y"), 1, 0);
		add(centerY, 1, 1);
		add(new JLabel("radius"), 2, 0);
		add(radius, 2, 1);
		add(new JLabel("outline color"), 3, 0);
		add(outlineColor, 3, 1);
		add(new JLabel("area color"), 4, 0);
		add(areaColor, 4, 1);
	}

	@Override
	public void checkEditing() {
		try {
			double x = Double.parseDouble(centerX.getText());
			double y = Double.parseDouble(centerY.getText());
			center = new Point2D(x, y);
			r = Double.parseDouble(radius.getText());
			outline = outlineColor.getCurrentColor();
			area = areaColor.getCurrentColor();
			
		} catch (RuntimeException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public void acceptEditing() {
		circle.setCenter(center);
		circle.setRadius(r);
		circle.setOutlineColor(outline);
		circle.setAreaColor(area);
	}

}
