package hr.fer.zemris.java.hw05.db;

/**
 * Class which defines seven implementations of <code>IComparisonOperator</code>.
 * Each comparison operator has different satisfaction rule.
 * @author Alen Carin
 *
 */
public class ComparisonOperators {

	/**The comparison is satisfied if the first string has smaller sum of ASCII values than the second.*/
	public static final IComparisonOperator LESS;
	
	/**The comparison is satisfied if the first string has smaller or equal sum of ASCII values than the second.*/
	public static final IComparisonOperator LESS_OR_EQUALS;
	
	/**The comparison is satisfied if the first string has greater sum of ASCII values than the second.*/
	public static final IComparisonOperator GREATER;
	
	/**The comparison is satisfied if the first string has greater or equal sum of ASCII values than the second.*/
	public static final IComparisonOperator GREATER_OR_EQUALS;
	
	/**The comparison is satisfied if the first string has the same sum of ASCII values as the second.*/
	public static final IComparisonOperator EQUALS;
	
	/**The comparison is satisfied if the first string has different sum of ASCII values than the second.*/
	public static final IComparisonOperator NOT_EQUALS;
	
	/**The comparison is satisfied if the first string contains the second string pattern.*/
	public static final IComparisonOperator LIKE;
	
	static {
		LESS = (value1, value2) -> {
			return value1.compareTo(value2) < 0 ? true : false;
		};
		
		LESS_OR_EQUALS = (value1, value2) -> {
			return value1.compareTo(value2) <= 0 ? true : false;
		};
		
		GREATER = (value1, value2) -> {
			return value1.compareTo(value2) > 0 ? true : false;
		};
		
		GREATER_OR_EQUALS = (value1, value2) -> {
			return value1.compareTo(value2) >= 0 ? true : false;
		};
		
		EQUALS = (value1, value2) -> {
			return value1.compareTo(value2) == 0 ? true : false;
		};
		
		NOT_EQUALS = (value1, value2) -> {
			return value1.compareTo(value2) != 0 ? true : false;
		};
		
		LIKE = (value1, value2) -> {

			if (!value2.contains("*") && value1.contains(value2)) {
				return true;
			}

			if (value2.startsWith("*")) {
				if (value1.endsWith(value2.replace("*", ""))) {
					return true;
				} else {
					return false;
				}
			} else if (value2.endsWith("*")) {
				if (value1.startsWith(value2.replace("*", ""))) {
					return true;
				} else {
					return false;
				}
			} else {
				String[] pattern = value2.split("\\*");
				if (value1.startsWith(pattern[0]) && value1.endsWith(pattern[1])
						&& value1.length() >= value2.replace("*", "").length()) {
					return true;
				} else {
					return false;
				}
			}
		};
	}
}
