package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class QueryFilterTest {
	
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
	public void testFilter() {
		
		List<String> lines = getLinesFromTheFile();
		StudentDatabase db = new StudentDatabase(lines);
		
		String query2 = " jmbag >\"0000000015\" and firstName>\"F\"  ";
		QueryParser parser2 = new QueryParser(query2);
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser2.getQuery()));
		assertEquals(35, (records.size()));
		
	}
}

