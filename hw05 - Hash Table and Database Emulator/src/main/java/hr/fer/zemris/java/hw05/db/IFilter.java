package hr.fer.zemris.java.hw05.db;

/**
 * This class is a filter for student records.
 * @author Alen Carin
 *
 */
public interface IFilter {

	/**
	 * Method which defines which student records should be accepted if this filter is used.
	 * @param record is a student record.
	 * @return true if the record is accepted.
	 */
	public boolean accepts(StudentRecord record);
}
