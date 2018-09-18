package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * The Interface Tool is the State in the State design pattern.
 * Each implementation of this interface is used to create a certain geometrical object.
 */
public interface Tool {
	
	/**
	 * This method is invoked when the mouse is pressed.
	 *
	 * @param e the e
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * This method is invoked when the mouse is released.
	 *
	 * @param e the e
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * This method is invoked when the mouse is clicked.
	 *
	 * @param e the e
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * This method is invoked when the mouse is moved.
	 *
	 * @param e the e
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * This method is invoked when the mouse is dragged.
	 *
	 * @param e the e
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paint method is used when the tool has to add something to the object drawing.
	 *
	 * @param g2d the g 2 d
	 */
	public void paint(Graphics2D g2d);
}