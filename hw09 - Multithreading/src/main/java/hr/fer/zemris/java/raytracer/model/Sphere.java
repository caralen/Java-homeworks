package hr.fer.zemris.java.raytracer.model;

/**
 * The Class Sphere represents a circular 3D graphical object.
 */
public class Sphere extends GraphicalObject {
	
	/** The center of the sphere. */
	Point3D center;
	
	/** The radius of the sphere. */
	double radius;
	
	/** The red diffuse component coefficients. */
	double kdr;
	
	/** The green diffuse component coefficients. */
	double kdg;
	
	/** The blue diffuse component coefficients. */
	double kdb;
	
	/** The red reflective component coefficients. */
	double krr;
	
	/** The green reflective component coefficients. */
	double krg;
	
	/** The blue reflective component coefficients. */
	double krb;
	
	/** The reflective component coefficient. */
	double krn;
	

	/**
	 * Instantiates a new sphere.
	 *
	 * @param center {@link #center}
	 * @param radius {@link #radius}
	 * @param kdr {@link #kdr}
	 * @param kdg {@link #kdg}
	 * @param kdb {@link #kdb}
	 * @param krr {@link #krr}
	 * @param krg {@link #krg}
	 * @param krb {@link #krb}
	 * @param krn {@link #krn}
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, 
			double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}


	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D start = ray.start;
		Point3D direction = ray.direction;

		double a = direction.norm() * direction.norm();
		double b = 2 * direction.scalarProduct(start.sub(center));
		double c = start.sub(center).scalarProduct(start.sub(center)) - Math.pow(radius, 2);

		double positiveRoot = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
		double negativeRoot = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);
		
		Point3D point;
		double distance = 0;
		boolean outer = false;

		if ((positiveRoot == negativeRoot || (positiveRoot > 0 && negativeRoot > 0))) {
			if (positiveRoot > negativeRoot) {
				positiveRoot = negativeRoot;
				point = start.add(direction.scalarMultiply(positiveRoot));
				distance = start.sub(point).norm();
				outer = false;
			} else {
				point = start.add(direction.scalarMultiply(positiveRoot));
				distance = start.sub(point).norm();
				outer = true;
			}
		} else {
			return null;
		}

		
		return new RayIntersection(point, distance, outer) {
			
			@Override
			public Point3D getNormal() {
				return point.sub(center).normalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
