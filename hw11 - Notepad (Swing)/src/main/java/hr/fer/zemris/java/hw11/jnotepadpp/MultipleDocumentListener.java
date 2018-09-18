package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * The listener interface for receiving multipleDocument events.
 * The class that is interested in processing a multipleDocument
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMultipleDocumentListener<code> method. When
 * the multipleDocument event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MultipleDocumentEvent
 */
public interface MultipleDocumentListener {
	
	/**
	 * Defines action when the current document changed.
	 *
	 * @param previousModel the previous model
	 * @param currentModel the current model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Defines action when a document is added.
	 *
	 * @param model the document model which is added
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Defines action when a document is removed.
	 *
	 * @param model the model
	 */
	void documentRemoved(SingleDocumentModel model);
}