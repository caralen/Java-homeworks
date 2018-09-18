package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The Class LocalizableAction extends <code>AbstractAction</code>.
 * It changes its name and tool tip key each time the language is changed.
 */
public abstract class LocalizableAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key for the name value. */
	private String nameKey;
	
	/** The accelerator key. */
	private String accKey;
	
	/** The mnemonic key. */
	private int mneKey;
	
	/** The key for the description value, i.e. tool tip description. */
	private String descKey;
	
	/** The localization provider which notifies its listeners each time the localization is changed. */
	private ILocalizationProvider provider;
	
	

	/**
	 * Instantiates a new localizable action.
	 *
	 * @param nameKey the key for the name value
	 * @param accKey the accelerator key
	 * @param mneKey the mnemonic key
	 * @param descKey the key for the description value
	 * @param provider the provider
	 */
	public LocalizableAction(String nameKey, String accKey, int mneKey, String descKey,
			ILocalizationProvider provider) {
		super();
		this.nameKey = nameKey;
		this.accKey = accKey;
		this.mneKey = mneKey;
		this.descKey = descKey;
		this.provider = provider;
		translate();
		addListener();
		setShortcuts();
	}

	/**
	 * Sets the shortcuts for accelerator and mnemonic key.
	 */
	private void setShortcuts() {
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(accKey));
		this.putValue(Action.MNEMONIC_KEY, mneKey);
	}

	/**
	 * Adds the listener that translates values each time the language is changed.
	 */
	private void addListener() {
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				translate();
			}
		});
	}

	/**
	 * Translates name and short description values.
	 */
	private void translate() {
		this.putValue(Action.NAME, provider.getString(nameKey));
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString(descKey));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);

}
