package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.util.Objects;

import javax.swing.JLabel;

/**
 * The Class ColorLabel is a subclass of JLabel.
 * It shows the currently selected colors.
 * It implements ColorChangeListener so that it will be notified when the color changes.
 */
public class ColorLabel extends JLabel implements ColorChangeListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The foreground color provider. */
	IColorProvider fgColorProvider;
	
	/** The background color provider. */
	IColorProvider bgColorProvider;
	
	/**
	 * Instantiates a new color label.
	 *
	 * @param fgColorProvider the foreground color provider
	 * @param bgColorProvider the background color provider
	 */
	public ColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		Objects.requireNonNull(fgColorProvider);
		Objects.requireNonNull(bgColorProvider);
		
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		Color fgColor = fgColorProvider.getCurrentColor();
		Color bgColor = bgColorProvider.getCurrentColor();
		
		setText("Foreground color: " + "(" + fgColor.getRed() + ", " + fgColor.getGreen() + ", " + fgColor.getBlue() + ")" + 
				", background color: (" + bgColor.getRed() + ", " + bgColor.getGreen() + ", " + bgColor.getBlue() + ").");
	}

	
}
