package hr.fer.zemris.java.hw05.db;

/**
 * A getter for certain field of <code>StudentRecord</code>.
 * @author Alen Carin
 *
 */
public interface IFieldValueGetter {

	/**
	 * Returns String value of a certain field in <code>StudentRecord</code>.
	 * @param record of a student.
	 * @return value of a certain field of student record.
	 */
	public String get(StudentRecord record);
}
