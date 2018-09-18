package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.utils.SimpleMouseListener;

/**
 * The Class JColorArea is a subclass of JComponent.
 * It holds a color that is currently used for drawing.
 * Color can be changed by clicking on this component and selecting another one.
 * Classes which are interested in knowing which color is currently active can add themselves to the list of observers.
 */
public class JColorArea extends JComponent implements IColorProvider {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The currently selected color. */
	private Color selectedColor;
	
	/** The old color. */
	private Color oldColor;
	
	/** The list of listeners. */
	private List<ColorChangeListener> listeners = new ArrayList<>();
	
	/**
	 * Instantiates a new JColorArea.
	 *
	 * @param selectedColor the selected color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		repaint();
		addClickListener(this);
	}
	
	/**
	 * Paints this component in currently selected color and fires notification to the observers.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(selectedColor);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		fire();
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	/**
	 * Sets the selected color to the one passed in arguments.
	 * Calls repaint on this component.
	 *
	 * @param selectedColor the new selected color
	 */
	public void setSelectedColor(Color selectedColor) {
		repaint();
		oldColor = this.selectedColor;
		this.selectedColor = selectedColor;
	}
	
	/**
	 * Fires notifications to the list of listeners.
	 */
	private void fire() {
		for(ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, oldColor, selectedColor);
		}
	}
	
	/**
	 * Adds the click listener to this area and opens a color choosing dialog when it is clicked.
	 *
	 * @param colorArea the color area
	 */
	private void addClickListener(JColorArea colorArea) {
		colorArea.addMouseListener(new SimpleMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(colorArea, "Choose color", selectedColor);
				colorArea.setSelectedColor(color);
			}
		});
	}
	
}
