package com.club.business.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** 
 * 常用方法工具类
 *
 * @author Tom
 * @date 2019-12-12
 * @version 1.0.0
 */
public class StringsUtils {

	/**
	 * 按照参数format的格式,字符串转日期
	 *
	 * @param str 日期字符串
	 * @param format yyyy-MM-dd HH:mm:ss
	 * @return Date
	 * @throws ParseException 
	 */
	public static Date str2Date(String str, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	/**
	 * 从第二个大写字母开始每个大写字母前加上"_"下划线符号
	 *
	 * @param str 需要加下划线的字母字符串
	 * @return
	 */
	public static String upperChar2UnderLine(String str) {
		String replaceAll = str.replaceAll("([A-Z])", "_$1");
		if ('_' == replaceAll.charAt(0)) {
			return replaceAll.replaceFirst("_", "");
		}
		return replaceAll;
	}

	/**
	 * 判断是否是汉字
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		return matcher.find();
	}
	
	/**
	 * 判断是否包含汉字
	 * @param str
	 * @return
	 */
	public static boolean hasChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		return matcher.find();
	}
	
	/**
	 * 数组去重
	 * @param arr
	 * @return
	 */
	public static String[] arrayDistinct(String[] arr) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			if(!list.contains(arr[i])) {
				list.add(arr[i]);
			}
		}
		return list.toArray(new String[1]);
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 *
	 * @param str 判断的日期字符串
	 * @return
	 */
	public static boolean isDate(String str) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(str);
		return m.matches();
			
	}

	/**
	 * 将未指定格式的日期字符串转化成java.util.Date类型日期对象
	 * @param str 待转换的日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringToDate(String str) throws ParseException {
		String parse = str;
		parse = parse.replaceFirst("^[0-9]{4}([^0-9])", "yyyy$1");
		parse = parse.replaceFirst("^[0-9]{2}([^0-9])", "yy$1");
		parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1MM$2");
		parse = parse.replaceFirst("([^0-9])[0-9]{1,2}( ?)", "$1dd$2");
		parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9])", "$1HH$2");
		parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1mm$2");
		parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1ss$2");
		DateFormat format=new SimpleDateFormat(parse);
		return format.parse(str);
	}

	/**
	 * 从List集合中取出重复元素
	 * @param list
	 * @param <E>
	 * @return
	 */
	public static <E> List<E> getDuplicateElements(List<E> list) {
		return list.stream()
				.collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
				.entrySet().stream()
				.filter(entry -> entry.getValue() > 1)
				.map(entry -> entry.getKey())
				.collect(Collectors.toList());
	}

}
