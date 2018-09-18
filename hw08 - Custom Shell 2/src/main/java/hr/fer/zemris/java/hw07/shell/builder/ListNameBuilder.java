package hr.fer.zemris.java.hw07.shell.builder;

import java.util.List;

/**
 * The implementation of the NameBuilder which keeps a list of references to other NameBuilders.
 * @author Alen Carin
 *
 */
public class ListNameBuilder implements NameBuilder {
	
	/** A list of NameBuilders. */
	private List<NameBuilder> nameBuilders;
	
	/**
	 * Instantiates a new list name builder.
	 *
	 * @param nameBuilders the name builders
	 */
	public ListNameBuilder(List<NameBuilder> nameBuilders) {
		this.nameBuilders = nameBuilders;
	}

	/**
	 * Iterates through list of NameBuilders and calls their execute methods.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		for(NameBuilder builder : nameBuilders) {
			builder.execute(info);
		}
	}

}
