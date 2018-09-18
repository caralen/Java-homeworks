package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A window that shows buttons on screen.
 * @author Alen Carin
 *
 */
public class Window extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new window.
	 */
	public Window() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Window");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}

	/**
	 * Inits the GUI with 6 button placed in certain positions on screen.
	 */
	private void initGUI() {
		
		Container cp = getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(1, 1));
		p.add(new JButton("y"), new RCPosition(2, 3));
		p.add(new JButton("z"), new RCPosition(2, 7));
		p.add(new JButton("w"), new RCPosition(4, 2));
		p.add(new JButton("a"), new RCPosition(4, 5));
		p.add(new JButton("b"), new RCPosition(4, 7));
		
		cp.add(p);
	}

	/**
	 * The main method which is called upon the start of the program.
	 *
	 * @param args command line arguments, not used here.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Window window = new Window();
				window.setVisible(true);
			}
		});
	}
}
