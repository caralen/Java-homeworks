package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashTable;

/**
 * Class which represents a student database.
 * It has two public methods: forJMBAG() for getting a StudentRecord based
 * on the given jmbag and filter() for filtering list of student records.
 * @author Alen Carin
 *
 */
public class StudentDatabase {
	
	/**List of strings representing student records.*/
	private List<String> rows;
	
	/**List of student records.*/
	private List<StudentRecord> studentRecords;
	
	/**Hash table containing pairs of String, StudentRecord.
	 * It enables searching for a specific student in O(1)*/
	private SimpleHashTable<String, StudentRecord> index;
	
	
	
	/**
	 * Constructor which takes one parameter, a list of strings,
	 * each string containing information about one student.
	 * @param rows of string containing information about one student.
	 */
	public StudentDatabase(List<String> rows) {
		this.rows = rows;
		studentRecords = new ArrayList<>();
		index = new SimpleHashTable<>();
		
		createStudentRecords();
		createIndex();
	}

	/**
	 * Parses the list of string rows where each row represents a record of a student.
	 * Then fills the list of student records with parsed information.
	 */
	private void createStudentRecords() {
		for(String row : rows) {
			String studentFields[]  = row.split("\\s+");
			
			if (studentFields.length == 4) {
				studentRecords.add(new StudentRecord(
						studentFields[0], 
						studentFields[1], 
						studentFields[2], 
						Integer.parseInt(studentFields[3])));
			} else if (studentFields.length == 5) {
				studentRecords.add(new StudentRecord(
						studentFields[0], 
						studentFields[1] + studentFields[2], 
						studentFields[3], 
						Integer.parseInt(studentFields[4])));
			} else {
				throw new IllegalArgumentException("Something is not right in the file");
			}
		}
	}
	
	/**
	 * Fills the index hash table with pairs student jmbag, student record.
	 * Each student record is then easily accessible trough the index in O(1).
	 */
	private void createIndex() {
		for(StudentRecord student : studentRecords) {
			index.put(student.getJmbag(), student);
		}
	}

	/**
	 * Returns StudentRecord which has jmbag equal to the one passed through arguments.
	 * @param jmbag Unique sequence of numbers for each student.
	 * @return StudentRecord that has the given jmbag.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 * Filters the list of StudentRecords and returns list containing
	 * only desired StudentRecords specified through filter.
	 * @param filter which chooses which records should be accepted.
	 * @return filtered list of StudentRecords.
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> tempList = new ArrayList<>();
		for(StudentRecord student : studentRecords) {
			if(filter.accepts(student)) {
				tempList.add(student);
			}
		}
		return tempList;
	}
}
