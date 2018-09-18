package hr.fer.zemris.java.hw06.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.hw07.crypto.Util;

/**
 * Class used for testing the functionality of the methods from the Util class.
 * @author Alen Carin
 *
 */
public class UtilTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testHexToByte1() {
		Util.hexToByte("0");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testHexToByte2() {
		Util.hexToByte("284GR0");
	}

	@Test
	public void testHexToByte3() {
		byte[] array = Util.hexToByte("01aE22");
		
		assertEquals(array[0], 1);
		assertEquals(array[1], -82);
		assertEquals(array[2], 34);
	}
	
	@Test
	public void testHexToByte4() {
		byte[] array = Util.hexToByte("e52217e3ee213ef1ffdee3a192e2ac7e");
		
		assertEquals(array.length, 16);
	}
	
	@Test
	public void testByteToHex1() {
		String hex = Util.byteToHex(new byte[] {});
		
		assertEquals(hex, "");
	}
	
	@Test
	public void testByteToHex2() {
		String hex = Util.byteToHex(new byte[] {1, -82, 34});
		
		assertEquals(hex, "01ae22");
	}
	
	@Test
	public void testByteToHex3() {
		String hex = Util.byteToHex(new byte[] {127});
		
		assertEquals(hex, "7f");
	}
	
	@Test
	public void testByteToHex4() {
		String hex = Util.byteToHex(new byte[] {-128});
		
		assertEquals(hex, "80");
	}
}
