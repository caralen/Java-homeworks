package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * The Interface SingleDocumentModel represents a model of single document, having information about file path
 * from which document was loaded (can be null for new document), document modification status
 * and reference to Swing component which is used for editing (each document has its own editor component).
 */
public interface SingleDocumentModel {
	
	/**
	 * Gets the text component of this model.
	 *
	 * @return the text component of this model
	 */
	JTextArea getTextComponent();
	
	/**
	 * Gets the file path of this model.
	 *
	 * @return the file path of this model
	 */
	Path getFilePath();
	
	/**
	 * Sets the file path for this model.
	 *
	 * @param path the new file path for this model
	 */
	void setFilePath(Path path);
	
	/**
	 * Checks if the text in document has been modified.
	 *
	 * @return true, if is modified
	 */
	boolean isModified();
	
	/**
	 * Sets the modified flag for this document model.
	 *
	 * @param modified the new modified status
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds the single document listener.
	 *
	 * @param l the listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes the single document listener.
	 *
	 * @param l the listener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
