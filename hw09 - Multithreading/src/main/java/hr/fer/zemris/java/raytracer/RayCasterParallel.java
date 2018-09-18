package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import static hr.fer.zemris.java.raytracer.Util.tracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * The Class RayCasterParallel is a parallel version of <code>RayCaster</code>.
 * Considering that for every pixel color is calculated independently this enchanced version
 * of ray caster creates a pool of threads for faster calculation of colors.
 * 
 * @author Alen Carin
 */
public class RayCasterParallel {

	/**
	 * Method which is called upon the start of the program.
	 * Sets initial values for eye, view and view up vectors and also horizontal and vertical values.
	 * @param args command line arguments, not used here
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
				new Point3D(10, 0, 0), 
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20, 
				20);
	}

	/**
	 * Returns an implementation of the <class>IRayTracerProducer</class>.
	 * @return ray tracer producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		
		return new IRayTracerProducer() {

			/**
			 * Calculates colors for each of the pixels that should be displayed on the screen,
			 * and saves them in rgb array which will later be used to draw on the screen.
			 */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D difference = view.sub(eye);
				double scaler = difference.norm();
				Point3D og = difference.scalarMultiply(1.0 / scaler);

				Point3D viewupNormalized = viewUp.normalize();

				Point3D jVector = viewupNormalized.sub(og.scalarMultiply(og.scalarProduct(viewupNormalized))).normalize();
				Point3D iVector = og.vectorProduct(jVector).normalize();

				Point3D screenCorner = view.sub(iVector.scalarMultiply(horizontal / 2.0))
						.add(jVector.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();
				
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculationJob(0, height, screenCorner, iVector, jVector, 
						horizontal, vertical, width, height, scene, eye, red, green, blue));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	
	/**
	 * The Class <code>CalculationJob</code> which extends <code>RecursiveAction</code>.
	 * It calculates color values for a number of lines of pixels. That number is set by {@link #threshold}.
	 * If the distance between values {@link #yMin} and {@link #yMax} is too big, 
	 * then two new <code>CalculationJobs</code> are created.
	 */
	public static class CalculationJob extends RecursiveAction {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The Constant threshold which is a maximum number of lines that one job should calculate. */
		private static final int threshold = 16;
		
		/** The y min value. */
		private int yMin;
		
		/** The y max value. */
		private int yMax;
		
		/** The screen corner point. */
		private Point3D screenCorner;
		
		/** The i vector, x axis. */
		private Point3D iVector;
		
		/** The j vector, y axis. */
		private Point3D jVector;
		
		/** The horizontal value. */
		private double horizontal;
		
		/** The vertical value. */
		private double vertical;
		
		/** The width of the screen. */
		private int width;
		
		/** The height of the screen. */
		private int height;
		
		/** The scene. */
		private Scene scene;
		
		/** The eye point. */
		private Point3D eye;
		
		/** The red values array. */
		private short[] red;
		
		/** The green values array. */
		private short[] green;
		
		/** The blue values array. */
		private short[] blue;

		
		/**
		 * Instantiates a new calculation job.
		 *
		 * @param yMin the {@link #yMin}
		 * @param yMax the {@link #yMin}
		 * @param screenCorner the {@link #screenCorner}
		 * @param iVector the {@link #iVector}
		 * @param jVector the {@link #jVector}
		 * @param horizontal the {@link #horizontal}
		 * @param vertical the {@link #vertical}
		 * @param width the {@link #width}
		 * @param height the {@link #height}
		 * @param scene the {@link #scene}
		 * @param eye the {@link #eye}
		 * @param red the {@link #red}
		 * @param green the {@link #green}
		 * @param blue the {@link #blue}
		 */
		public CalculationJob(int yMin, int yMax, Point3D screenCorner, Point3D iVector, Point3D jVector, double horizontal,
				double vertical, int width, int height, Scene scene, Point3D eye, short[] red, short[] green,
				short[] blue) {
			this.yMin = yMin;
			this.yMax = yMax;
			this.screenCorner = screenCorner;
			this.iVector = iVector;
			this.jVector = jVector;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.scene = scene;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		/**
		 * If the distance between {@link #yMin} and {@link #yMax} is greater than {@link #threshold}
		 * then two new jobs are created, otherwise colors are calculated for these lines of pixels.
		 */
		@Override
		protected void compute() {
			if(yMax-yMin+1 <= threshold) {
				computeDirect();
				return;
			}
			invokeAll(
				new CalculationJob(yMin, yMin+(yMax-yMin)/2, screenCorner, iVector, jVector,
						horizontal, vertical, width, height, scene, eye, red, green, blue),
				new CalculationJob(yMin+(yMax-yMin)/2, yMax, screenCorner, iVector, jVector, 
						horizontal, vertical, width, height, scene, eye, red, green, blue)
			);
		}
		
		/**
		 * Calculates colors for lines of pixels between {@link #yMin} and {@link #yMax} values.
		 */
		public void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin * width;;
			
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {

					Point3D screenPoint = screenCorner
							.add((iVector.scalarMultiply(horizontal * x / (width - 1)))
									.sub(jVector.scalarMultiply(vertical * y / (height - 1))));

					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb, eye);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}
		}
		
	}
}
