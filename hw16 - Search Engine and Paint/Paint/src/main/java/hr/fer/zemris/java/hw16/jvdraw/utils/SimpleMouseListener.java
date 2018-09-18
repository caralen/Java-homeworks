package hr.fer.zemris.java.hw16.jvdraw.utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This abstract class is a facade for the MouseListener interface.
 * It is used when classes only have to use a mouseClicked method from the MouseListener interface 
 * and do not want the implement other methods.
 * All other methods are implemented to do absolutely nothing.
 */
public abstract class SimpleMouseListener implements MouseListener {

	/**
	 * This method is invoked when a mouse is clicked.
	 */
	@Override
	public abstract void mouseClicked(MouseEvent e);

	/**
	 * Does nothing.
	 * Override if you would like to use it.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}

	/**
	 * Does nothing.
	 * Override if you would like to use it.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}

	/**
	 * Does nothing.
	 * Override if you would like to use it.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}

	/**
	 * Does nothing.
	 * Override if you would like to use it.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing
	}

}
