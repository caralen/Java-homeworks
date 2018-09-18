package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void testParsing() {
		String query1 = " jmbag =\"0123456789\"   ";
		QueryParser parser1 = new QueryParser(query1);
		
		assertTrue(parser1.isDirectQuery());
		
		String query2 = " jmbag =\"0123456789\" and lastName>\"J\"  ";
		QueryParser parser2 = new QueryParser(query2);
		
		assertFalse(parser2.isDirectQuery());
	}
	
	@Test
	public void testGetJmbag1() {
		String query = " jmbag =\"0123456789\"   ";
		QueryParser parser = new QueryParser(query);
		
		assertEquals("0123456789", parser.getQueriedJMBAG());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testGetJmbag2() {
		String query = " jmbag =\"0123456789\" and lastName>\"J\"  ";
		QueryParser parser = new QueryParser(query);
		
		parser.getQueriedJMBAG();
	}
	
	@Test
	public void testGetQuery() {
		String query = " jmbag =\"0123456789\" and lastName>\"J\"  ";
		QueryParser parser = new QueryParser(query);
		
		assertEquals(2, parser.getQuery().size());
	}
}
