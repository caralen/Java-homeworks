package hr.fer.zemris.java.math;

/**
 * Demo class for testing functionalities of class Vector3.
 * @author Alen Carin
 *
 */
public class ComplexDemo {

	/**
	 * Method that is called upon the start of the program.
	 * @param args command line arguments, not used here.
	 */
	public static void main(String[] args) {
		
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		Vector3 m = l.normalized();
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
		System.out.println(l);
		System.out.println(l.norm());
		System.out.println(m);
		System.out.println(l.dot(j));
		System.out.println(i.add(new Vector3(0,1,0)).cosAngle(l));
		
		Complex[] factors = new Complex[] {new Complex(7, 2), new Complex(2, 0), new Complex(5, 0), new Complex(1, 0)};
		ComplexPolynomial poly = new ComplexPolynomial(factors);
		System.out.println(poly.derive());
		System.out.println(poly.order());
		
		
		Complex[] factors1 = new Complex[] {new Complex(3, 0), new Complex(0, 0), new Complex(1, 0)};
		Complex[] factors2 = new Complex[] {new Complex(5, 0), new Complex(-1, 0), new Complex(1, 0), new Complex(2, 0)};
		System.out.println(new ComplexPolynomial(factors1).multiply(new ComplexPolynomial(factors2)));
		
		System.out.println();
		
		Complex[] roots = new Complex[] {new Complex(5, 0), new Complex(2, 0)};
		ComplexRootedPolynomial rooted = new ComplexRootedPolynomial(roots);
		System.out.println(rooted);
		System.out.println(rooted.toComplexPolynom());
		
		System.out.println();
		
		Complex[] basicRoots = new Complex[] {Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG};
		System.out.println(new ComplexRootedPolynomial(basicRoots).toComplexPolynom().derive());
	}
}
