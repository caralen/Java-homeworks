package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

/**
 * Class used for testing the <code>StudentDatabase</code> class.
 * @author Alen Carin
 *
 */
public class StudentDatabaseTest {
	
	private List<String> getLinesFromTheFile() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			return lines;
		} catch (IOException e) {
			System.out.println("Can't read from the file!");
		}
		return null;
	}

	@Test
	public void testforJMBAG() {
		List<String> lines = getLinesFromTheFile();
		
		StudentDatabase db = new StudentDatabase(lines);
		
		assertEquals(db.forJMBAG("0000000006"), 
				new StudentRecord("0000000006", "Cvrlje", "Ivan", 3));
	}
	
	@Test
	public void testFilter(){
		IFilter filterTrue = record -> true;
		IFilter filterFalse = record -> false;
		
		List<String> lines = getLinesFromTheFile();
		StudentDatabase db = new StudentDatabase(lines);
		
		assertTrue(db.filter(filterTrue).size() == 63);
		assertTrue(db.filter(filterFalse).size() == 0);
	}
}
