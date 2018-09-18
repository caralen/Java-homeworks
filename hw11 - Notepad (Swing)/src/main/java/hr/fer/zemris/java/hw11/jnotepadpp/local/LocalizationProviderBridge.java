package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The Class LocalizationProviderBridge is a bridge between <code>FormLocalizationProvider</code> 
 * and <code>AbstractLocalizationProvider</code>.
 * It provides methods for connecting and disconnecting of localization listeners.
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** The provider. */
	private ILocalizationProvider provider;
	
	/** The flag which tells if this provider is connected or not. */
	private boolean connected;
	
	/** The listener. */
	private ILocalizationListener listener;
	
	/**
	 * Instantiates a new localization provider bridge.
	 *
	 * @param provider the provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}
	
	/**
	 * Disconnects provider if it was connected.
	 * It removes this {@link #listener} from the providers collection of listeners.
	 */
	public void disconnect() {
		if(!connected) return;
		
		provider.removeLocalizationListener(listener);
		connected = false;
	}
	
	/**
	 * Connects provider if it already isn't connected.
	 * If it isn't connected it creates an anonymous class for <code>ILocalizationListener</code>
	 * and adds it to providers localization listeners.
	 */
	public void connect() {
		if(connected) return;
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
		
		provider.addLocalizationListener(listener);
		connected = true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
}
