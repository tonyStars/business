package com.club.business.config;

import com.club.business.util.PropertiesUtil;

/**
 * activeMQ - Config
 *
 * @author Tom
 * @date 2019-11-16
 */
public class ActiveMqConfig {

    /**
     * ACTIVEMQ url
     */
    public static String ACTIVEMQ_URL = PropertiesUtil.getValue("config/activmq.properties","activemqUrl");

    /**
     * ACTIVEMQ 用户名
     */
    public static String ACTIVEMQ_USER = PropertiesUtil.getValue("config/activmq.properties","activemqUser");

    /**
     * ACTIVEMQ 密码
     */
    public static String ACTIVEMQ_PASSWORD = PropertiesUtil.getValue("config/activmq.properties","activemqPassword");

}
