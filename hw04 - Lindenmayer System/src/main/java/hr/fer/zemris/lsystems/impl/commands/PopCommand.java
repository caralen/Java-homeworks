package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents a command which is used for popping a state from the stack
 * @author Alen Carin
 *
 */
public class PopCommand implements Command {

	/**
	 * Pops a state from the stack.
	 */
	@Override
	public void execute(Context context, Painter painter) {
		context.popState();
	}
}
