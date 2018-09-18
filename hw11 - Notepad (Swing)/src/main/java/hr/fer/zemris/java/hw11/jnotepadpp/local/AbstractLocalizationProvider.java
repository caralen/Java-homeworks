package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class AbstractLocalizationProvider implements <code>ILocalitarionProvider</code> interface
 * and implements all its methods except getString.
 * It has a collection of localization listeners which can be added or removed via public methods.
 * When the method fire is called all listeners are notified about the change.
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	/** The listeners. */
	List<ILocalizationListener> listeners;
	
	/**
	 * Instantiates a new abstract localization provider.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider#addLocalizationListener(hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener)
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider#removeLocalizationListener(hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener)
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Fire.
	 */
	public void fire() {
		for(ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider#getString(java.lang.String)
	 */
	public abstract String getString(String key);
}
