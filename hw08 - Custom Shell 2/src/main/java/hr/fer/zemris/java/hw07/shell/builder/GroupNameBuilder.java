package hr.fer.zemris.java.hw07.shell.builder;

/**
 * Implementation of the NameBuilder. This group represents a reference to the certain group in mask.
 * @author Alen Carin
 *
 */
public class GroupNameBuilder implements NameBuilder {
	
	/** Index at which this group is in the mask. */
	private int index;
	
	/** Padding that is added at the beginning of this group. */
	private String padding;
	
	/**
	 * Instantiates a new group name builder.
	 *
	 * @param index {@link #index}
	 */
	public GroupNameBuilder(int index) {
		this.index = index;
	}

	/**
	 * Instantiates a new group name builder.
	 *
	 * @param index {@link #index}
	 * @param padding {@link #padding}
	 */
	public GroupNameBuilder(int index, String padding) {
		this.index = index;
		this.padding = padding;
	}

	/**
	 * Appends this group to the string builder containing text that represents the new name of the file.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		if(padding == null) {
			info.getStringBuilder().append(info.getGroup(index));
		}
		else {
			int addition = Integer.parseInt(padding);
			
			if(padding.contains("0")) {
				info.getStringBuilder().append(
						String.format("%0" + addition + "d", Integer.parseInt(info.getGroup(index)))
						);
			} else {
				info.getStringBuilder().append(
						String.format("%" + addition + "%d", Integer.parseInt(info.getGroup(index)))
						);
			}
		}
	}

}
