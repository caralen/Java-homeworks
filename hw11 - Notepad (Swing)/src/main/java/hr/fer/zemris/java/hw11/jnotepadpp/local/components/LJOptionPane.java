package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The Class LJOptionPane is a localized option pane 
 * which sets its values to the new values each time the language is changed.
 */
public class LJOptionPane extends JOptionPane {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Calls static method showMessageDialog on the <code>JOptionPane</code> with values in current language.
	 *
	 * @param parentComponent the parent component
	 * @param messageKey the message key
	 * @param titleKey the title key
	 * @param messageType the message type
	 * @param provider the provider
	 */
	public static void showMessageDialog(Component parentComponent, String messageKey, String titleKey, int messageType, 
			ILocalizationProvider provider) {
		
		JOptionPane.showMessageDialog(
				parentComponent, 
				provider.getString(messageKey), 
				provider.getString(titleKey), 
				messageType);
	}
	
	/**
	 * Calls static method showMessageDialog on the <code>JOptionPane</code> with values in current language.
	 *
	 * @param parentComponent the parent component
	 * @param messageKeys the message keys
	 * @param titleKey the title key
	 * @param messageType the message type
	 * @param provider the provider
	 */
	public static void showMessageDialog(Component parentComponent, String[] messageKeys, String titleKey, int messageType, 
			ILocalizationProvider provider) {
		
		String message = translateArray(messageKeys, provider);
		
		JOptionPane.showMessageDialog(
				parentComponent, 
				message, 
				provider.getString(titleKey), 
				messageType);
	}

	/**
	 * Translates array of strings to the values in current language.
	 * Every value at the even index of the array is translated.
	 *
	 * @param messageKeys the message keys
	 * @param provider the provider
	 * @return the string
	 */
	private static String translateArray(String[] messageKeys, ILocalizationProvider provider) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < messageKeys.length; i++) {
			
			if (i % 2 == 0) {
				sb.append(provider.getString(messageKeys[i]));
			} else {
				sb.append(messageKeys[i]);
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * Calls static method showOptionDialog on the <code>JOptionPane</code> with values in current language.
	 *
	 * @param parentComponent the parent component
	 * @param message the message
	 * @param title the title
	 * @param optionType the option type
	 * @param messageType the message type
	 * @param icon the icon
	 * @param options the options
	 * @param initialValue the initial value
	 * @param provider the provider
	 * @return the int
	 */
	public static int showOptionDialog(Component parentComponent, String message, String title, 
			int optionType, int messageType, Icon icon, Object[] options, Object initialValue,
			ILocalizationProvider provider) {
		
		return JOptionPane.showOptionDialog(
				parentComponent, 
				provider.getString(message), 
				provider.getString(title), 
				optionType,
				messageType,
				icon,
				options,
				initialValue);
	}
}
