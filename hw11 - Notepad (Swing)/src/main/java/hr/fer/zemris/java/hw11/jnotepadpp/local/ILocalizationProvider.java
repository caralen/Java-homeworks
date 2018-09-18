package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The Interface ILocalizationProvider.
 */
public interface ILocalizationProvider {

	/**
	 * Adds the localization listener.
	 *
	 * @param listener the localization listener
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Removes the localization listener.
	 *
	 * @param listener the localization listener
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Gets the string that is under the given key.
	 *
	 * @param key the value of the key
	 * @return the string that is under the given key
	 */
	String getString(String key);
}
