package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents a command which is used to scale 
 * the size of the lines that the turtle is drawing.
 * @author Alen Carin
 *
 */
public class ScaleCommand implements Command {
	
	private double factor;
	
	/**
	 * Constructor which sets the field value of the factor
	 * to the value passed through argument.
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}

	/**
	 * Scales the unitLength of the current state by the given factor.
	 */
	@Override
	public void execute(Context context, Painter painter) {
		double unitLength = context.getCurrentState().getUnitLength();
		unitLength = unitLength * factor;
	}

}
