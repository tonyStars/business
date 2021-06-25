package com.club.business.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 通过spring的applicationContext获取对象
 *
 * @author Tom
 * @date 2019-12-12
 * 
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
		
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	 /** 
     * 通过名称获取对象 
     * @param name 对象名称
     * @return Object
     * @throws BeansException
     */  
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);  
    }

	/**
	 * 通过class获取对象
	 * @param clazz
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(Class<?> clazz) throws BeansException {
		return applicationContext.getBean(clazz);
	}

}
