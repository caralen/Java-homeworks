package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJOptionPane;


/**
 * The Class DefaultMultipleDocumentModel is an implementation of the <code>MultipleDocumentModel</code>.
 * It extends <code>JTabbedPane</code> and holds a tab for each document in the list.
 * It represents a model capable of holding zero, one or more document where each document is having
 * a concept of current document - the one which is shown to the user and on which user works.
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The list of documents that are present in this tabbed pane. */
	private List<SingleDocumentModel> documentsList;
	
	/** The document that is currently open and visible to the user. */
	private SingleDocumentModel currentDocument;
	
	/** The list of listeners which are notified when change happens. */
	private List<MultipleDocumentListener> listeners;
	
	/** The document listener which is a <code>SingleDocumentListener</code>. */
	private SingleDocumentListener documentListener;
	
	/** Image icon used to indicate that the file is modified but not saved. */
	private ImageIcon redIcon;
	
	/** Image icon used to indicate that the file is not modified. */
	private ImageIcon greenIcon;
	
	/** Form localization provider is used for getting some string at current language. */
	private FormLocalizationProvider flp;
	
	/**
	 * Instantiates a new default multiple document model, adds change listener to this tabbed pane.
	 */
	public DefaultMultipleDocumentModel(FormLocalizationProvider flp) {
		documentsList = new ArrayList<>();
		listeners = new ArrayList<>();
		
		this.flp = flp;
		
		addListener();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel document = new DefaultSingleDocumentModel(null, null);
		currentDocument = document;
		documentsList.add(document);
		this.addTab("new " + documentsList.size(), new JScrollPane(currentDocument.getTextComponent()));
		
		if(documentsList.size() != 1) {
			setSelectedIndex(getSelectedIndex()+1);
		} else {
			setSelectedIndex(0);
		}
		notifyDocumentAdded(currentDocument);
		notifyCurrentDocumentChanged(null);
		
		setToolTipTextAt(getSelectedIndex(), "Not yet saved.");
		return document;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		
		byte[] okteti = null;
		try {
			okteti = Files.readAllBytes(path);
		} catch(Exception e1) {
			String[] message = {"errorReadingMessage", String.valueOf(path.getFileName())};
			LJOptionPane.showMessageDialog(
					getParent(), 
					message, 
					"error", 
					JOptionPane.ERROR_MESSAGE,
					flp);
			return null;
		}
		if(documentExists(path)) {
			setSelectedIndex(findDocumentWithPath(path));
			return null;
		}
		String text = new String(okteti, StandardCharsets.UTF_8);
		SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
		documentsList.add(model);
		
		SingleDocumentModel previousModel = currentDocument;
		currentDocument = model;

		this.addTab(path.getFileName().toString(), currentDocument.getTextComponent());
		
		notifyDocumentAdded(currentDocument);
		notifyCurrentDocumentChanged(previousModel);
		
		
		if(documentsList.size() != 1) {
			setSelectedIndex(getNumberOfDocuments()-1);
		} else {
			setSelectedIndex(0);
		}
		
		setToolTipTextAt(getSelectedIndex(), path.toAbsolutePath().toString());
		
		return model;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
		byte[] podaci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, podaci);
		} catch(IOException e1) {
			LJOptionPane.showMessageDialog(
					getParent(), 
					"errorWritingMessage", 
					"error", 
					JOptionPane.ERROR_MESSAGE,
					flp);
			return;
		}
		LJOptionPane.showMessageDialog(
				getParent(), 
				"savedMessage", 
				"infoMessage", 
				JOptionPane.INFORMATION_MESSAGE,
				flp);
		
		model.setModified(false);
		model.setFilePath(newPath);
		notifyCurrentDocumentChanged(currentDocument);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		Objects.requireNonNull(model, "Can't close when there is nothing to close");
		if(model.isModified()) {
			Object[] options = {"Yes", "No"};
			int n = LJOptionPane.showOptionDialog(getParent(),
				    "saveBeforeClosing",
				    "save",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[0],
				    flp);
			
			if(n == 0) {
				Path newPath = chooseWhereToSave();
				if(newPath == null) return;
				saveDocument(model, newPath);
			}
		}
		
		SingleDocumentModel previousDocument = currentDocument;
		if(documentsList.size() != 0) {
			documentsList.remove(model);
			remove(getSelectedComponent());
			setSelectedIndex(documentsList.size()-1);
		} else {
			currentDocument = null;
		}
		
		notifyDocumentRemoved(previousDocument);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return documentsList.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		return documentsList.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new DocumentIterator();
	}
	
	/**
	 * The Class DocumentIterator which represents an iterator through <code>SingleDocumentModels</code>.
	 */
	private class DocumentIterator implements Iterator<SingleDocumentModel> {
		
		/** The number of remaining documents in the list of documents. */
		private int remainingDocuments;
		
		/**
		 * Instantiates a new document iterator.
		 */
		public DocumentIterator() {
			remainingDocuments = documentsList.size();
		}

		@Override
		public boolean hasNext() {
			return remainingDocuments > 0;
		}

		@Override
		public SingleDocumentModel next() {
			return getDocument(--remainingDocuments);
		}
	}
	
	/**
	 * Adds a new <code>ChangeListener</code> to this tabbed pane. Each time a state is changed
	 * the currentDocument field is updated, listeners on the previous current document are removed
	 * and listeners to the new current document are added.
	 * All the listeners from the list {@link #listeners} are notified about the change.
	 */
	private void addListener() {
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(documentListener != null) {
					currentDocument.removeSingleDocumentListener(documentListener);
				}
				SingleDocumentModel previousModel = currentDocument;
				if(documentsList.size() != 0) {
					currentDocument = documentsList.get(DefaultMultipleDocumentModel.this.getSelectedIndex());
				} else {
					currentDocument = null;
				}
				notifyCurrentDocumentChanged(previousModel);
				
				addSingleDocumentListener();
				if(currentDocument != null) {
					currentDocument.addSingleDocumentListener(documentListener);
				}
			}
		});
	}


	/**
	 * Notifies listeners that the current document changed.
	 *
	 * @param previousModel is the previous current model
	 */
	private void notifyCurrentDocumentChanged(SingleDocumentModel previousModel) {
		for(MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(previousModel, currentDocument);
		}
		setIcon(currentDocument);
	}
	
	/**
	 * Notifies listeners that a document is added.
	 *
	 * @param model the model
	 */
	private void notifyDocumentAdded(SingleDocumentModel model) {
		for(MultipleDocumentListener listener : listeners) {
			listener.documentAdded(currentDocument);
		}
	}
	
	/**
	 * Notifies listeners that a document is removed.
	 *
	 * @param model the model
	 */
	private void notifyDocumentRemoved(SingleDocumentModel model) {
		for(MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(currentDocument);
		}
	}
	
	/**
	 * Sets the appropriate icon to the selected tab. 
	 * If the model has been modified then the icon is red, otherwise the icon is green.
	 *
	 * @param model for which the icon should be set
	 */
	private void setIcon(SingleDocumentModel model) {
		if(model == null) return;
		
		if(redIcon == null || greenIcon == null) {
			initializeIcons();
		}
		
		if(model.isModified()) {
			setIconAt(getSelectedIndex(), redIcon);
		} else {
			setIconAt(getSelectedIndex(), greenIcon);
		}
	}
	
	/**
	 * Initialises field values {@link #greenIcon} and {@link #redIcon}.
	 */
	private void initializeIcons() {
		redIcon = getIcon("icons/redDisk.png");
		greenIcon = getIcon("icons/greenDisk.png");
	}

	/**
	 * Opens an input stream which gets resources as a stream. 
	 * File at the given path in resources is read and a new <code>ImageIcon</code> 
	 * is created from the given stream bytes.
	 * @param path to the png file which should be loaded
	 * @return
	 */
	private ImageIcon getIcon(String path) {
		InputStream is = this.getClass().getResourceAsStream(path);
		if(is==null) {
			System.out.println("Neuspjelo citanje");
			System.exit(1);
		}
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			System.out.println("Ne mogu otvoriti izlazni tok");
			System.exit(1);
		}
		return new ImageIcon(bytes);
	}

	/**
	 * Creates a new <code>SingleDocumentListener</code> and sets it as the field value {@link #documentListener}.
	 * When the document is current document is modified set icon is called.
	 * When the file path is updated for the current document title and tool tip text are changed.
	 */
	private void addSingleDocumentListener() {
		documentListener = new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIcon(currentDocument);
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toAbsolutePath().toString());
			}
		};
	}
	
	/**
	 * Pops a window for user to choose a path where he wants to save the document.
	 *
	 * @return the path which the user chose
	 */
	private Path chooseWhereToSave() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if(jfc.showOpenDialog(DefaultMultipleDocumentModel.this) != JFileChooser.APPROVE_OPTION) {
			LJOptionPane.showMessageDialog(
					DefaultMultipleDocumentModel.this, 
					"notsavedmessage", 
					"error", 
					JOptionPane.INFORMATION_MESSAGE,
					flp);
			return null;
		}
		Path path = jfc.getSelectedFile().toPath();
		if(Files.exists(path)) {
			LJOptionPane.showMessageDialog(
					DefaultMultipleDocumentModel.this, 
					"fileExistsMessage", 
					"error", 
					JOptionPane.INFORMATION_MESSAGE,
					flp);
			return null;
		}
		return path;
	}
	
	/**
	 * Finds document in {@link #documentsList} with the given path.
	 *
	 * @param path the path to the document
	 * @return the position of the tab where the document with the given path is located
	 */
	private int findDocumentWithPath(Path path) {
		int size = documentsList.size();
		for(int i = 0; i < size; i++) {
			if(documentsList.get(i).getFilePath() == path) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Returns true if a document with the same path exists in {@link #documentsList}, false otherwise.
	 *
	 * @param path the path to the document
	 * @return true, if document with the same path exists in some tab
	 */
	private boolean documentExists(Path path) {
		for (SingleDocumentModel model : documentsList) {
			if (model == null || model.getFilePath() == null) {
				continue;
			}
			if (model.getFilePath().equals(path)) {
				return true;
			}
		}
		return false;
	}
}
