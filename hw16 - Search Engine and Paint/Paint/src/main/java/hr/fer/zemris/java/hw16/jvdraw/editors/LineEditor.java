package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.utils.Point2D;

/**
 * The Class LineEditor is a subclass of JPanel.
 * It enables user to change information about the current line object.
 */
public class LineEditor extends GeometricalObjectEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The line. */
	private Line line;
	
	/** The start X. */
	private JTextArea startX;
	
	/** The start Y. */
	private JTextArea startY;
	
	/** The end X. */
	private JTextArea endX;
	
	/** The end Y. */
	private JTextArea endY;
	
	/** The color area. */
	private JColorArea colorArea;
	
	/** The start point. */
	private Point2D startPoint;
	
	/** The end point. */
	private Point2D endPoint;
	
	/** The color. */
	private Color color;
	
	/**
	 * Instantiates a new line editor.
	 *
	 * @param line the line
	 */
	public LineEditor(Line line) {
		this.line = line;
		
		startX = new JTextArea(String.valueOf(line.getStartPoint().getX()));
		startY = new JTextArea(String.valueOf(line.getStartPoint().getY()));
		endX = new JTextArea(String.valueOf(line.getEndPoint().getX()));
		endY = new JTextArea(String.valueOf(line.getEndPoint().getY()));
		colorArea = new JColorArea(line.getColor());
		
		setLayout(new GridLayout(5, 2));
		
		add(new JLabel("start x"), 0, 0);
		add(startX, 0, 1);
		add(new JLabel("start y"), 1, 0);
		add(startY, 1, 1);
		add(new JLabel("end x"), 2, 0);
		add(endX, 2, 1);
		add(new JLabel("end y"), 3, 0);
		add(endY, 3, 1);
		add(new JLabel("color"), 4, 0);
		add(colorArea, 4, 1);
	}

	@Override
	public void checkEditing() {
		try {
			startPoint = new Point2D(Double.parseDouble(startX.getText()), Double.parseDouble(startY.getText()));
			endPoint = new Point2D(Double.parseDouble(endX.getText()), Double.parseDouble(endY.getText()));
			color = colorArea.getCurrentColor();
		} catch(RuntimeException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public void acceptEditing() {
		line.setStartPoint(startPoint);
		line.setEndPoint(endPoint);
		line.setColor(color);
	}

}
