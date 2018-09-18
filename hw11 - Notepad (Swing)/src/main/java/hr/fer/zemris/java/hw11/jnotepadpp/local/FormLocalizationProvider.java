package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * The Class FormLocalizationProvider extends <code>LocalizationProviderBridge</code>.
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Instantiates a new form localization provider.
	 * Sets a window listener to the given frame.
	 * Each time the window is opened the connect method is called.
	 * Each time the window is closed the disconnect method is called.
	 *
	 * @param provider the localization provider
	 * @param frame the window frame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
