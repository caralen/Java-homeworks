package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents a command which is used for drawing a line.
 * @author Alen Carin
 *
 */
public class DrawCommand implements Command {
	
	private double step;
	
	/**
	 * Constructor. Sets the local value of step to the value passed through argument.
	 * @param step
	 */
	public DrawCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Method which evaluates where the turtle must go 
	 * and changes the values of the turtle state.
	 * Calls method drawLine with these new values.
	 */
	@Override
	public void execute(Context context, Painter painter) {
		TurtleState state = context.getCurrentState();
		Vector2D position = state.getCurrentPosition();
		Vector2D oldPosition = position.copy();
		position.translate(state.getDirection().scaled(step).scaled(state.getUnitLength()));
		
		painter.drawLine(
				oldPosition.getX(),
				oldPosition.getY(),
				position.getX(),
				position.getY(),
				state.getColor(),
				(float) state.getUnitLength()
				);
	}
}
