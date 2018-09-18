package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import static hr.fer.zemris.java.raytracer.Util.tracer;

/**
 * The program for calculating and drawing ray caster.
 * 
 * @author Alen Carin
 *
 */
public class RayCaster {
	

	/**
	 * Method which is called upon the start of the program.
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
				short[] rgb = new short[3];
				int offset = 0;

				for (int y = 0; y < height; y++) {
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
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
}
