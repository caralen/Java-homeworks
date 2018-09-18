package hr.fer.zemris.java.hw15.crypto;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Static class that is used for calculating hash function using the SHA-1 algorithm.
 * @author Alen Carin
 *
 */
public class ShaHash {

	/**
	 * Takes one string from user and checks if the given string is equal to the digest
	 * which is generated with the SHA-256 algorithm from the file given in arguments.
	 * @param path to the file for which the digest will be generated.
	 * @throws IOException if the input stream cannot be opened on the given file.
	 * @throws NoSuchAlgorithmException if the name of the message digest algorithm is invalid.
	 */
	public static String calculateSha(String password) throws IOException {

		if(password.equals("")) return password;
		
		byte[] buff = password.getBytes();
		MessageDigest sha = null;
		
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		sha.update(buff, 0, buff.length);
		
		String digest = byteToHex(sha.digest());
		return digest;
	}
	
	/**
	 * Converts array of bytes to String of hex characters.
	 * @param byteArray array of bytes, each byte is a single number.
	 * @return String of hex characters.
	 */
	private static String byteToHex(byte[] byteArray) {
		if(byteArray.length == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();

		for (byte b : byteArray) {
			buffer.append(String.format("%02X", (int) b & 0xff));
		}
		return buffer.toString().toLowerCase();
	}
}
