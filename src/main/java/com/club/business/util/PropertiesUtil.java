package com.club.business.util;

import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 获取配置文件工具类
 *
 * @author Tom
 * @date 2019-11-16
 */
public class PropertiesUtil {

    /**
     * 根据配置文件名,获取配置文件
     * @param propertiesName 配置文件名
     * @return
     */
	public static Properties getProperties(String propertiesName) {
		Properties prop = new Properties();
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
		try {
			prop.load(in);
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * 根据配置文件名称和配置文件中的key值获取对应的value
     * @param propertiesName 配置文件名称
     * @param key 配置文件中对应的key
     * @return
     */
	public static String getValue(String propertiesName, String key) {
		Properties prop = new Properties();
		try {
            //boot项目加载文件方法
            InputStream in = new ClassPathResource(propertiesName).getInputStream();
			prop.load(in);
			return prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过key得到 properties文件中的值
	 * @param key properties文件中的key值
	 * @param resource properties文件,如：  路径/xxx  ; 不要后缀名
	 * @return properties文件key所对应的值
	 */
	public static String getByKey(String key, String resource) {
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rb.getString(key);
	}
}
