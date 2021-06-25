package com.club.business.util;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * 字符串处理工具
 *
 * @author Tom
 * @date 2019-12-12
 */
public class StrUtils {
	
	/**
	 * 判断某个对象是否为空 集合类、数组做特殊处理
	 * 
	 * @param obj
	 * @return 如为空，返回true,否则false
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		/**如果不为null，需要处理几种特殊对象类型*/
		if (obj instanceof String) {
			return obj.equals("");
		} else if (obj instanceof Collection) {
			/**对象为集合*/
			Collection coll = (Collection) obj;
			return coll.size() == 0;
		} else if (obj instanceof Map) {
			/**对象为Map*/
			Map map = (Map) obj;
			return map.size() == 0;
		} else if (obj.getClass().isArray()) {
			/**对象为数组*/
			return Array.getLength(obj) == 0;
		} else {
			/**其他类型，只要不为null，即不为empty*/
			return false;
		}
	}
	
    /**
     * 读取html中所有的标签内容
	 * @param filePath 文件路径 
	 * @return 获得html的全部内容 
	 */ 
	public static String readHtml(String filePath) {
		StringBuffer sb = new StringBuffer();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));){
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 将字符串数组转换为Integer数组
	 * @param strArr
	 * @return
	 */
    public static Integer[] stringArr2IntegerArr(String[] strArr) {
    	int length = strArr.length;
    	Integer[] intArr = new Integer[length];
		for (int i = 0; i < length; i++) {
			intArr[i] = Integer.parseInt(strArr[i]);
		}
		return intArr;
	}

	/**
	 * 生成随机字符串(数字)
	 * @param length 生成字符串的长度
	 * @return
	 */
	public static String getRandomNum(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
    
}
