package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;

/**
 * Collection which calculates and holds a certain amount of prime numbers.
 * @author Alen Carin
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**Starting value for the counter*/
	private static int COUNTER_START = 0;
	
	/**First prime number in the collection.*/
	private static int START_PRIME = 2;
	
	/**Size of the collection, i.e. a number of prime numbers that this collection stores.*/
	private int size;
	
	/**An array of prime numbers.*/
	private Integer[] primes;

	/**
	 * Constructor which takes through arguments the number 
	 * of how many primes the collection should store.
	 * @param size {@link #size}
	 */
	public PrimesCollection(int size) {
		this.size = size;
		primes = new Integer[size];
		calculatePrimes();
	}

	/**
	 * Calculates first n number of primes,
	 * n is equal to collection size.
	 */
	private void calculatePrimes() {
		int counter = COUNTER_START;
		int prime = START_PRIME;
		
		while(counter != size) {
			if(isPrime(prime)) {
				primes[counter++] = prime;
			}
			prime++;
		}
	}
	
	/**
	 * Checks if the passed number is a prime number.
	 * @param number that has to be checked if it is a prime number.
	 * @return true if the passed number is a prime number.
	 */
	private boolean isPrime(int number) {
		if(number == 2) return true;
		if(number == 3) return true;
		
	    for(int i = 2; i < number; ++i) {
	        if (number % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}

	/**
	 * Factory of iterators. Returns a new instance of iterator implementation.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Implementation of the integer iterator.
	 * @author Alen Carin
	 *
	 */
	private class IteratorImpl implements Iterator<Integer> {
		
		/**Remaining number of integers in the collection that haven't been returned yet.*/
		private int remainingSize;
		
		/**
		 * Default constructor.
		 */
		public IteratorImpl() {
			remainingSize = size;
		}

		/**
		 * Returns true if there is at least one more integer to be retrieved, false otherwise.
		 */
		@Override
		public boolean hasNext() {
			return remainingSize > 0;
		}

		/**
		 * Returns the next integer in the collection.
		 */
		@Override
		public Integer next() {
			int index = size - remainingSize;
			remainingSize--;
			return primes[index];
		}
	}
	
}
