package com.club.business.config;

import com.club.business.util.PropertiesUtil;

/**
 * 登陆配置文件
 *
 * @author Tom
 * @date 2019-11-16
 */
public class AuthConfig {

    /**
     * 配置是否使用标识
     */
    public static String IS_USED = PropertiesUtil.getValue("config/auth.properties","isUsed");
    

    /**
     * 不拦截的请求接口URL
     */
    public static String IGNOR_URL = PropertiesUtil.getValue("config/auth.properties","ignorUrl");

    /**
     * 不拦截的请求URL地址
     */
    public static String IGNOR_PATH = PropertiesUtil.getValue("config/auth.properties","ignorPath");
    
    /**
     * 登录页
     */
    public static String LOGIN_PAGE = PropertiesUtil.getValue("config/auth.properties","loginPage");
    
}
