package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * The Interface IColorProvider has methods for adding and removing observers which are interested
 * in knowing which color is currently used. It also provides method for getting the current color.
 */
public interface IColorProvider {
	
	/**
	 * Gets the current color.
	 *
	 * @return the current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds the color change listener to the list.
	 *
	 * @param l the color change listener
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the color change listener from the list.
	 *
	 * @param l the color change listener
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}