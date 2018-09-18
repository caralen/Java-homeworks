package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * This class is an implementation of the <code>IFilter</code> interface.
 * This filter accepts student records which are searched by the given query.
 * @author Alen Carin
 *
 */
public class QueryFilter implements IFilter {
	
	/**Query split into list of conditional expressions, between them should stand "and".*/
	List<ConditionalExpression> expressions;
	
	/**
	 * Constructor which takes a list of conditional expressions and sets its field value.
	 * @param expressions {@link #expressions}
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	/**
	 * Accepts those student records which are searched by {@link #expressions}.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		boolean recordSatisfies;
		
		for(ConditionalExpression expression : expressions) {
			recordSatisfies = expression.getComparisonOperator().satisfied(
					expression.getFieldGetter().get(record), 
					expression.getStringLiteral());
			
			if(!recordSatisfies) {
				return false;
			}
		}
		return true;
	}

}
