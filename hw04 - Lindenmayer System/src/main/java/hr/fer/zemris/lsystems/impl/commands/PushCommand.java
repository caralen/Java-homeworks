package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents a command which is used for pushing a state to the stack
 * @author Alen Carin
 *
 */
public class PushCommand implements Command {

	/**
	 * Pushes a state to the stack.
	 */
	@Override
	public void execute(Context context, Painter painter) {
		context.pushState(context.getCurrentState().copy());
	}

}
