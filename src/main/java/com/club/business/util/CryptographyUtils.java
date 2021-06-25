package com.club.business.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * 
 * @Description: 加密工具类
 * @author Tom
 * @date 2019-12-04
 *
 */
public class CryptographyUtils {

	/**
	 * Base64加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encBase64(String str) {
		try {
			byte[] bytes = str.getBytes("UTF-8");
			return Base64.getEncoder().encodeToString(bytes);
		} catch (UnsupportedEncodingException e) {
			// do nothing
		}
		return "";
	}

	/**
	 * Base64解密
	 * 
	 * @param str
	 * @return
	 */
	public static String decBase64(String str) {
		try {
			byte[] decode = Base64.getDecoder().decode(str);
			return new String(decode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// do nothing
		}
		return "";
	}

	/**
	 * Md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encMd5(String str) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("UTF-8"));
			byte[] result = md5.digest(); 
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < result.length; i++) { 
				int tmp = result[i]; 
				if (tmp < 0) {
					tmp += 256; 
				}
				if (tmp < 16) {
					sb.append("0"); 
				}
				sb.append(Integer.toHexString(tmp)); 
			}
			return sb.toString();
		} catch (Exception e) {
			//do nothing
		}
		return null;
	}

	/**
	 * Md5盐值加密
	 * @param str 待加密字符串(用于密码加密该字段为密码)
	 * @param salt 盐值(用于密码加密该字段为用户名)
	 * @return
	 */
	public static String encMd5(String str, String salt) { // 加盐提高加密复杂度(盐字符串应妥善保管)
		return encMd5(salt + str);
	}
	
}
