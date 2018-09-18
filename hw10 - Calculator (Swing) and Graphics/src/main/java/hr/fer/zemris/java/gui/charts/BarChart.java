package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * The Class BarChart represents a bar chart with all of its information.
 */
public class BarChart {

	/** The values of each bar. */
	private List<XYValue> values;
	
	/** The description X axis. */
	private String descriptionX;
	
	/** The description Y axis. */
	private String descriptionY;
	
	/** The min Y. */
	private int minY;
	
	/** The max Y. */
	private int maxY;
	
	/** The space between values at y axis. */
	private int space;
	
	/**
	 * Instantiates a new bar chart.
	 *
	 * @param values {@link #values}
	 * @param descriptionX {@link #descriptionX}
	 * @param descriptionY {@link #descriptionY}
	 * @param minY the min Y {@link #minY}
	 * @param maxY the max Y {@link #maxY}
	 * @param space the space {@link #space}
	 */
	public BarChart(List<XYValue> values, String descriptionX, String descriptionY, int minY, int maxY, int space) {
		this.values = values;
		this.descriptionX = descriptionX;
		this.descriptionY = descriptionY;
		this.minY = minY;
		this.maxY = maxY;
		this.space = space;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Gets the description X.
	 *
	 * @return the description X
	 */
	public String getDescriptionX() {
		return descriptionX;
	}

	/**
	 * Gets the description Y.
	 *
	 * @return the description Y
	 */
	public String getDescriptionY() {
		return descriptionY;
	}

	/**
	 * Gets the min Y.
	 *
	 * @return the min Y
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Gets the max Y.
	 *
	 * @return the max Y
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Gets the space.
	 *
	 * @return the space
	 */
	public int getSpace() {
		return space;
	}
	
}
