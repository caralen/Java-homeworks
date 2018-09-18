package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * The Interface DrawingModel is a drawing model which holds geometrical objects.
 */
public interface DrawingModel {
	
	/**
	 * Returns the size of the collection, i.e. the number of objects in the collection.
	 *
	 * @return the number of objects stored in the model
	 */
	public int getSize();

	/**
	 * Returns the object at the given index in the collection.
	 *
	 * @param index the index at which the object is stored in the collection of objects
	 * @return the object stored at given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds the given object to the collection.
	 *
	 * @param object the geometrical object
	 */
	public void add(GeometricalObject object);

	/**
	 * Adds the drawing model listener to the collection of listeners.
	 * It will be notified when a change happens in the model.
	 *
	 * @param l the listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the drawing model listener from the collection of listeners.
	 * It will no longer be notified when the change happens in the model.
	 *
	 * @param l the listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes the given object from the collection of objects.
	 *
	 * @param object the object which will be removed
	 */
	void remove(GeometricalObject object);
	
	/**
	 * Change order of objects in the collection.
	 * Given object will be moved for the given object from the current position in the collection.
	 *
	 * @param object the geometrical object
	 * @param offset the offset from the current position
	 */
	void changeOrder(GeometricalObject object, int offset);
}