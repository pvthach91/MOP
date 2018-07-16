package com.brian.service;

import java.security.*;

public class MD5 {
	public static void main(String[] args) {
		System.out.println(MD5.Encrypt("1234567"));
		//System.out.println(MD5.Encrypt("1234567"));
	}

	public static String Encrypt(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			//32位加密
			return buf.toString();
			// 16位的加密
			//System.out.println("result: " + buf.toString().substring(8, 24));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	// 加密
	public static String Encrypt(String str) {
		String s = str;
		if (s == null) {
			return "";
		} else {
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				Logger.getLogger(MD5.class.getName()).log(Level.SEVERE, null,
						ex);
			}
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			try {
				value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return value;
		}
	}
	*/
}
