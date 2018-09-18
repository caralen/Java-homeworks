package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * The Class GeometricalObject.
 */
public abstract class GeometricalObject {
	
	/** The list of listeners. */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * This method is invoked with a certain implementation of the GeometricalObjectVisitor in arguments.
	 * The geometrical object will then call the method of the visitor with itself in arguments.
	 *
	 * @param v the geometrical object visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Creates the geometrical object editor.
	 *
	 * @return the geometrical object editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Adds the geometrical object listener.
	 *
	 * @param l the listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	
	/**
	 * Removes the geometrical object listener.
	 *
	 * @param l the listener
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Sends notifications to the list of listeners.
	 *
	 * @param o the o
	 */
	protected void fire(GeometricalObject o) {
		for (GeometricalObjectListener listener : listeners) {
			listener.geometricalObjectChanged(o);
		}
	}
}
