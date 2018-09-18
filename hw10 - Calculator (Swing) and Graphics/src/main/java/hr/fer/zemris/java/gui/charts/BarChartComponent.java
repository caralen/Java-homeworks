package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

import javax.swing.JComponent;

/**
 * The Class BarChartComponent extends <code>JComponent</code> and overrides paintComponent method.
 * This class takes a bar chart with its information and paints it.
 */
public class BarChartComponent extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The minimal x distance from border of the component. */
	private static final int MIN_DIST_X = 10;
	
	/** The y distance from border of the component. */
	private static final int MIN_DIST_Y = 15;
	
	/** The chart which will be painted. */
	private BarChart chart;

	/**
	 * Instantiates a new bar chart component.
	 *
	 * @param chart {@link #chart}
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
		
		int height = getSize().height;
		int width = getSize().width;
		
//		Crtanje naslova
		Font titleFont = new Font("SansSerif", Font.BOLD, 10);
		FontMetrics titleFontMetrics = g2d.getFontMetrics(titleFont);
		
		int titleWidthx = titleFontMetrics.stringWidth(chart.getDescriptionX());
		int titleWidthy = titleFontMetrics.stringWidth(chart.getDescriptionY());
		
		g2d.drawString(chart.getDescriptionX(), (width - titleWidthx)/2, height-MIN_DIST_X);
		
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		
		g2d.drawString(chart.getDescriptionY(), -(height+titleWidthy)/2, MIN_DIST_Y);
		
		g2d.setTransform(defaultAt);
		
		List<XYValue> values = chart.getValues();
		
		if (values == null || values.size() == 0) {
			return;
		}
		
		int minValueX = 0;
		int maxValueX = 0;
		int minValueY = chart.getMinY();
		int maxValueY = chart.getMaxY();
		int graphSizeY = maxValueY-minValueY;
		int space = chart.getSpace();
		
		while(graphSizeY % space != 0) {
			space++;
		}
		
		for(XYValue value : values) {
			if(value.getX() < minValueX) {
				minValueX = value.getX();
			}
			if(value.getX() > maxValueX) {
				maxValueX = value.getX();
			}
		}
		
		int topPadding = MIN_DIST_Y;
		int sidePadding = 2*MIN_DIST_X;
		int bottomPadding = 3*MIN_DIST_Y;
		int oneStep = (height-bottomPadding-topPadding)/graphSizeY;

		List<Integer> list = new ArrayList<>();
		for(int i = minValueY; i <= graphSizeY; i += space) {
			list.add(i);
		}
//		Crtanje vodoravnih linija i opisa uz y-os
		int biggestLabel = 0;
		int labelCounter = 0;
		for(Integer number : list) {
			Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
			FontMetrics labelFontMetrics = g2d.getFontMetrics(labelFont);
			
			if(labelFontMetrics.stringWidth(String.valueOf(number)) > biggestLabel) {
				biggestLabel = labelFontMetrics.stringWidth(String.valueOf(number));
			}
			
			int labelx = sidePadding;
			int labely = (int) (height - bottomPadding - labelCounter*oneStep);
			
			g2d.drawString(String.valueOf(number), labelx, labely);
			g2d.drawLine(2*labelx, labely, width, labely);
			labelCounter += space;
		}
		sidePadding += 2*biggestLabel;
		int columnWidth = (width-sidePadding-MIN_DIST_X) / maxValueX;
		
//		Crtanje stupaca i okomitih linija i opisa uz x-os 
		for(XYValue value : values) {
			int thisWidth = columnWidth;
			int thisHeight = (int) ((value.getY() + abs(minValueY)) * oneStep);
			int x = (value.getX()-1) * columnWidth + sidePadding;
			int y = height - thisHeight - bottomPadding;
			
			
			Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
			FontMetrics labelFontMetrics = g2d.getFontMetrics(labelFont);
			
			int labelWidth = labelFontMetrics.stringWidth(String.valueOf(value.getX()));
			int labelx = (value.getX()-1) * columnWidth + (columnWidth - labelWidth) / 2 + sidePadding;
			int labely = height - 2*MIN_DIST_Y;
			g2d.setColor(Color.BLACK);
			g2d.drawString(String.valueOf(value.getX()), labelx, labely);
			g2d.drawLine(x, MIN_DIST_Y, x, height-bottomPadding);
			
			g2d.setColor(Color.decode("#ff6600"));
			g2d.fillRect(x, y, thisWidth, thisHeight);
			g2d.setColor(Color.decode("#ffffff"));
			g2d.drawRect(x, y, thisWidth, thisHeight);
		}
		createArrows(g2d, sidePadding, bottomPadding);
		
	}

	/**
	 * Creates and paints arrows for the coordinate system.
	 *
	 * @param g2d instance of graphics 2D
	 * @param sidePadding the side padding
	 * @param bottomPadding the bottom padding
	 */
	private void createArrows(Graphics2D g2d, int sidePadding, int bottomPadding) {
		int width = getWidth();
		int height = getHeight() - bottomPadding;
		
		int[] x1 = {sidePadding-5, sidePadding, sidePadding+5};
		int[] y1 = {MIN_DIST_Y, MIN_DIST_Y-10, MIN_DIST_Y};
		
		int[] x2 = {width-MIN_DIST_X, width-MIN_DIST_X, width};
		int[] y2 = {height-5, height+5, height};
		
		g2d.setColor(Color.BLACK);
		g2d.fillPolygon(x1, y1, 3);
		g2d.fillPolygon(x2, y2, 3);
	}

	
	
}
