package com.club.business.excel.common;

/**
 * 导出枚举类
 *
 * @author Tom
 * @date 2019-12-12
 */
public enum ExportModeEnum {
	
	BIG(1,"大数据导出"),
	MAP(2,"列表导出"),
	SINGLE(3,"注解导出"),
	TEMPLATE(4,"模板导出"),
	SINGLEADD(5,"注解加自定义"),
	SINGLEID(6,"注解加ID");

	/**
	 * 编码类型
	 */
	private int code;
	/**
	 * 描述
	 */
	private String desc;

	ExportModeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
