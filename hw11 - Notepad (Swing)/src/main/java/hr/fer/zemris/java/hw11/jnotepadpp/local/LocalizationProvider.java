package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Class LocalizationProvider is a singleton.
 * There can be only one instance of this class and it is instantiated when the class is created.
 * There is no public constructor.
 * Instance of this class can only be fetched by calling the static getInstance method.
 * Then you can call methods setLanguage and getString through the instance of this class.
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** The only instance of this localization provider. */
	private static LocalizationProvider localizationProvider = new LocalizationProvider();
	
	/** The current language of this provider. */
	private String language;
	
	/** The bundle for fetching resources. */
	private ResourceBundle bundle;
	
	/**
	 * Private constructor which sets the default language to english and
	 * saves values from resources into the bundle.
	 */
	private LocalizationProvider() {
		this.language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
	}
	
	/**
	 * Gets the only instance of LocalizationProvider.
	 *
	 * @return single instance of LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		return localizationProvider;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
		fire();
	}
	
	/**
	 * Gets the value for the given key in language that is currently active.
	 */
	@Override
	public String getString(String key) {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		return bundle.getString(key);
	}
}
