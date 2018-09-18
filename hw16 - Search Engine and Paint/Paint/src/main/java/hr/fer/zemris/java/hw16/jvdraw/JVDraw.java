package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.color.ColorLabel;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.utils.JVDFilesUtil;
import hr.fer.zemris.java.hw16.jvdraw.utils.SimpleKeyListener;
import hr.fer.zemris.java.hw16.jvdraw.utils.SimpleMouseListener;

/**
 * The Class JVDraw is the main class of the program. It extends JFrame.
 * It is a simple version of paint program where you can draw three kinds of objects:
 * <li>A line
 * <li>A circle
 * <li>A filled circle
 * <br><br>
 * 
 * The picture can be exported in jpg, png or gif format or it can be saved in jvd format.
 * Jvd format can later be reopened and reused in the program.
 */
public class JVDraw extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The current state i.e. the tool that is currently in use. */
	private Tool currentState;
	
	/** The reference to an implementation of the DrawingModel. */
	private DrawingModel model;
	
	/** Reference to the drawing canvas. */
	private JDrawingCanvas canvas;
	
	/**
	 * Instantiates a new JVDraw.
	 * Sets constraints and details for the window and then calls the initGUI method which fills the user interface.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("JVDRAW");
		setLocation(20, 20);
		setSize(800, 450);
		initGUI();
	}

	/**
	 * Instantiates the user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		model = new DrawingModelImpl();
		
		createCanvas(cp);
		model.addDrawingModelListener(canvas);
		
		createToolbarAndLabel(cp, canvas);
		createList(cp);
		
		createMenuBar();
	}

	/**
	 * Creates a menu bar that contains a sinlge menu - File.
	 * File contains 6 menu items: new, open, save, save as, export and exit.
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		createMenuItem(fileMenu, "New", e -> JVDFilesUtil.clearModel(model));
		createMenuItem(fileMenu, "Open", e -> JVDFilesUtil.openFile(this, model, canvas));
		createMenuItem(fileMenu, "Save", e -> JVDFilesUtil.saveFile(this, model, canvas));
		createMenuItem(fileMenu, "Save As", e -> JVDFilesUtil.saveNewFile(this, model, canvas));
		createMenuItem(fileMenu, "Export", e -> JVDFilesUtil.exportFile(this, model, canvas));
		createMenuItem(fileMenu, "Exit", e -> saveAndExit());
		
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}
	
	/**
	 * Asks the user to save the document before exiting and then if the operation is not cancelled 
	 * the window is closed and the application terminated.
	 */
	private void saveAndExit() {
		if(canvas.isChanged()) {
			int answer = JOptionPane.showOptionDialog(
					this, 
					"Would you like to save before exiting", 
					"Save?", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					null, null, null);
			
			if(answer == 0) {
				JVDFilesUtil.saveFile(this, model, canvas);
			} else if(answer == 2) {
				return;
			}
		}
		dispose();
	}

	/**
	 * Creates a menu item, attaches the given action listener to it and the item is added to the given file menu.
	 * @param fileMenu is the menu in the menu bar.
	 * @param name is the name for the menu item.
	 * @param action is the action that will be performed when the item is clicked.
	 */
	private void createMenuItem(JMenu fileMenu, String name, ActionListener action) {
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(action);
		fileMenu.add(item);
	}

	/**
	 * Creates a list model which is used as an adapter for the DrawingModel.
	 * Creates a JList and sets its properties.
	 * Creates a scroll pane for the list.
	 * @param cp is the container that holds all the elements.
	 */
	private void createList(Container cp) {
		ListModel<GeometricalObject> listModel = new DrawingObjectListModel(model);
		
		JList<GeometricalObject> list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		
		addListListeners(list);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension((int)(this.getWidth()*0.3), this.getHeight()));
		
		cp.add(scrollPane, BorderLayout.EAST);
	}

	/**
	 * Adds the key listener and the mouse listener to the list so that it can notify the {@link #model} 
	 * when a certain important action happens.
	 *
	 * @param list the list
	 */
	private void addListListeners(JList<GeometricalObject> list) {
		
		list.addKeyListener(new SimpleKeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				GeometricalObject object = list.getSelectedValue();
				if(object == null) return;
				
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					model.remove(object);
				} else if (e.getKeyCode() == KeyEvent.VK_PLUS) {
					model.changeOrder(object, 1);
				} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					model.changeOrder(object, -1);
				}
			}
		});
		
		
		list.addMouseListener(new SimpleMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					GeometricalObject object = list.getSelectedValue();
					GeometricalObjectEditor editor = object.createGeometricalObjectEditor();
					
					if(JOptionPane.showConfirmDialog(
							list,
							editor,
							"Edit object", 
							JOptionPane.YES_NO_OPTION) == 0) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch(Exception ex) {
							
						}
					}
				}
			}
		});
	}

	/**
	 * Creates the toolbar containing tools and the label which shows which colors are currently in use.
	 *
	 * @param cp the container which holds all the elements.
	 * @param canvas the canvas that holds the whole picture.
	 */
	private void createToolbarAndLabel(Container cp, JDrawingCanvas canvas) {
		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		toolbar.add(panel, BorderLayout.WEST);
		JColorArea fgColor = new JColorArea(Color.RED);
		JColorArea bgColor = new JColorArea(Color.BLUE);
		panel.add(fgColor);
		panel.add(bgColor);
		
		ButtonGroup group = new ButtonGroup();
		
		createToggleButton("Line", group, panel, e -> {
			currentState = new LineTool(fgColor, model);
			canvas.setCurrentTool(currentState);
		});
		createToggleButton("Circle", group, panel, e -> {
			currentState = new CircleTool(fgColor, model);
			canvas.setCurrentTool(currentState);
		});
		createToggleButton("Filled circle", group, panel, e -> {
			currentState = new FilledCircleTool(fgColor, bgColor, model);
			canvas.setCurrentTool(currentState);
		});
		
		//line tool is set as current state at the beginning
		currentState = new LineTool(fgColor, model);
		canvas.setCurrentTool(currentState);
		
		ColorLabel label = new ColorLabel(fgColor, bgColor);
		
		cp.add(toolbar, BorderLayout.NORTH);
		cp.add(label, BorderLayout.SOUTH);
	}
	
	/**
	 * Creates a toggle button which is a part of the button group.
	 * @param name is the name of the button.
	 * @param group is the button group to which this button is added.
	 * @param panel is the panel to which the button is added.
	 * @param actionListener is an action listener which is triggered when the button is clicked.
	 */
	private void createToggleButton(String name, ButtonGroup group, JPanel panel, ActionListener actionListener) {
		JToggleButton lineButton = new JToggleButton(name);
		lineButton.addActionListener(actionListener);
		group.add(lineButton);
		panel.add(lineButton);
	}

	/**
	 * Creates the canvas.
	 *
	 * @param cp the container which holds all the elements.
	 * @return the canvas which holds the whole painting.
	 */
	private void createCanvas(Container cp) {
		canvas = new JDrawingCanvas(model);
		cp.add(canvas, BorderLayout.CENTER);
	}

	/**
	 * The main method is the method which is called upon the start of the program.
	 *
	 * @param args the are the command line arguments which are not used in this program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JVDraw jvDraw = new JVDraw();
				jvDraw.setVisible(true);
			}
		});
	}
}
