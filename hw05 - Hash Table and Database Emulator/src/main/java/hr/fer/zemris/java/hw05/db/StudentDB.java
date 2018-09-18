package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Program which takes simple SQL queries and prints table of student records from the
 * <code>database.txt</code> file which satisfy the query criteria.
 * @author Alen Carin
 *
 */
public class StudentDB {

	/**
	 * Main method which is called upon the start of the program.
	 * @param args that are passed through command line, not used here.
	 */
	public static void main(String[] args) {
		
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			
		} catch (IOException e) {
			System.out.println("Cannot read from the file");
			System.exit(-1);
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		Scanner sc = new Scanner(System.in);
		Set<StudentRecord> recordsSet = new HashSet<>();
		
		while(true) {
			recordsSet.clear();
			System.out.printf("> ");
			String input = sc.nextLine();
			
			if(input.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			if(input.contains("query")) {
				input = input.replace("query", "");
			} else {
				System.out.println("Query must contain command \"query\"!");
				continue;
			}
			QueryParser parser = null;
			try {
				parser = new QueryParser(input);
			} catch(Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			if(parser.isDirectQuery()) {
				StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());
				if(record != null) {
					recordsSet.add(record);
				}
			} else {
				for(StudentRecord record : db.filter(new QueryFilter(parser.getQuery()))) {
					recordsSet.add(record);
				}
			}
			if(!recordsSet.isEmpty()) {
				printTable(recordsSet);
			}
			System.out.println("Records selected: " + recordsSet.size());
		}
		sc.close();
	}

	/**
	 * Prints the table of student records.
	 * @param recordsSet is a set containing student records which have to be printed.
	 */
	private static void printTable(Set<StudentRecord> recordsSet) {
		int jmbagLength = 10;
		int maxLastNameLength = 0;
		int maxFirstNameLength = 0;
		int finalGradeLength = 1;
		
		for(StudentRecord record : recordsSet) {
			if(record.getLastName().length() > maxLastNameLength) {
				maxLastNameLength = record.getLastName().length();
			}
			if(record.getFirstName().length() > maxFirstNameLength) {
				maxFirstNameLength = record.getFirstName().length();
			}
		}
		
		printSymbolLine(jmbagLength, maxLastNameLength, maxFirstNameLength, finalGradeLength);
		
		for(StudentRecord record : recordsSet) {
			System.out.printf("|");
			System.out.printf(" %s ", record.getJmbag());
			System.out.printf("|");
			System.out.printf(" %s ", record.getLastName());
			printSpaces(maxLastNameLength - record.getLastName().length());
			System.out.printf("|");
			System.out.printf(" %s ", record.getFirstName());
			printSpaces(maxFirstNameLength - record.getFirstName().length());
			System.out.printf("|");
			System.out.printf(" %s ", record.getFinalGrade());
			System.out.printf("|%n");
		}
		
		printSymbolLine(jmbagLength, maxLastNameLength, maxFirstNameLength, finalGradeLength);
	}

	/**
	 * Prints as much spaces as the value of length variable passed through arguments.
	 * @param length is the number of spaces that have to be printed.
	 */
	private static void printSpaces(int length) {
		for(int i = 0; i < length; i++) {
			System.out.printf(" ");
		}
	}

	/**
	 * Prints a line of equals and plus symbols in the correct way.
	 * @param jmbagLen is the length of every jmbag.
	 * @param lastNameLen is the length of the longest last name.
	 * @param firstNameLen is the length of the longest first name.
	 * @param gradeLen is the length of every final grade.
	 */
	private static void printSymbolLine(int jmbagLen, int lastNameLen, int firstNameLen, int gradeLen) {
		int additionalSpaces = 2;
		
		System.out.printf("+");
		printEqualsSymbol(jmbagLen + additionalSpaces);
		System.out.printf("+");
		printEqualsSymbol(lastNameLen + additionalSpaces);
		System.out.printf("+");
		printEqualsSymbol(firstNameLen + additionalSpaces);
		System.out.printf("+");
		printEqualsSymbol(gradeLen + additionalSpaces);
		System.out.printf("+%n");
	}

	/**
	 * Prints as many equals symbols as the value of the length variable. 
	 * @param length is the number of equals symbols that have to be printed.
	 */
	private static void printEqualsSymbol(int length) {
		for(int i = 0; i < length; i++) {
			System.out.printf("=");
		}
	}
}
