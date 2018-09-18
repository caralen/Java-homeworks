package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents a command which is used for rotating the turtle.
 * @author Alen Carin
 *
 */
public class RotateCommand implements Command {
	
	private double angle;
	
	/**
	 * Constructor which sets the field value of the angle
	 * to the value passed through argument.
	 * @param angle
	 */
	public RotateCommand(double angle) {
		super();
		this.angle = angle;
	}

	/**
	 * Rotates the direction vector from the 
	 * current turtle state for the given angle value.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D direction = ctx.getCurrentState().getDirection();
		direction.rotate(angle);
	}

}
