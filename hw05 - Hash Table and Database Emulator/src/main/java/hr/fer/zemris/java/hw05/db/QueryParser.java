package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Class which takes a string representation of query and parses it into 
 * a list of conditional expressions. There are several methods to fetch the parsed query.
 * @author Alen Carin
 *
 */
public class QueryParser {

	/**String representation of a line which queries a database.*/
	private String query;
	
	/**List of conditional expressions that together represent a query.*/
	private List<ConditionalExpression> expressions;

	/**
	 * Constructor. Takes a string representation of query and calls the parse method.
	 * @param query which will be parsed.
	 */
	public QueryParser(String query) {
		this.query = query;
		expressions = new ArrayList<>();
		parseQuery();
	}
	
	/**
	 * Method which takes care of parsing the query. It uses a lexer to
	 * parse the string representation of query into a list of conditional expressions.
	 */
	private void parseQuery() {
		Lexer lexer = new Lexer(query);
		boolean firstTime = true;
		lexer.nextToken();
		
		while(lexer.getToken().getType() != TokenType.EOF) {
			int counterOfTokens = 0;
			Token[] tokens = new Token[3];
			if(!firstTime) {
				lexer.nextToken();
			}
			firstTime = false;
			
			while(lexer.getToken().getType() != TokenType.LOGICAL_OPERATOR && 
					lexer.getToken().getType() != TokenType.EOF) {
				tokens[counterOfTokens++] = lexer.getToken();
				lexer.nextToken();
			}
			if(isValidExpression(tokens)) {
				IFieldValueGetter fieldGetter = createFieldValueGetter(tokens[0].getValue());
				IComparisonOperator comparisonOperator = createComparisonOperator(tokens[1].getValue());
				String stringLiteral = createStringLiteral(tokens[2].getValue());
				
				expressions.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
			} else {
				throw new IllegalArgumentException("Expression is invalid");
			}
		}
	}


	/**
	 * Creates a field value getter from the given token value.
	 * @param token is the value of the extracted token.
	 * @return
	 */
	private IFieldValueGetter createFieldValueGetter(Object token) {
		String fieldValue = (String) token;
		
		switch(fieldValue) {
		
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
			
		case "lastName":
			return FieldValueGetters.LAST_NAME;
			
		case "jmbag":
			return FieldValueGetters.JMBAG;
			
		default:
			throw new IllegalArgumentException("Invalid field value getter");
		}
	}

	/**
	 * Creates a comparison operator from the given token value.
	 * @param token is the value of the extracted token.
	 * @return comparison operator.
	 */
	private IComparisonOperator createComparisonOperator(Object token) {
		String operator = (String) token;
		
		switch (operator) {

		case "<":
			return ComparisonOperators.LESS;

		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;

		case ">":
			return ComparisonOperators.GREATER;

		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;

		case "!=":
			return ComparisonOperators.NOT_EQUALS;

		case "=":
			return ComparisonOperators.EQUALS;

		case "LIKE":
			return ComparisonOperators.LIKE;

		default:
			throw new IllegalArgumentException("Invalid comparison operator");
		}
	}

	/**
	 * Creates a string literal from the given value.
	 * @param value that should be a valid string literal.
	 * @throws IllegalArgumentException if the value is not valid literal.
	 * @return literal in string format.
	 */
	private String createStringLiteral(Object value) {
		String literal = (String) value;
		if(literal.contains("*")) {
			String literalWithoutAsterisk = literal.replaceFirst("\\*", "");
			if(literalWithoutAsterisk.contains("*")) {
				throw new IllegalArgumentException("String literal contains more than one wildcard *");
			}
		}
		return literal;
	}

	/**
	 * Checks if the expression is valid. Expression is valid if it consists of:
	 * attribute name, operator and string literal in the correct order.
	 * @param tokens array of extracted tokens.
	 * @return true if the expression is valid.
	 */
	private boolean isValidExpression(Token[] tokens) {
		if(tokens.length != 3) {
			return false;
		}
		if(tokens[0].getType() == TokenType.ATTRIBUTE_NAME &&
					tokens[1].getType() == TokenType.OPERATOR &&
					tokens[2].getType() == TokenType.LITERAL) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the query has only one comparison, on attribute jmbag,
	 * and operator must be equals.
	 * @return true if the query is direct.
	 */
	public boolean isDirectQuery() {
		if(expressions.size() != 1) {
			return false;
		}
		if(expressions.get(0).getComparisonOperator() != ComparisonOperators.EQUALS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the queried student jmbag if the query is direct, 
	 * @throws IllegalStateException if the query is not direct.
	 * @return student jmbag.
	 */
	public String getQueriedJMBAG() {
		if (!this.isDirectQuery()) {
			throw new IllegalStateException("Must be direct query to call getQueriedJMBAG.");
		} else {
			return expressions.get(0).getStringLiteral();
		}
	}
	
	/**
	 * Returns list of conditional expressions which represents a single query.
	 * @return list of conditional expressions.
	 */
	public List<ConditionalExpression> getQuery(){
		return expressions;
	}
}
