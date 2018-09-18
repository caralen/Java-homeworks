package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * A layout implementation designed for calculator.
 * @author Alen Carin
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	/** The Constant ZERO_PADDING. */
	private static final int ZERO_PADDING = 0;
	
	/** The Constant FIRST_POSITION. */
	private static final RCPosition FIRST_POSITION = new RCPosition(1, 1);
	
	/** The Constant FIRST_POSITION_WIDTH. */
	private static final int FIRST_POSITION_WIDTH = 5;
	
	/** The Constant ROWS_MIN. */
	private static final int ROWS_MIN = 1;
	
	/** The Constant ROWS_MAX. */
	private static final int ROWS_MAX = 5;
	
	/** The Constant COLUMNS_MIN. */
	private static final int COLUMNS_MIN = 1;
	
	/** The Constant COLUMNS_MAX. */
	private static final int COLUMNS_MAX = 7;
	
	/** The min width. */
	private int minWidth = 0;
		
	/** The min height. */
	private int minHeight = 0;
    
    /** The preferred width. */
    private int preferredWidth = 0;
    
    /** The preferred height. */
    private int preferredHeight = 0;
	
	/** The map of components. */
	private Map<Component, RCPosition> components;
	
	/** The padding between components. */
	private int padding;
	
	/**
	 * Default constructor, instantiates a new calculator layout.
	 */
	public CalcLayout() {
		this(ZERO_PADDING);
	}

	/**
	 * Instantiates a new calculator layout.
	 *
	 * @param padding between components
	 */
	public CalcLayout(int padding) {
		this.padding = padding;
		components = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

        setSizes(parent);

        Insets insets = parent.getInsets();
        dim.width = COLUMNS_MAX * preferredWidth + (COLUMNS_MAX-1) * padding + insets.left + insets.right;
        dim.height = ROWS_MAX * preferredHeight + (ROWS_MAX-1) * padding + insets.top + insets.bottom;

        return dim;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		
		setSizes(parent);

        Insets insets = parent.getInsets();
        dim.width = COLUMNS_MAX * minWidth + (COLUMNS_MAX-1) * padding + insets.left + insets.right;
        dim.height = ROWS_MAX * minHeight + (ROWS_MAX-1) * padding + insets.top + insets.bottom;
        
        return dim;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
        int x = 0, y = insets.top;
        
        setSizes(parent);
        
        preferredWidth = (parent.getWidth()-(COLUMNS_MAX-1)*padding) / COLUMNS_MAX;
        preferredHeight = (parent.getHeight()-(ROWS_MAX-1)*padding) / ROWS_MAX;
        
        for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
        	Component c = entry.getKey();

			RCPosition position = entry.getValue();

			x = (preferredWidth + padding) * (position.getColumn() - 1);
			y = (preferredHeight + padding) * (position.getRow() - 1);
			
			// Set the component's size and position.
			if (position.getRow() == 1 && position.getColumn() == 1) {
				c.setBounds(x, y, FIRST_POSITION_WIDTH*preferredWidth + (FIRST_POSITION_WIDTH-1)*padding, preferredHeight);
			} else {
				c.setBounds(x, y, preferredWidth, preferredHeight);
			}
        }
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		
		RCPosition position;
		
		if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else if (constraints instanceof String) {
			String[] parts = ((String) constraints).split(",");
			position = new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			
		} else {
			throw new CalcLayoutException("Invalid constraints");
		}
		checkPosition(position);
		components.put(comp, position);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return null;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}
	
	/**
	 * Sets the minimal and preferred sizes.
	 * Iterates through the map of components and sets the global preferred size to be the biggest
	 * preferred size of all components and does the same for the minimum sizes.
	 *
	 * @param parent container
	 */
	private void setSizes(Container parent) {

        //Reset preferred/minimum width and height.
        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;
        
        for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
        	Dimension preferredSize = entry.getKey().getPreferredSize();
        	Dimension minSize = entry.getKey().getMinimumSize();
        	
			if (preferredSize.width > preferredWidth) {
				if (entry.getValue().equals(FIRST_POSITION)) {
					preferredWidth = (preferredSize.width - (FIRST_POSITION_WIDTH - 1) * padding)
							/ FIRST_POSITION_WIDTH;
				} else {
					preferredWidth = preferredSize.width;
				}
			}
			if (preferredSize.height > preferredHeight) {
				preferredHeight = preferredSize.height;
			}
			if (minSize.width > minWidth) {
				if (entry.getValue().equals(FIRST_POSITION)) {
					minWidth = (minSize.width - (FIRST_POSITION_WIDTH - 1) * padding) 
							/ FIRST_POSITION_WIDTH;
				} else {
					minWidth = minSize.width;
				}
			}
			if (minSize.height > minHeight) {
				minHeight = minSize.height;
			}
        	
        }
	}
	

	/**
	 * Check if the position of the given constraints are valid and 
	 * if already exists a component with the same position.
	 *
	 * @param constraints for the new component
	 */
	private void checkPosition(RCPosition constraints) {
		checkPositionValid(constraints);
		checkPositionExists(constraints);
	}

	/**
	 * Check if the given position is valid.
	 *
	 * @param constraints for the new component
	 */
	private void checkPositionValid(RCPosition constraints) {
		if (constraints.getRow() < ROWS_MIN || constraints.getRow() > ROWS_MAX) {
			throw new CalcLayoutException("X must be between 1 and 5");
		} else if (constraints.getColumn() < COLUMNS_MIN || constraints.getColumn() > COLUMNS_MAX) {
			throw new CalcLayoutException("Y must be between 1 and 7");
		} else if (constraints.getRow() == COLUMNS_MIN) {
			if (constraints.getColumn() > 1 && constraints.getColumn() < 6) {
				throw new CalcLayoutException("If x is 1 than y mustn't be between 1 and 6");
			}
		}
	}
	
	/**
	 * Check if a component with the same position already exists.
	 *
	 * @param constraints for the new component
	 */
	private void checkPositionExists(RCPosition constraints) {
		for(Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			if(entry.getValue().equals(constraints)) {
				throw new CalcLayoutException("Component is already defined for this position");
			}
		}
	}
}
