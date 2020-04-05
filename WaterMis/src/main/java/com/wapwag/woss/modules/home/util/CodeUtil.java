package com.wapwag.woss.modules.home.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class CodeUtil {
	
	private static final String CODE = "SHA-256";
	/**
	 * 将摘要信息转换为相应的编码
	 * 
	 * @param code
	 *            编码类型
	 * @param message
	 *            摘要信息
	 * @return 相应的编码字符串
	 */
	public static String Encode(String pwd) {
		pwd = base64(pwd);
		pwd = getBase(pwd);
		MessageDigest md;
		String encode = null;
		try {
			md = MessageDigest.getInstance(CODE);
			encode = bytes2Hex(md.digest(pwd.getBytes()));
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

	private static String bytes2Hex(byte[] bts) {
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
	
	private static String base64(String pwd){
        try{  
            return new String(Base64.encodeBase64(pwd.getBytes("UTF-8")));  
        } catch(UnsupportedEncodingException e){  
        	return "";
        } 
	}	
	

}
