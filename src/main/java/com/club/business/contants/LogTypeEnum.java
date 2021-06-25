package com.club.business.contants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统日志类型枚举类(暂未使用)
 *
 * @author Tom
 * @date 2019-12-12
 */
public enum LogTypeEnum {

	LOG_LOGIN("00","登录","logLogin"),
	LOG_SEARCH("01","查询","logSearch"),
	LOG_ADD("02","新增","logAdd"),
	LOG_UPDATE("03","修改","logUpdate"),
	LOG_DELETE("04","停用","logDelete"),
	LOG_RESUME("05","启用","logResume"),
	LOG_VIEW("06","查看","logView"),
	LOG_REMOVE("07","删除","logRemove"),
	LOG_OTHERS("99","其他","logOthers")
	;

	/**
	 * 操作类型
	 */
	private String type;

	/**
	 * 操作类型名称
	 */
	private String name;

	/**
	 * 操作类型编码
	 */
	private String code;

	LogTypeEnum(String type, String name, String code){
		this.type = type;
		this.name = name;
		this.code = code;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCode() {
		return this.code;
	}
	
	/**
	 * 获取 type,name 键值对集合
	 *
	 * @author Tom
	 * @return
	 */
	public static List<Map<String,Object>> getList() {
		List<Map<String,Object>> list = new ArrayList<>();
		for (LogTypeEnum info : LogTypeEnum.values()) {
			Map<String,Object> map = new HashMap<>();
			map.put("type", info.getType());
			map.put("name", info.getName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据type获取枚举类参数
	 *
	 * @author Tom
	 * @param type 操作类型
	 * @return
	 */
	public static LogTypeEnum getByType(String type) {
		for (LogTypeEnum info : LogTypeEnum.values()) {
			if (info.getType().equals(type)) {
				return info;
			}
		}
		return null;
	} 
}
