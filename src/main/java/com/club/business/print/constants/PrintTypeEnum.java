package com.club.business.print.constants;

import com.club.business.print.service.impl.*;

/**
 * 打印单据枚举类
 *
 * @author Tom
 * @date 2019-12-16
 */
public enum PrintTypeEnum {
	/**
	 * 付款通知单
	 */
	SYSCOMPANY(1, CompanyPrintService.class);

	/**
	 * 类型标识
	 */
	private int index;

	/**
	 * 服务类<根据指定枚举获取对应的服务类>
	 */
	private Class<?> clazz;
	
	PrintTypeEnum(int index, Class<?> clazz){
		this.index = index;
		this.clazz = clazz;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * 根据类型标识获取对应的枚举
	 * @param index 类型标识
	 * @return
	 */
	public static PrintTypeEnum getByIndex(int index) {
		for (PrintTypeEnum info : PrintTypeEnum.values()) {
			if (info.getIndex() == index) {
				return info;
			}
		}
		return null;
	} 
}
