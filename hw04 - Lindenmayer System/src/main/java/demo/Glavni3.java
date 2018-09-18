package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Method which is called upon the start of the program.
 * No command line arguments are used.
 * @param args not used.
 *
 */
public class Glavni3 {

	/**
	 * Method which is called upon the start of the program.
	 * No command line arguments are used.
	 * @param args not used
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
