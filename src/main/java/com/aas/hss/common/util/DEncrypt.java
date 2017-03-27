package com.aas.hss.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DEncrypt {

	String key = "";

	public static byte[] md5(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(data);
			return md.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	public static String md5(String data) {
		try {
			byte[] md5 = md5(data.getBytes());
			return toHexString(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String();
	}

	private static String toHexString(byte[] md5) {
		StringBuilder sb = new StringBuilder();
		byte[] arrayOfByte = md5;
		int j = md5.length;
		for (int i = 0; i < j; i++) {
			byte b = arrayOfByte[i];
			sb.append(leftPad(Integer.toHexString(b & 0xFF), '0', 2));
		}

		return sb.toString();
	}

	private static String leftPad(String str, char ch, int length) {
		char[] chs = new char[length];
		Arrays.fill(chs, ch);
		char[] src = str.toCharArray();
		System.arraycopy(src, 0, chs, length - src.length, src.length);
		return new String(chs);
	}

	@SuppressWarnings("unused")
	private byte[] encoder(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(this.key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(this.key.getBytes("UTF-8"));
		cipher.init(1, secretKey, iv);
		return cipher.doFinal(message.getBytes("UTF-8"));
	}

	@SuppressWarnings("unused")
	private byte[] decoder(byte[] src) throws Exception {
		IvParameterSpec iv = new IvParameterSpec(this.key.getBytes("UTF-8"));
		DESKeySpec dks = new DESKeySpec(this.key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(2, securekey, iv);
		return cipher.doFinal(src);
	}

	public static void main(String[] args) {
	}

	public static String newMD5(String inputStr,String ecodingType) throws UnsupportedEncodingException {
		String md5Str = inputStr;
		if (inputStr != null) {
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
				md.update(inputStr.getBytes(ecodingType));
				BigInteger hash = new BigInteger(1, md.digest());
				md5Str = hash.toString(16);
				if ((md5Str.length() % 2) != 0) {
					md5Str = "0" + md5Str;
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return md5Str;
	}
}
