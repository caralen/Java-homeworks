package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents a command for defining color value for the turtle state.
 * @author Alen Carin
 *
 */
public class ColorCommand implements Command {
	
	private Color color;
	
	/**
	 * Constructor. Sets the local value of color to the value passed through argument.
	 * @param color
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}

	/**
	 * Sets the color value of the current state of the turtle to this color value.
	 */
	@Override
	public void execute(Context context, Painter painter) {
		TurtleState state = context.getCurrentState();
		
		context.popState();
		TurtleState newState = new TurtleState(
				state.getCurrentPosition(), 
				state.getDirection(), 
				color, 
				state.getUnitLength());
		context.pushState(newState);
	}

}
