package hr.fer.zemris.java.hw16.jvdraw.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This abstract class is a facade for the KeyListener interface.
 * It is used when classes only have to use a keyReleased method from the KeyListener interface 
 * and do not want the implement other methods.
 * All other methods are implemented to do absolutely nothing.
 */
public abstract class SimpleKeyListener implements KeyListener {

	/**
	 * This method is invoked when a key is released.
	 */
	@Override
	public abstract void keyReleased(KeyEvent e);
	
	/**
	 * Does nothing.
	 * Override if you would like to use it.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	/**
	 * Does nothing.
	 * Override if you would like to use it.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// do nothing
	}
}
