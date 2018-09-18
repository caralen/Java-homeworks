package hr.fer.zemris.java.hw07.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which implements static methods for converting string of hex values into bytes and vice versa.
 * @author Alen Carin
 *
 */
public class Util {

	
	/**
	 * Converts array of bytes to String of hex characters.
	 * @param byteArray array of bytes, each byte is a single number.
	 * @return String of hex characters.
	 */
	public static String byteToHex(byte[] byteArray) {
		if(byteArray.length == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();

		for (byte b : byteArray) {
			buffer.append(String.format("%02X ", (int) b & 0xff));
		}
		return buffer.toString().toUpperCase();
	}
	
	/**
	 * Converts an array of bytes byte to char array.
	 *
	 * @param byteArray the byte array which will be converted
	 * @return the produced char array
	 */
	public static char[] byteToCharArray(byte[] byteArray) {
		String text = new String(byteArray);
		return text.toCharArray();
	}
	
	/**
	 * Replaces unsupported characters in the char array.
	 * Unsupported characters are replaced with a dot character.
	 * Unsupported characters are those with values less than 32 and greater than 127
	 *
	 * @param charArray the array of characters which can contain unsupported characters
	 * @return the char array containing only supported characters
	 */
	public static char[] replaceUnsupported(char[] charArray) {
		for(int i = 0; i < charArray.length; i++) {
			if(charArray[i] < 32 || charArray[i] > 127) {
				charArray[i] = '.';
			}
		}
		return charArray;
	}

	
	/**
	 * Split the given arguments string using the following rules:
	 * <li> arguments are divided by a single or more spaces.
	 * <li> if there are quotation marks everything inside is a single argument.
	 * <li> after the closing quotation mark there must be at least one space.
	 * <li> inside quotation marks quotation sign escaped with the backslash is not treated as the quotation end
	 * <li> inside quotation marks backslash sign can be escaped
	 *
	 * @param arguments the given string arguments
	 * @return the string array containing arguments
	 */
	public static String[] splitArguments(String arguments, Environment env) {
		
		arguments = arguments.replaceAll("\\\"", "");

		if(Pattern.matches("[^\\\"]*[\"][^\"]*[\"]\\S*", arguments)) {
			env.writeln("There must be a space after the ending quotation mark");
			return new String[0];
		}
		
		List<String> argumentsList = new ArrayList<String>();
		Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(arguments);
		while (matcher.find()) {
			argumentsList.add(matcher.group(1).replaceAll("\"", "")); 
		}
		
		String[] argumentsArray = new String[argumentsList.size()];
		
		int counter = 0;
		for(String s : argumentsList) {
			s = s.replace("\"", "");
			argumentsArray[counter++] = s;
		}
		
		return argumentsArray;
	}
}
