package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectListener;

/**
 * The Class DrawingModelImpl is an implemenetation of the DrawingModel, as well as
 * GeometricalObjectListener.
 * It holds a collection of geometrical objects.
 * Each time a object is added to the collection, this model registers as a listener to that object so it
 * could get notified when a change in the object happens.
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {
	
	/** The list of objects. */
	private List<GeometricalObject> objects = new ArrayList<>();
	
	/** The list of listeners. */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		object.addGeometricalObjectListener(this);
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		object.removeGeometricalObjectListener(this);
		objects.remove(object);
		objectRemoved(object, index);
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		if(index+offset < 0 || index+offset > objects.size()-1) return;
		
		Collections.swap(objects, index, index + offset);
		geometricalObjectChanged(object);
	}

	/**
	 * It is called when a geometrical object is changed.
	 * It notifies all of the listeners from the list of listeners.
	 *
	 * @param o the geometrical object
	 */
	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		for(DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, objects.indexOf(o), objects.indexOf(o));
		}
	}
	
	/**
	 * This method is called when an object is removed.
	 * It notifies all the listeners from the list of listeners.
	 *
	 * @param o the geometrical object
	 * @param index the index at which the object was saved in the collection.
	 */
	private void objectRemoved(GeometricalObject o, int index) {
		for(DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, index, index);
		}
	}
}
