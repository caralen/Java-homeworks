package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.LayoutManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * The Class JStatusBar represents a status bar at the south end of the notepad window.
 */
public class JStatusBar extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The label showing the length of the document. */
	private JLabel length;
	
	/** The label showing the line in which the caret is currently positioned. */
	private JLabel lines;
	
	/** The label showing the column in which the caret is currently positioned. */
	private JLabel columns;
	
	/** The label showing the number of characters in current selection in text. */
	private JLabel selection;
	
	/** The clock component. */
	private Clock clock;
	
	/** The label for showing the clock component. */
	static JLabel clockLabel;

	/**
	 * Instantiates a new status bar.
	 *
	 * @param layout the layout for the status bar
	 */
	public JStatusBar(LayoutManager layout) {
		super(layout);
		addPanels();
	}

	/**
	 * Initialises clock.
	 */
	private void initializeClock() {
		clock = new Clock();
	}

	/**
	 * Adds panels to this status bar.
	 */
	private void addPanels() {
		JPanel panelCenter = new JPanel();
		
		initialiseLabels();
		initializeClock();
		
		length.setHorizontalAlignment(SwingConstants.LEFT);
		
		panelCenter.add(lines);
		panelCenter.add(columns);
		panelCenter.add(selection);
		
		clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.add(length);
		this.add(panelCenter);
		this.add(clockLabel);
		
	}

	/**
	 * Initialises label values.
	 */
	private void initialiseLabels() {
		length = new JLabel("Length: ");
		lines = new JLabel("Ln: ");
		columns = new JLabel("Col: ");
		selection = new JLabel("Sel: ");
		clockLabel = new JLabel();
	}

	/**
	 * Gets the {@link #length}.
	 *
	 * @return the length
	 */
	public JLabel getLength() {
		return length;
	}

	/**
	 * Sets the {@link #length} value.
	 *
	 * @param length the new length text
	 */
	public void setLengthText(String length) {
		this.length.setText("Length: " + length);
	}

	/**
	 * Gets the {@link #lines}.
	 *
	 * @return the lines
	 */
	public JLabel getLines() {
		return lines;
	}

	/**
	 * Sets the {@link #lines} value.
	 *
	 * @param lines the new lines text
	 */
	public void setLinesText(String lines) {
		this.lines.setText("Ln: " + lines);
	}

	/**
	 * Gets the {@link #columns}.
	 *
	 * @return the columns
	 */
	public JLabel getColumns() {
		return columns;
	}

	/**
	 * Sets the {@link #columns} values.
	 *
	 * @param columns the new columns text
	 */
	public void setColumnsText(String columns) {
		this.columns.setText("Col: " + columns);
	}

	/**
	 * Gets the {@link #selection} values.
	 *
	 * @return the selection
	 */
	public JLabel getSelection() {
		return selection;
	}

	/**
	 * Sets the {@link #selection} values.
	 *
	 * @param selection the new selection text
	 */
	public void setSelectionText(String selection) {
		this.selection.setText("Sel: " + selection);
	}
	
	/**
	 * Reset label values.
	 */
	public void resetLabels() {
		setLengthText("0");
		setLinesText("0");
		setColumnsText("0");
		setSelectionText("0");
	}
	
	/**
	 * Stops clock.
	 */
	public void stopClock() {
		clock.stop();
	}
	
	/**
	 * The Class Clock it tells the current date and time.
	 */
	static class Clock extends JComponent {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The time. */
		volatile String time;
		
		/** The stop requested. */
		volatile boolean stopRequested;
		
		/** The formatter. */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
		
		/**
		 * Instantiates a new clock.
		 */
		public Clock() {
			updateTime();
			
			Thread t = new Thread(()->{
				while(true) {
					try {
						Thread.sleep(500);
					} catch(Exception ex) {}
					if(stopRequested) break;
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		/**
		 * Stops the clock.
		 */
		private void stop() {
			stopRequested = true;
		}

		/**
		 * Updates time and sets clock label to the new value of the clock component.
		 */
		private void updateTime() {
			time = formatter.format(LocalDateTime.now());
			clockLabel.setText(time);
		}
	}
}
