package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

/**
 * Class used for testing the <code>FieldValueGetters</code> class.
 * @author Alen Carin
 *
 */
public class FieldValueGettersTest {
	
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
	public void testFieldValueGetters() {
		
		List<String> lines = getLinesFromTheFile();
		StudentDatabase db = new StudentDatabase(lines);
		StudentRecord student = db.forJMBAG("0000000001");
		
		String firstName = FieldValueGetters.FIRST_NAME.get(student);
		String lastName = FieldValueGetters.LAST_NAME.get(student);
		String jmbag = FieldValueGetters.JMBAG.get(student);
		
		assertEquals("Marin", firstName);
		assertEquals("Akšamović", lastName);
		assertEquals("0000000001", jmbag);
	}
}
