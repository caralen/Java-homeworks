package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Represents a single command that a turtle must be able to execute.
 * @author Alen Carin
 *
 */
public interface Command {

	/**
	 * A method that is a implementation of the command execution.
	 * @param context which keeps a stack of states.
	 * @param painter used for drawing.
	 */
	void execute(Context context, Painter painter);
}
