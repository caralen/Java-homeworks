package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * The listener interface for receiving colorChange events.
 * The class that is interested in processing a colorChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addColorChangeListener<code> method. When
 * the colorChange event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ColorChangeEvent
 */
public interface ColorChangeListener {
	
	/**
	 * This method is called when a new color is selected.
	 *
	 * @param source the color provider
	 * @param oldColor the old color
	 * @param newColor the new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}