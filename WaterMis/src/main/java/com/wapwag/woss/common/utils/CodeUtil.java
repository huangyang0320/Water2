package com.wapwag.woss.common.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.wapwag.woss.common.task.AccessRecordTask;

public class CodeUtil {
	/**
	 * 将摘要信息转换为相应的编码
	 * 
	 * @param code
	 *            编码类型
	 * @param message
	 *            摘要信息
	 * @return 相应的编码字符串
	 */
	public static String Encode(String code, String message) {
		message = getBase(message);
		MessageDigest md;
		String encode = null;
		try {
			md = MessageDigest.getInstance(code);
			encode = bytes2Hex(md.digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encode;
	}

	private static String getBase(String str) {
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			str = new String(decoder.decodeBuffer(str));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	
	
	public static void main(String[] args) {
		String pwd = "QWJjMTIzKys=";
		//pwd = pwd.toLowerCase();
		System.out.println(pwd);
		System.out.println(CodeUtil.Encode("SHA-256",pwd));
	}

}
