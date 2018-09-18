package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The Class BarChartDemo is a program that takes a path to the file from user 
 * in which information about a chart should be stored. Then creates a bar chart component and a label
 * and adds them to the layout.
 */
public class BarChartDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new bar chart demo.
	 *
	 * @param barChart the bar chart
	 * @param path the path to the text file in which should be info about the chart.
	 */
	public BarChartDemo(BarChart barChart, Path path) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart");
		setLocation(100, 100);
		setSize(500, 500);
		setMinimumSize(new Dimension(300, 300));
		initGUI(barChart, path);
	}

	/**
	 * Inits the GUI.
	 *
	 * @param barChart the bar chart
	 * @param path the path
	 */
	private void initGUI(BarChart barChart, Path path) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		BarChartComponent charComponent = new BarChartComponent(barChart);
		JLabel label = new JLabel("path: " + path.toAbsolutePath().normalize().toString());
		
		JPanel topPanel = new JPanel(new GridLayout(1, 0));
		topPanel.add(label);
		topPanel.setBackground(Color.decode("#ffd11a"));
		
		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(charComponent);
		
		cp.add(topPanel, BorderLayout.PAGE_START);
		cp.add(central, BorderLayout.CENTER);
	}
	
	/**
	 * The main method which is called upon the start of the program.
	 *
	 * @param args the command line arguments, path to the file should be passed.
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
//				./src/main/resources/examples/test1.txt
				
				BarChartDemo window = null;
				try {
					window = new BarChartDemo(openFile(args), Paths.get(args[0]));
				} catch(RuntimeException | IOException e) {
					System.out.println(e.getMessage());
					System.exit(-1);
				}
				window.setVisible(true);
			}
		});
	}
	
	/**
	 * Opens the file and creates a bar chart from the information stored in file.
	 *
	 * @param args the command line args
	 * @return the bar chart
	 * @throws IOException if cannot read from the file
	 */
	private static BarChart openFile(String[] args) throws IOException {
		if(args.length != 1) {
			throw new IllegalArgumentException("Invalid number of arguments, should be path to file.");
		}
		
		Path path = Paths.get(args[0]);
		List<String> lines = Files.readAllLines(path);
		
		return createChart(lines);
	}

	/**
	 * Creates the bar chart.
	 *
	 * @param lines the lines of the file
	 * @return the bar chart
	 */
	private static BarChart createChart(List<String> lines) {
		String[] valuesArray = lines.get(2).split(" ");
		List<XYValue> values = new ArrayList<>();
		
		for(int i = 0; i < valuesArray.length; i++) {
			String[] parts = valuesArray[i].split(",");
			values.add(new XYValue(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		}
		return new BarChart(values, 
				lines.get(0), 
				lines.get(1), 
				Integer.parseInt(lines.get(3)), 
				Integer.parseInt(lines.get(4)), 
				Integer.parseInt(lines.get(5)));
	}
}
