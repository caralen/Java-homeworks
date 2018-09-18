package hr.fer.zemris.java.hw06.demo2;

/**
 * Demo class for testing functionalities of the PrimesCollection.
 * @author Alen Carin
 *
 */
public class PrimesDemo2 {

	/**
	 * Main method which is called upon the start of the program.
	 * @param args command line arguments, not used here.
	 */
	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection) {
			for(Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
