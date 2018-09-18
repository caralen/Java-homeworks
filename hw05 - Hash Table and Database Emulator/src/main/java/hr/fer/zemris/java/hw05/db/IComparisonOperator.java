package hr.fer.zemris.java.hw05.db;

/**
 * Operator for comparison between two Strings.
 * @author Alen Carin
 *
 */
public interface IComparisonOperator {

	/**
	 * Returns true if the first and second string satisfy the comparison operator.
	 * @param value1 first string.
	 * @param value2 second string.
	 * @return true if the comparison is satisfied, false otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
