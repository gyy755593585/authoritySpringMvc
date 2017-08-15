/**
 *
 */
package com.wuji.authority.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author Yayun
 *
 */
public class SecurityUtil {

	public static String md5(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}

	public static String md5(String salt, String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(salt.getBytes());
		md.update(password.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}

	public static String getSalt() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
