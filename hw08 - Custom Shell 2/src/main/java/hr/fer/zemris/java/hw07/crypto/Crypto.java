package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class which holds the program that is designed to implement three functions:
 * <ul>
 * 	<li> check if the given String is equal to the digest of the given file
 * 	<li> generate an encrypted file based on the given file
 * 	<li> generate a decrypted file based on the given file
 * </ul>
 * @author Alen Carin
 *
 */
public class Crypto {
	
	/**String name for the encrypt function.*/
	private static String ENCRYPT = "encrypt";
	
	/**String name for the decrypt function.*/
	private static String DECRYPT = "decrypt";
	
	/**Number of bytes which fit in four kilobytes.*/
	private static int FOUR_KILOBYTES = 4096;
	
	/**Prefix which should be added at the start of the path to point to the current directory*/
	private static String CURRENT_FOLDER_IDENTIFIER = "./";

	/**
	 * Starting method of the program.
	 * @param args command line arguments, should contain three or four arguments.
	 * 		first argument is a function name, others are paths to some files.
	 */
	public static void main(String[] args) {
		
		try {
			if (args.length == 2) {
				if (args[0].equals("checksha")) {
					Path path = Paths.get(CURRENT_FOLDER_IDENTIFIER + args[1]);
					checkSha(path);
				} else {
					System.out.println("Invalid function name");
					System.exit(-1);
				}
			} else if (args.length == 3) {
				Path pathSrc = Paths.get(CURRENT_FOLDER_IDENTIFIER + args[1]);
				Path pathDest = Paths.get(CURRENT_FOLDER_IDENTIFIER + args[2]);
				
				if (args[0].equals(ENCRYPT)) {
					crypter(ENCRYPT, pathSrc, pathDest);
				} else if (args[0].equals(DECRYPT)) {
					crypter(DECRYPT, pathSrc, pathDest);
				} else {
					System.out.println("Invalid function name");
					System.exit(-1);
				}
			} else {
				System.out.println("Invalid number of arguments");
				System.exit(-1);
			}

		} catch (IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}


	/**
	 * Takes one string from user and checks if the given string is equal to the digest
	 * which is generated with the SHA-256 algorithm from the file given in arguments.
	 * @param path to the file for which the digest will be generated.
	 * @throws IOException if the input stream cannot be opened on the given file.
	 * @throws NoSuchAlgorithmException if the name of the message digest algorithm is invalid.
	 */
	private static void checkSha(Path path) throws IOException, NoSuchAlgorithmException {
		Scanner sc = new Scanner(System.in);
		System.out.format("Please provide expected sha-256 digest for %s\n", path.toString().substring(2));
		System.out.format("> ");
		String entry = sc.nextLine();
		sc.close();
		
		try(InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buff = new byte[FOUR_KILOBYTES];
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			while(true) {
				int r = is.read(buff);
				if(r < 1) break;
				
				sha.update(buff, 0, r);
			}
			String digest = Util.byteToHex(sha.digest());
			
			if(digest.equals(entry)) {
				System.out.format("Digesting completed. Digest of %s matches expected digest.\n"
						, path.toString().substring(2));
			}else {
				System.out.format("Digesting completed. "
						+ "Digest of %s does not match the expected digest. Digest was: %s\n"
						, path.toString().substring(2), digest);
			}
		}
	}
	
	/**
	 * Method used for generating an encrypted or decrypted file based on the given file.
	 * @param function String value of the name of the function which will be executed.
	 * @param inputPath path to the file based on which the new file will be generated.
	 * @param outputPath path where the new crypted file should be generated.
	 * @throws IOException if the input or the output stream cannot be opened on the given file.
	 * @throws InvalidKeyException if the key value is invalid.
	 * @throws InvalidAlgorithmParameterException if the algorithm parameter is invalid.
	 * @throws NoSuchAlgorithmException if the name of the message digest algorithm is invalid.
	 * @throws NoSuchPaddingException if there is no such padding at the end of the file.
	 * @throws IllegalBlockSizeException if the block size used in update and doFinal methods is illegal.
	 * @throws BadPaddingException if the padding at the end of the file is invalid.
	 */
	private static void crypter(String function, Path inputPath, Path outputPath)
			throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.format("> ");
		String keyText = sc.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.format("> ");
		String ivText = sc.nextLine();
		sc.close();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(function.equals(ENCRYPT) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		try (InputStream is = new BufferedInputStream(Files.newInputStream(inputPath));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputPath))) {

			byte[] buff = new byte[FOUR_KILOBYTES];

			while (true) {
				int r = is.read(buff);
				if (r < 0) {
					break;
				} else if (r < FOUR_KILOBYTES) {
					byte[] finalBytes = cipher.doFinal(buff, 0, r);
					os.write(finalBytes, 0, finalBytes.length);
					break;
				}
				byte[] updatedBytes = cipher.update(buff);
				os.write(updatedBytes, 0, updatedBytes.length);
			}

			System.out.format("%s completed. Generated file %s based on file %s.",
					function.equals(ENCRYPT) ? "Encryption" : "Decryption", 
					outputPath.toString().substring(2),
					inputPath.toString().substring(2));
		}
	}
}
