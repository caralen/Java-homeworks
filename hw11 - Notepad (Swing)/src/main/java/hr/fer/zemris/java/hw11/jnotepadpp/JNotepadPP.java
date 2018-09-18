package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.UnaryOperator;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJOptionPane;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LocalizableAction;


/**
 * The Class JNotepadPP is a notepad application, similar to Notepad++.
 * This class extends <code>JFrame</code> class and represents a window.
 * 
 * <p>It supports following functionalities to the user:
 * <li> creating a new blank document
 * <li> opening existing document
 * <li> saving document
 * <li> saving-as document (warn user if file already exists)
 * <li> close document shown in a tab (and remove that tab)
 * <li> cut/copy/paste text
 * <li> statistical info
 * <li> exiting application
 */
public class JNotepadPP extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The model capable of holdnig zero, one or more documents. */
	private MultipleDocumentModel multidocModel;
	
	/** The clipboard for saving strings that are copied or cut. */
	private String clipboard;
	
	/** Caret listener listens for any caret movement and does some action when the movement happens. */
	private CaretListener caretListener;
	
	/** Status bar at the bottom of the notepad. */
	private JStatusBar statusBar;
	
	/** Menu for converting selected text to upper or lower case. */
	private JMenu changeCaseMenu;
	
	/** Menu for sorting the selected text. */
	private JMenu sortMenu;
	
	/** Form localization provider is used for getting some string at current language. */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Instantiates a new JNotepadPP.
	 *
	 * @throws HeadlessException the headless exception
	 */
	public JNotepadPP() throws HeadlessException {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		setLocation(20, 20);
		setSize(700, 500);
		initGui();
	}


	/**
	 * Initiates graphical user interface.
	 */
	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		multidocModel = new DefaultMultipleDocumentModel(flp);

		setWindowListener();
		
		createMenus();
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);
		
		Set<JButton> disabledButtons = new HashSet<>();
		
		newButton(toolBar, newDocumentAction, true);
		newButton(toolBar, openDocumentAction, true);
		disabledButtons.add(newButton(toolBar, saveDocumentAction, false));
		disabledButtons.add(newButton(toolBar, saveAsDocumentAction, false));
		disabledButtons.add(newButton(toolBar, closeDocumentAction, false));
		newButton(toolBar, exitAction, true);
		disabledButtons.add(newButton(toolBar, cutSelectedPartAction, false));
		disabledButtons.add(newButton(toolBar, copySelectedPartAction, false));
		disabledButtons.add(newButton(toolBar, pasteSelectedPartAction, false));
		disabledButtons.add(newButton(toolBar, showStatisticsAction, false));
		
		cp.add(toolBar, BorderLayout.PAGE_START);
		addMultidocListener(disabledButtons);
		
		statusBar = new JStatusBar(new GridLayout(1, 3));
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(statusBar, BorderLayout.SOUTH);
		panel.add((Component) multidocModel, BorderLayout.CENTER);
		cp.add(panel, BorderLayout.CENTER);
	}
	

	/**
	 * Creates a new button, sets its enabled flag and adds it to the toolbar.
	 *
	 * @param toolBar the notepad toolbar
	 * @param action the action that needs to be performed when the button is clicked
	 * @param enabled the enabled flag of the button
	 * @return the new button which has set action, set enabled flag and is added to toolbar
	 */
	private JButton newButton(JToolBar toolBar, Action action, boolean enabled) {
		JButton button = new JButton(action);
		button.setEnabled(enabled);
		toolBar.add(button);
		return button;
	}


	/**
	 * Adds the new <code>MultipleDocumentListener</code> listener which sets button flags and application tittle.
	 *
	 * @param buttons the buttons that should be disabled when the last document is removed
	 * and enabled when the first document is added
	 */
	private void addMultidocListener(Set<JButton> buttons) {
		multidocModel.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if(multidocModel.getNumberOfDocuments() == 0) {
					for(JButton button : buttons) {
						button.setEnabled(false);
					}
					statusBar.resetLabels();
				}
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				if(multidocModel.getNumberOfDocuments() == 1) {
					for(JButton button : buttons) {
						button.setEnabled(true);
					}
				}
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				changeCaseMenu.setEnabled(false);
				sortMenu.setEnabled(false);
				if(currentModel != null) {
					JTextArea editor = currentModel.getTextComponent();
					
					if (caretListener != null && previousModel != null) {
						editor.removeCaretListener(caretListener);
					}
					
					setTitle(currentModel);
					
					editor.addCaretListener(getCaretListener(editor));
					statusBar.resetLabels();
					statusBar.setLengthText(String.valueOf(editor.getDocument().getLength()));
				} else {
					JNotepadPP.this.setTitle("JNotepad++");
				}
			}

			/**
			 * Sets title for of the window to have the path of the current document.
			 * @param currentModel
			 */
			private void setTitle(SingleDocumentModel currentModel) {
				Path path = currentModel.getFilePath() == null ? null : currentModel.getFilePath().toAbsolutePath();
				if(path == null) {
					JNotepadPP.this.setTitle("(not saved) JNotepad++");
				} else {
					JNotepadPP.this.setTitle(path.toString() + " - JNotepad++");
				}
			}
		});
	}
	
	/**
	 * Returns a new caret listener which listens for the movement of the caret and does action according to the movement.
	 * When the movement happens status bar changes its values and some menus can be disabled or enabled.
	 * @param editor
	 * @return
	 */
	private CaretListener getCaretListener(JTextArea editor) {
		return new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				int caretPosition = editor.getCaretPosition();
				int lineNum = 0;
				int columnNum = 0;
				int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());

				try {
					lineNum = editor.getLineOfOffset(caretPosition);
					columnNum = caretPosition - editor.getLineStartOffset(lineNum);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				statusBar.setLengthText(String.valueOf(editor.getDocument().getLength()));
				statusBar.setLinesText(String.valueOf(lineNum+1));
				statusBar.setColumnsText(String.valueOf(columnNum+1));
				statusBar.setSelectionText(String.valueOf(len));
				
				if (len == 0) {
					changeCaseMenu.setEnabled(false);
					sortMenu.setEnabled(false);
				} else {
					changeCaseMenu.setEnabled(true);
					sortMenu.setEnabled(true);
				}
			}
		};
	}

	/** The action for creating a new document. */
	private Action newDocumentAction = new LocalizableAction("new","control N", KeyEvent.VK_N, "newdesc", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			multidocModel.createNewDocument();
		}
	};
	
	/** The action for opening a new document from the disk. */
	private Action openDocumentAction = new LocalizableAction("open", "control O", KeyEvent.VK_O, "opendesc", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			
			if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			if(!Files.isReadable(filePath)) {
				LJOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"notExistsMessage", 
						"error", 
						JOptionPane.ERROR_MESSAGE,
						flp);
				return;
			}
			multidocModel.loadDocument(filePath);
		}
	};
	

	/** The action for saving a document to the disk, 
	 * if the document is already on the disk it is saved to the same location. */
	private Action saveDocumentAction = new LocalizableAction("save", "control S", KeyEvent.VK_S, "savedesc", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(multidocModel.getCurrentDocument() == null) {
				return;
			}
			Path path = multidocModel.getCurrentDocument().getFilePath();
			
			if(path == null) {
				path = chooseFilePath();
				if(path == null) {
					return;
				}
			}
			multidocModel.saveDocument(multidocModel.getCurrentDocument(), path);
		}
	};
	
	/** The action for saving a document to a new location on disk. */
	private Action saveAsDocumentAction = new LocalizableAction("save-as", "control alt S", KeyEvent.VK_A, "saveasdesc", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(multidocModel.getCurrentDocument() == null) {
				return;
			}
			Path path = chooseFilePath();
			if(path == null) {
				return;
			}
			multidocModel.saveDocument(multidocModel.getCurrentDocument(), path);
		}
	};
	
	/** The action for closing the current document. */
	private Action closeDocumentAction = new LocalizableAction("close", "control W", KeyEvent.VK_C, "closedesc", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				multidocModel.closeDocument(multidocModel.getCurrentDocument());
			} catch(NullPointerException e1) {
				return;
			}
		}
	};
	
	/** The action used for exiting the application, for all the files that are modified, 
	 * but not yet saved user is asked to save first. */
	private Action exitAction = new LocalizableAction("exit", "alt F4", KeyEvent.VK_E, "exitdesc", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveAndClose();
		}
	};

	
	/** The action used for cutting the selected string, string is saved to clipboard. */
	private Action cutSelectedPartAction = new LocalizableAction("cut", "control X", KeyEvent.VK_C, "cutdesc", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(multidocModel.getCurrentDocument() == null) return;
			
			JTextArea editor = multidocModel.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
			
			if(len == 0) return;
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				clipboard = doc.getText(offset, len);
				doc.remove(offset, len);
			} catch(BadLocationException e1) {
				e1.printStackTrace();
			}
			ActionMap amap = editor.getActionMap();
			amap.put(DefaultEditorKit.cutAction, cutSelectedPartAction);
		}
	};
	
	/** The action used for copying the selected string, string is saved to clipboard. */
	private Action copySelectedPartAction = new LocalizableAction("copy", "control C", KeyEvent.VK_O, "copydesc", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(multidocModel.getCurrentDocument() == null) return;
			
			JTextArea editor = multidocModel.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
			
			if(len == 0) return;
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				clipboard = doc.getText(offset, len);
			} catch(BadLocationException e1) {
				e1.printStackTrace();
			}
			ActionMap amap = editor.getActionMap();
			amap.put(DefaultEditorKit.copyAction, copySelectedPartAction);
		}
	};
	
	/** The action used for pasting the string saved in clipboard. */
	private Action pasteSelectedPartAction = new LocalizableAction("paste", "control P", KeyEvent.VK_P, "pastedesc", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(multidocModel.getCurrentDocument() == null) return;
			
			JTextArea editor = multidocModel.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			
			int offset = editor.getCaret().getDot();
			try {
				doc.insertString(offset, clipboard, null);
			} catch(BadLocationException e1) {
				e1.printStackTrace();
			}
			ActionMap amap = editor.getActionMap();
			amap.put(DefaultEditorKit.pasteAction, pasteSelectedPartAction);
		}
	};
	
	/** The action used for showing statistics of the current document. */
	private Action showStatisticsAction = new LocalizableAction("stats", "control T", KeyEvent.VK_S, "statsdesc", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(multidocModel.getCurrentDocument() == null) return;
			
			JTextArea editor = multidocModel.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			int numberOfChars = editor.getCaret().getDot();
			int nonSpaces = 0;
			try {
				String text = doc.getText(0, numberOfChars);
				nonSpaces = text.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "").length();
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			
			String[] text = new String[7];
			text[0] = "documentHas";
			text[1] = " " + numberOfChars + " ";
			text[2] = "chars";
			text[3] = ", " + nonSpaces + " ";
			text[4] = "charsNonBlank";
			text[5] = ", " + editor.getLineCount() + " ";
			text[6] = "lines";
			
			LJOptionPane.showMessageDialog(
					getParent(), 
					text, 
					"stats", 
					JOptionPane.INFORMATION_MESSAGE,
					flp);
		}
	};
	
	/**
	 * Action that sets current notepad language to English.
	 */
	private Action enLanguageAction = new LocalizableAction("en", "control E", KeyEvent.VK_E, "endesc", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("en");
		}
	};
	
	/**
	 * Action that sets current notepad language to Croatian.
	 */
	private Action hrLanguageAction = new LocalizableAction("hr", "control H", KeyEvent.VK_C, "hrdesc", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("hr");
		}
	};
	
	/**
	 * Action that sets current notepad language to German.
	 */
	private Action deLanguageAction = new LocalizableAction("de", "control G", KeyEvent.VK_G, "dedesc", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("de");
		}
	};
	
	/**
	 * Sets current language of the notepad to the given language.
	 * @param language string shortcut for a specific language that will become the current language of notepad.
	 */
	private void setLanguage(String language) {
		LocalizationProvider.getInstance().setLanguage(language);
	}
	
	/**
	 * Action that converts current selection of text to upper case.
	 */
	private Action toUpperCase = new LocalizableAction("upperCase", "control U", KeyEvent.VK_U, "upperCaseDesc", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeChar(c -> Character.toUpperCase(c));
		}
	};
	
	/**
	 * Action that converts current selection of text to lower case.
	 */
	private Action toLowerCase = new LocalizableAction("lowerCase", "control L", KeyEvent.VK_L, "lowerCaseDesc", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeChar(c -> Character.toLowerCase(c));
		}
	};
	
	/**
	 * Action that inverts case for the current selection of text.
	 */
	private Action invertCase = new LocalizableAction("invertCase", "control I", KeyEvent.VK_I, "invertCaseDesc", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeChar(c -> Character.isLowerCase(c) ? Character.toUpperCase(c) : Character.toLowerCase(c));
		}
	};
	
	/**
	 * Goes through currently selected text and applies given operation on each character.
	 * @param operator which will be applied to each character.
	 */
	private void changeChar(UnaryOperator<Character> operator) {
		JTextArea editor = multidocModel.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		try {
			String text = doc.getText(offset, len);
			
			char[] znakovi = text.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				znakovi[i] = operator.apply(znakovi[i]);
			}
			text = new String(znakovi);
			
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch(BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Sorts selected lines of text in ascending order.
	 */
	private Action sortAscending = new LocalizableAction("ascending", "control A", KeyEvent.VK_A, "ascendingDescription", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Locale locale = new Locale(flp.getString("currentLanguage"));
			Collator collator = Collator.getInstance(locale);
			
			sortText((s1, s2) -> collator.compare(s1, s2));
		}
	};
	
	/**
	 * Sorts selected lines of text in descending order.
	 */
	private Action sortDescending = new LocalizableAction("descending", "control D", KeyEvent.VK_D, "descendingDescription", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Locale locale = new Locale(flp.getString("currentLanguage"));
			Collator collator = Collator.getInstance(locale);
			
			sortText((s1, s2) -> collator.compare(s2, s1));
		}
	};
	
	/**
	 * Sorts currently selected lines of text with the given comparator.
	 * @param comparator for comparing and sorting lines of characters
	 */
	private void sortText(Comparator<String> comparator) {
		if(multidocModel.getCurrentDocument() == null) return;
		JTextArea editor = multidocModel.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		try {
			int markLine = editor.getLineOfOffset(editor.getCaret().getMark());
			int dotLine = editor.getLineOfOffset(editor.getCaret().getDot());
			if(markLine == dotLine) return;
			
			int startingLine = markLine > dotLine ? dotLine : markLine;
			int endingLine = markLine > dotLine ? markLine : dotLine;
			
			List<String> lines = new ArrayList<>();
			for(int i = startingLine; i <= endingLine; i++) {
				
				int len = editor.getLineEndOffset(i) - editor.getLineStartOffset(i);
				int startOffset = editor.getLineStartOffset(i);
				
				lines.add(doc.getText(startOffset, len));
			}
			List<String> copyLines = new ArrayList<>(lines);
			lines.sort(comparator);
			
			for(int i = 0; i < lines.size(); i++) {
				if((i != lines.size()-1) && lines.get(i).equals(copyLines.get(copyLines.size()-1))) {
					lines.set(i, lines.get(i) + "\n");
				}
				if((i == lines.size()-1) && lines.get(i).contains("\n") && doc.getLength() == editor.getLineEndOffset(endingLine)) {
					lines.set(i, lines.get(i).substring(0, lines.get(i).length()-1));
				}
			}
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line);
			}
			
			int startOffset = editor.getLineStartOffset(startingLine);
			int len = editor.getLineEndOffset(endingLine) - startOffset;
			doc.remove(startOffset, len);
			doc.insertString(startOffset, sb.toString(), null);
			
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Deletes lines from selected text that have multiple occurrences.
	 * Only the first occurrence remains in the text.
	 */
	private Action sortUnique = new LocalizableAction("unique", "control Q", KeyEvent.VK_Q, "uniqueDescription", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(multidocModel.getCurrentDocument() == null) return;
			JTextArea editor = multidocModel.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			try {
				int markLine = editor.getLineOfOffset(editor.getCaret().getMark());
				int dotLine = editor.getLineOfOffset(editor.getCaret().getDot());
				if(markLine == dotLine) return;
				
				int startingLine = markLine > dotLine ? dotLine : markLine;
				int endingLine = markLine > dotLine ? markLine : dotLine;
				
				List<String> lines = new ArrayList<>();
				for(int i = startingLine; i <= endingLine; i++) {
					
					int len = editor.getLineEndOffset(i) - editor.getLineStartOffset(i);
					int startOffset = editor.getLineStartOffset(i);
					
					lines.add(doc.getText(startOffset, len));
				}
				lines.set(lines.size()-1, lines.get(lines.size()-1) + "\n");
				List<String> uniqueLines = new ArrayList<>();
				
				for(String line : lines) {
					if(uniqueLines.contains(line)) continue;
					uniqueLines.add(line);
				}
				uniqueLines.set(uniqueLines.size()-1, uniqueLines.get(uniqueLines.size()-1).replaceAll("\n", ""));
				
				StringBuilder sb = new StringBuilder();
				for (String line : uniqueLines) {
					sb.append(line);
				}
				int startOffset = editor.getLineStartOffset(startingLine);
				int len = editor.getLineEndOffset(endingLine) - startOffset;
				doc.remove(startOffset, len);
				doc.insertString(startOffset, sb.toString(), null);
				
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};

	/**
	 * Creates the menu and adds menu items to it.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		addFileMenu(menuBar);
		addEditMenu(menuBar);
		addInfoMenu(menuBar);
		addLanguageMenu(menuBar);
		addToolsMenu(menuBar);
		
		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates file menu and adds it to the menu bar.
	 * @param menuBar menu bar of the notepad
	 */
	private void addFileMenu(JMenuBar menuBar) {
		JMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
	}

	/**
	 * Creates edit menu and adds it to the menu bar.
	 * @param menuBar menu bar of the notepad
	 */
	private void addEditMenu(JMenuBar menuBar) {
		JMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(cutSelectedPartAction));
		editMenu.add(new JMenuItem(copySelectedPartAction));
		editMenu.add(new JMenuItem(pasteSelectedPartAction));
	}

	/**
	 * Creates info menu and adds it to the menu bar.
	 * @param menuBar menu bar of the notepad
	 */
	private void addInfoMenu(JMenuBar menuBar) {
		JMenu infoMenu = new LJMenu("info", flp);
		menuBar.add(infoMenu);
		infoMenu.add(new JMenuItem(showStatisticsAction));
	}

	/**
	 * Creates language menu and adds it to the menu bar.
	 * @param menuBar menu bar of the notepad
	 */
	private void addLanguageMenu(JMenuBar menuBar) {
		JMenu languageMenu = new LJMenu("language", flp);
		menuBar.add(languageMenu);
		
		languageMenu.add(new JMenuItem(hrLanguageAction));
		languageMenu.add(new JMenuItem(enLanguageAction));
		languageMenu.add(new JMenuItem(deLanguageAction));
	}

	/**
	 * Creates tools menu and adds it to the menu bar.
	 * @param menuBar menu bar of the notepad
	 */
	private void addToolsMenu(JMenuBar menuBar) {
		JMenu toolsMenu = new LJMenu("tools", flp);
		menuBar.add(toolsMenu);
		
		changeCaseMenu = new LJMenu("changeCase", flp);
		toolsMenu.add(changeCaseMenu);
		changeCaseMenu.add(new JMenuItem(toUpperCase));
		changeCaseMenu.add(new JMenuItem(toLowerCase));
		changeCaseMenu.add(new JMenuItem(invertCase));
		changeCaseMenu.setEnabled(false);
		
		
		sortMenu = new LJMenu("sort", flp);
		toolsMenu.add(sortMenu);
		
		sortMenu.add(new JMenuItem(sortAscending));
		sortMenu.add(new JMenuItem(sortDescending));
		sortMenu.add(new JMenuItem(sortUnique));
		sortMenu.setEnabled(false);
	}


	/**
	 * Creates a new window adapter which saves files and then closes the window 
	 * and adds that adapter to window as a window listener.
	 */
	private void setWindowListener() {
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				saveAndClose();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				statusBar.stopClock();
			}
		});
	}
	
	/**
	 * Saves all documents and then calls the method dispose to close the window.
	 */
	private void saveAndClose() {
		for(SingleDocumentModel model : multidocModel) {
			multidocModel.closeDocument(model);
		}
		dispose();
	}
	
	/**
	 * Pops a window for choosing a document path, 
	 * status if the operation went with success or not is printed to the user.
	 *
	 * @return the path which the user chose.
	 */
	protected Path chooseFilePath() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if(jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			LJOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"notsavedmessage", 
					"error", 
					JOptionPane.INFORMATION_MESSAGE,
					flp);
			return null;
		}
		Path path = jfc.getSelectedFile().toPath();
		
		if(Files.exists(path)) {
			LJOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"fileExistsMessage", 
					"error", 
					JOptionPane.INFORMATION_MESSAGE,
					flp);
			return null;
		}
		return path;
	}

	/**
	 * The main method of the program, starts a new thread and 
	 * creates new instance of <code>JNotepadPP</code> and sets it as visible.
	 *
	 * @param args the command line arguments, not used here.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JNotepadPP notepad = new JNotepadPP();
				notepad.setVisible(true);
			}
		});
	}
}
