package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Method which is called upon the start of the program.
 * No command line arguments are used.
 * @param args not used.
 *
 */
public class Glavni2 {

	/**
	 * Method which is called upon the start of the program.
	 * No command line arguments are used.
	 * @param args not used
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}
	
	/**
	 * Creates a Koch curve through configuration methods and returns LSystem.
	 * @param provider
	 * @return LSystem
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
				"origin						0.05 0.4",
				"angle						0",
				"unitLength					0.9",
				"unitLengthDegreeScaler		1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
}
