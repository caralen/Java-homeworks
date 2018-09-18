package hr.fer.zemris.java.hw05.db;

/**
 * Represents a single student record with 4 field values:
 * jmbag, first name, last name and final grade.
 * Each student has its unique jmbag.
 * @author Alen Carin
 *
 */
public class StudentRecord {
	
	/**Unique sequence of numbers for each student.*/
	private String jmbag;
	
	/**Student's last name*/
	private String lastName;
	
	/**Student's first name*/
	private String firstName;
	
	/**Student's final grade*/
	private int finalGrade;
	
	/**
	 * Sets value of fields to the values passed through arguments.
	 * @param jmbag Unique sequence of numbers for each student.
	 * @param lastName Student's last name
	 * @param firstName Student's first name
	 * @param finalGrade Student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	

	/**
	 * Returns jmbag of this student.
	 * @return jmbag of this student.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns last name of this student.
	 * @return last name of this student.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns first name of this student.
	 * @return first name of this student.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns final grade of this student.
	 * @return final grade of this student.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Calculates value of the hash code based on {@link #jmbag}.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this one based on {@link #jmbag}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return jmbag + " " + lastName + " " + firstName + " " + finalGrade;
	}
}
