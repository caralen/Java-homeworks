package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * The listener interface for receiving drawingModel events.
 * The class that is interested in processing a drawingModel
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDrawingModelListener<code> method. When
 * the drawingModel event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DrawingModelEvent
 */
public interface DrawingModelListener {
	
	/**
	 * This method is called when objects are added.
	 *
	 * @param source the drawing model
	 * @param index0 the starting index of the objects which are changed
	 * @param index1 the ending index of the objects which are changed
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * This method is called when objects are removed.
	 *
	 * @param source the drawing model
	 * @param index0 the starting index of the objects which are changed
	 * @param index1 the ending index of the objects which are changed
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Objects changed.
	 *
	 * @param source the source
	 * @param index0 the starting index of the objects which are changed
	 * @param index1 the ending index of the objects which are changed
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}