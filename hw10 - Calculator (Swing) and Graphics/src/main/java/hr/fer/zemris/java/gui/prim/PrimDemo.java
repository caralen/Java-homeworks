package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo program for showing generating and showing prime numbers in two lists.
 * @author Alen Carin
 *
 */
public class PrimDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor, instantiates a new prim demo program.
	 */
	public PrimDemo() {
		setLocation(500, 200);
		setSize(300, 200);
		setMinimumSize(new Dimension(300, 200));
		setTitle("Prim program");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}

	/**
	 * Initiates the graphic user interface.
	 * Creates a container containing two panels,
	 * first one containing two lists and the second one containing a button.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> listLeft = new JList<>(model);
		JList<Integer> listRight = new JList<>(model);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));
		
		JButton next = new JButton("New prim");
		bottomPanel.add(next);
		
		next.addActionListener(e -> {
			model.next();
		});
		
		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(listLeft));
		central.add(new JScrollPane(listRight));
		
		cp.add(central, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	/**
	 * The main method that is called upon the start of the program.
	 *
	 * @param args command line arguments, not used here.
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
