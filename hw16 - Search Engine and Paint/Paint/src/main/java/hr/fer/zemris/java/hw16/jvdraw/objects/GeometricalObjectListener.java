package hr.fer.zemris.java.hw16.jvdraw.objects;

/**
 * The listener interface for receiving geometricalObject events.
 * The class that is interested in processing a geometricalObject
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addGeometricalObjectListener<code> method. When
 * the geometricalObject event occurs, that object's appropriate
 * method is invoked.
 *
 * @see GeometricalObjectEvent
 */
public interface GeometricalObjectListener {
	
	/**
	 * This method is invoked when a geometrical object is changed.
	 *
	 * @param o the geometrical object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}