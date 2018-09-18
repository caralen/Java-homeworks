package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class used for testing the <code>ConditionalExpression</code> class.
 * @author Alen Carin
 *
 */
public class ConditionalExpressionTest {

	
	@Test
	public void testConditionalExpression1() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME, 
				"Bos*", 
				ComparisonOperators.LIKE
				);
		
		assertEquals(expr.getFieldGetter(), FieldValueGetters.LAST_NAME);
		assertEquals(expr.getStringLiteral(), "Bos*");
		assertEquals(expr.getComparisonOperator(), ComparisonOperators.LIKE);
	}
	
	@Test
	public void testConditionalExpression2() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME, 
				"Bos*", 
				ComparisonOperators.LIKE
				);
		
		StudentRecord record = new StudentRecord("0123", "Bosnjak", "Marko", 5);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		
		assertTrue(recordSatisfies);
	}
}
