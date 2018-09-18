package hr.fer.zemris.java.hw16.trazilica;

/**
 * Interface that is used for building different structures.
 * It is used for reducing code duplication in building vectors and vocabulary in console.
 * @author Alen Carin
 *
 */
@FunctionalInterface
public interface Builder {

	/**
	 * Builds a specific structure.
	 * @param sb is the string builder
	 * @param v is the vector
	 */
	public void build(StringBuilder sb, Vector v);
}
