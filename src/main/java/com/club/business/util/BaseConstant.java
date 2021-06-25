package com.club.business.util;

/**
 * 基础常量类
 * @author Tom
 * @date 2019-11-16
 * @version 1.0.0
 */
public class BaseConstant {

	/**************************************session参数配置**********************************************/
	/**
	 * session中的用户
	 */
	public static final String SESSION_USER = "sessionUser";

	/**
	 * session中的菜单
	 */
	public static final String SESSION_MENU = "sessionMenu";

	/**
	 * session中的按钮
	 */
	public static final String SESSION_BUTTON = "sessionButton";

	/**************************************session参数配置**********************************************/
	/**************************************系统日志类型*************************************************/
	/**
	 * 登录
	 */
	public static final String LOG_LOGIN = "00#登录";
	/**
	 * 查询
	 */
	public static final String LOG_SEARCH = "01#查询";
	/**
	 * 新增
	 */
	public static final String LOG_ADD = "02#新增";
	/**
	 * 修改
	 */
	public static final String LOG_UPDATE = "03#修改";
	/**
	 * 停用
	 */
	public static final String LOG_DELETE = "04#停用";
	/**
	 * 启用
	 */
	public static final String LOG_RESUME = "05#启用";
	/**
	 * 查看
	 */
	public static final String LOG_VIEW = "06#查看";
	/**
	 * 删除
	 */
	public static final String LOG_REMOVE = "07#删除";
	/**
	 * 排序
	 */
	public static final String LOG_SORT = "08#排序";
	/**
	 * 抽奖
	 */
	public static final String LOG_LUCK = "09#抽奖";
	/**
	 * 其他
	 */
	public static final String LOG_OTHERS = "99#其他";

	/**************************************系统日志类型*************************************************/
	/**************************************BaseUtils中request2Model操作类型*****************************/
	/**
	 * 新增
	 */
	public static final String OPER_ADD = "01";
	/**
	 * 修改
	 */
	public static final String OPER_UPDATE = "02";
	/**************************************BaseUtils中request2Model操作类型*****************************/

}
