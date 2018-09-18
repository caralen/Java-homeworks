package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.file.Path;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

/**
 * The Class JDrawingCanvas is a subclass of JComponent and is used for creating the whole picture with objects on it.
 * It implements DrawingModelListener so that it can get notified when there is a change in the model and then it
 * repaints the whole picture.
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The reference to the implementation of DrawingModel. */
	private DrawingModel model;
	
	/** The tool that is currently in use. */
	private Tool currentTool;
	
	/** The path on the disk of the current document. */
	private Path documentPath;
	
	/** Flag which shows if the document has been changed since the last time it was saved. */
	private boolean changed;
	

	/**
	 * Instantiates a new JDrawingCanvas.
	 * Mouse listeners are added here in the constructor so that 
	 * the current tool could be notified when the mouse is being used.
	 *
	 * @param model the model
	 */
	public JDrawingCanvas(DrawingModel model) {
		this.model = model;
		addListeners();
	}

	/**
	 * Paints the background in white and calls accept method on each object in the model 
	 * with an instance of GeometricalObjectPainter in arguments.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		int size = model.getSize();
		for(int i = 0; i < size; i++) {
			GeometricalObject object = model.getObject(i);
			object.accept(painter);
		}
		currentTool.paint(g2d);
		changed = true;
	}
	
	/**
	 * Returns path to the current document.
	 * @return path to the current document.
	 */
	public Path getPath() {
		return documentPath;
	}

	/**
	 * Sets a new path for the current document.
	 * @param path that will be set for the current document.
	 */
	public void setPath(Path path) {
		documentPath = path;
		changed = false;
	}
	
	/**
	 * Returns true if the document has been changed since the last save.
	 * @return true if there has been a change in the document after the last save.
	 */
	public boolean isChanged() {
		return changed;
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		model = source;
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		model = source;
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		model = source;
		repaint();
	}

	/**
	 * Sets the tool passed in the arguments to be the current tool.
	 *
	 * @param currentTool the tool that is currently in use.
	 */
	public void setCurrentTool(Tool currentTool) {
		this.currentTool = currentTool;
	}
	
	/**
	 * Adds mouse listeners to this component so that it can notify the current tool each time a mouse action happens.
	 */
	private void addListeners() {
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				currentTool.mouseMoved(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				currentTool.mouseDragged(e);
			}
		});
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				currentTool.mouseReleased(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				currentTool.mousePressed(e);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				currentTool.mouseClicked(e);
			}
		});
	}
	
}
