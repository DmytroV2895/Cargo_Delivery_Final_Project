package com.varukha.webproject.util;

import java.math.BigInteger;
import java.util.Base64;

/**
 * Class Encoder.
 * Encode password
 * @author Dmytro VArukha
 * @version 1.0
 *
 */
public final class Encoder {
	private Encoder() {
	}

	/**
	 * @param password user password to encode
	 * @return String hash value of password
	 */
	public static String encodePassword(String password) {
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] bytesEncoded = encoder.encode(password.getBytes());
		BigInteger bigInt = new BigInteger(1, bytesEncoded);
		return bigInt.toString(16);
	}
}
