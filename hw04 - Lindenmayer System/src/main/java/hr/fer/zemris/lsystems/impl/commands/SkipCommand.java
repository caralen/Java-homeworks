package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents a command which is used to skip space instead of drawing a line.
 * @author Alen Carin
 *
 */
public class SkipCommand implements Command {
	
	private double step;
	
	/**
	 * Constructor which sets the field value of the step
	 * to the value passed through argument.
	 * @param step
	 */
	public SkipCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Moves the current position of the turtle by one step.
	 */
	@Override
	public void execute(Context context, Painter painter) {
		TurtleState state = context.getCurrentState();
		Vector2D position = state.getCurrentPosition();
		position.translate(state.getDirection().scaled(step).scaled(state.getUnitLength()));
	}
}
