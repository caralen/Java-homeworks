package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The Class DefaultSingleDocumentModel is a model of a single document.
 * It has a field value <code>JTextArea</code> which is the editor for this document model.
 * When the document of the editor is updated this document model notifies all listeners that are in internal list.
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	/** The path on the disk for this document. */
	private Path path;
	
	/** The flag if the document has been modified. */
	private boolean modified;
	
	/** The editor which is a swing component that holds a text. */
	private JTextArea editor;
	
	/** The list of listeners which are notified when a change happens in the document. */
	private List<SingleDocumentListener> listeners;
	

	/**
	 * Instantiates a new default single document model.
	 *
	 * @param path the path to the document
	 * @param text the text of the document
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		this.path = path;
		modified = false;
		
		if(text == null) {
			text = "";
		}
		editor = new JTextArea();
		editor.setText(text);
		addListener();
		
		listeners = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getFilePath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFilePath(Path path) {
		this.path = path;
		
		for(SingleDocumentListener listener : listeners) {
			listener.documentFilePathUpdated(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(boolean modified) {
		if(this.modified != modified) {
			this.modified = modified;
			
			for(SingleDocumentListener listener : listeners) {
				listener.documentModifyStatusUpdated(this);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Adds a new <code>DocumentListener</code> to the editor's document.
	 */
	private void addListener() {
		editor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}
}
