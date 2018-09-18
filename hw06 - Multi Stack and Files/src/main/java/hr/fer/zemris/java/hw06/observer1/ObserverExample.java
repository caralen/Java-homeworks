package hr.fer.zemris.java.hw06.observer1;

/**
 * Class used for testing the Observer Pattern.
 * @author Alen Carin
 *
 */
public class ObserverExample {

	/**
	 * Method which is called upon the start of the program.
	 * @param args command line arguments which are not used here.
	 */
	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(5));
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
