package hr.fer.zemris.java.hw07.crypto;

/**
 * Class which implements static methods for converting string of hex values into bytes and vice versa.
 * @author Alen Carin
 *
 */
public class Util {

	/**
	 * Converts the String of hex characters to an array of bytes where each byte is a number.
	 * @param hex String of hex characters
	 * @return an array of bytes, each byte is a single number.
	 * @throws IllegalArgumentException if the hex String is invalid.
	 */
	public static byte[] hexToByte(String hex) throws IllegalArgumentException {
		char[] hexChars = hex.toCharArray();
		if (!isValidHex(hexChars)) {
			throw new IllegalArgumentException("Invalid hex string");
		}
		int byteCounter = 0;
		byte[] byteArray = new byte[hexChars.length / 2];

		for (int i = 0; i < hexChars.length; i += 2) {
			byteArray[byteCounter++] = charsToByte(hexChars[i], hexChars[i + 1]);
		}
		
		return byteArray;
	}

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
			buffer.append(String.format("%02X", (int) b & 0xff));
		}
		return buffer.toString().toLowerCase();
	}

	/**
	 * Converts two characters representing hex numbers into a single byte in big-endian notation.
	 * @param c1 first character.
	 * @param c2 second character
	 * @return a single byte created from two hex numbers in big-endian notation.
	 */
	private static byte charsToByte(char c1, char c2) {
		return (byte) ((Character.digit(c1, 16) << 4) + (Character.digit(c2, 16)));
	}
	

	/**
	 * Checks if characters are valid hex number characters.
	 * @param hexChars array of characters.
	 * @return true if all the characters are valid hex numbers, false otherwise.
	 */
	private static boolean isValidHex(char[] hexChars) {
		if (hexChars.length % 2 != 0)
			return false;
		
		for (int i = 0; i < hexChars.length; i++) {
			if (hexChars[i] < '0' || hexChars[i] > '9' && hexChars[i] < 'A' 
					|| hexChars[i] > 'F' && hexChars[i] < 'a'
					|| hexChars[i] > 'f') {
				return false;
			}
		}
		return true;
	}
}
