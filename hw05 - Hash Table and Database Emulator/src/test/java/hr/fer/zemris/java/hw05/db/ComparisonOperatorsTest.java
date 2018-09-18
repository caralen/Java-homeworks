package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

/**
 * Class used for testing the <code>ComparisonOperators</code> class.
 * @author Alen Carin
 *
 */
public class ComparisonOperatorsTest {
	
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
	public void testLess() {
		
		assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testLessOrEquals() {
		
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Jasna", "Pero"));
	}
	
	@Test
	public void testGreater() {
		
		assertTrue(ComparisonOperators.GREATER.satisfied("Tin", "Fran"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Tin", "Fran"));
	}
	
	@Test
	public void testEquals() {
		
		assertTrue(ComparisonOperators.EQUALS.satisfied("Tin", "Tin"));
	}
	
	@Test
	public void testNotEquals() {
		
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Jasna", "Pero"));
	}
	
	@Test
	public void testLike() {
		
		List<String> lines = getLinesFromTheFile();
		StudentDatabase db = new StudentDatabase(lines);
		StudentRecord student = db.forJMBAG("0000000001");
		
		assertTrue(ComparisonOperators.LIKE.satisfied(FieldValueGetters.FIRST_NAME.get(student), "Ma*"));
		assertTrue(ComparisonOperators.LIKE.satisfied(FieldValueGetters.FIRST_NAME.get(student), "*in"));
		assertTrue(ComparisonOperators.LIKE.satisfied(FieldValueGetters.FIRST_NAME.get(student), "M*n"));
		assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
		assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
	}
}
