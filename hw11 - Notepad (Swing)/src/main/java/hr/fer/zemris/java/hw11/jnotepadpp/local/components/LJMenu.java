package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The Class LJMenu is a localized menu 
 * which sets its value to the new value each time the language is changed.
 */
public class LJMenu extends JMenu {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The key for getting the label name. */
	private String key;
	
	/** The localization provider which notifies its listeners each time the localization is changed. */
	private ILocalizationProvider provider;
	
	/**
	 * Instantiates a new LJ menu.
	 *
	 * @param key {@link #key}
	 * @param provider {@link #provider}
	 */
	public LJMenu(String key, ILocalizationProvider provider) {
		super();
		this.key = key;
		this.provider = provider;
		updateLabel();
		addListener();
	}
	
	/**
	 * Adds a new localization listener which is called 
	 * which calls the update method each time it is notified about the change.
	 */
	private void addListener() {
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				updateLabel();
			}
		});
	}

	/**
	 * Updates value of this menu.
	 */
	private void updateLabel() {
		this.setText(provider.getString(key));
	}
}
