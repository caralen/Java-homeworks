package hr.fer.zemris.java.hw07.shell.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for parsing the given expression.
 *
 * @author Alen Carin
 */
public class NameBuilderParser {

	/** The expression which has to be parsed. */
	private String expression;
	
	/** The list of name builders. */
	private List<NameBuilder> builders;
	
	/**
	 * Instantiates a new name builder parser.
	 *
	 * @param expression the expression
	 */
	public NameBuilderParser(String expression) {
		builders = new ArrayList<>();
		this.expression = expression;
		parse();
	}
	 

	/**
	 * Gets the name builder.
	 *
	 * @return the name builder
	 */
	public NameBuilder getNameBuilder() {
		return new ListNameBuilder(builders);
	}

	
	/**
	 * Parses the {@link #expression} and creates appropriate NameBuilders.
	 */
	private void parse() {
		if(expression.contains(" ")) {
			throw new IllegalArgumentException();
		}
		String[] parts = expression.split("\\$\\{|\\}");
		
		if(parts.length % 2 != 1) {
			throw new IllegalArgumentException();
		}
		int counter = 0;
		
		for(String part : parts) {
			if (counter % 2 == 0) {
				builders.add(new ConstNameBuilder(part));
			} else {
				part = part.replace("\\s+", "");
				
				if(part.contains(",")) {
					String[] subParts = part.split(",");
					builders.add(new GroupNameBuilder(Integer.parseInt(subParts[0]), subParts[1]));
				} else {
					builders.add(new GroupNameBuilder(Integer.parseInt(part)));
				}
			}
			counter++;
		}
	}
}
