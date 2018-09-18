package hr.fer.zemris.java.hw16.jvdraw.editors;

import javax.swing.JPanel;

/**
 * The Class GeometricalObjectEditor is an abstract class which extends JPanel.
 * It enables user to change properties of a certaing object.
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Checks if the information entered are valid.
	 */
	public abstract void checkEditing();
	
	/**
	 * Accepts entered information as valid and changes properties of the actual object.
	 */
	public abstract void acceptEditing();
}
