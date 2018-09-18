package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * The Interface MultipleDocumentModel represents a model capable of holding zero, one or more documents,
 * where each document is having a concept of current document – the one which is shown to the
 * user and on which user works.
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Creates the new document model.
	 *
	 * @return the single document model
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Gets the current document.
	 *
	 * @return the current document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads a document into new tab from the given path on the disk.
	 *
	 * @param path the path where the document is stored
	 * @return the single document model
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves the current document to the given path on the disk.
	 *
	 * @param model the current model
	 * @param newPath the new path where the document will be saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes current document and asks the user if it should be saved if it already isn't.
	 *
	 * @param model the model will should be closed
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds the multiple document listener to the internal collection of listeners.
	 *
	 * @param l the listener which will be added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes the multiple document listener from the internal collection of listeners.
	 *
	 * @param l the listener which should be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Gets the number of documents that are open in tabs.
	 *
	 * @return the number of documents opened in tabs
	 */
	int getNumberOfDocuments();
	
	/**
	 * Gets the document at the given index.
	 *
	 * @param index the index of the tab for which the document would be returned
	 * @return the document
	 */
	SingleDocumentModel getDocument(int index);
}