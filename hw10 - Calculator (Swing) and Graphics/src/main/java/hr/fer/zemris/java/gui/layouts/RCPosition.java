package hr.fer.zemris.java.gui.layouts;

/**
 * The Class RCPosition represents a x-y position in calculator.
 */
public class RCPosition {

	/** The row of the component in calculator. */
	private int row;
	
	/** The column of the component in calculator. */
	private int column;
	
	/**
	 * Instantiates a new RC position.
	 *
	 * @param row 
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
}
