package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.java.math.Complex;
import hr.fer.zemris.java.math.ComplexPolynomial;
import hr.fer.zemris.java.math.ComplexRootedPolynomial;

/**
 * This is a program for calculating fractals derived from Newton-Raphson iteration.
 * @author Alen Carin
 *
 */
public class Newton {
	
	/** Limit for the module. */
	private static double moduleLimit = 1E-3;
	
	/** Number of iterations. */
	private static int m = 16*16*16;
	
	/**
	 * Method which is called upon the start of the program.
	 * @param args command line arguments, not used here
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Scanner sc = new Scanner(System.in);
		int counter = 1;
		List<Complex> roots = new ArrayList<>();
		
		while(true) {
			System.out.printf("%s %d> ", "Root", counter);
			String line = sc.nextLine();
			if(line.equals("done")) {
				if(counter < 2) {
					System.out.println("Invalid number of roots, should be at least 2");
					System.exit(-1);
				}
				break;
			}
			
			try {
				roots.add(Complex.parse(line));
			} catch(RuntimeException e) {
				System.out.println(e.getMessage());
				counter--;
			}
			counter++;
		}
		int i = 0;
		Complex[] rootsArray = new Complex[roots.size()];
		for(Complex root : roots) {
			rootsArray[i++] = root;
		}
		sc.close();
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(rootsArray);
		ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
		
		FractalViewer.show(new JobProducer(rootedPolynomial, polynomial));
	}
	
	
	/**
	 * The Class MojProducer which represents a producer of jobs.
	 */
	public static class JobProducer implements IFractalProducer{
		
		/** Thread pool with daemon threads. */
		private ExecutorService pool;
		
		/** Complex rooted polynomial. */
		private ComplexRootedPolynomial rootedPolynomial;
		
		/** Complex polynomial. */
		private ComplexPolynomial polynomial;
		
		/**
		 * Default constructor. Instantiates a thread pool with deamon threads.
		 * Sets fields of complex rooted polynomial and rooted polynomial to given values.
		 */
		public JobProducer(ComplexRootedPolynomial rootedPolynomial, ComplexPolynomial polynomial) {
			
			this.rootedPolynomial = rootedPolynomial;
			this.polynomial = polynomial;
			
			
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new ThreadFactory() {
				
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setDaemon(true);
					return t;
				}
			});
		}


		/**
		 * Creates a thread pool, creates jobs and delegates them to different threads.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer) {
			
			System.out.println("Calculation started...");
			short[] data = new short[width * height];
			final int brojTraka = 8 * Runtime.getRuntime().availableProcessors();
			int brojYPoTraci = height / brojTraka;
			
			List<Future<Void>> rezultati = new ArrayList<>();
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				Job posao = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data
						, rootedPolynomial, polynomial, moduleLimit);
				rezultati.add(pool.submit(posao));
			}
			for(Future<Void> posao : rezultati) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace(System.out);
				}
			}
			
			System.out.println("Calculating is done. Let's notify observer i.e. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
		
	}
	
}
