package com.club.business.util;

import com.club.business.sys.vo.SysUser;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Enumeration;

/** 
 * 常用方法工具类
 *
 * @author Tom
 * @date 2019-12-12
 * @version 1.0.0
 */
public class BaseUtils {

	/**
	 * 将request参数转化为model
	 * @param model 需要转化的对象
	 * @param request HttpServletRequest
	 * @param operType 操作类型从BaseConstant配置常量中取,无则写null
	 * @throws Exception
	 */
	public static void request2Model(Object model, HttpServletRequest request, String operType) throws Exception {
		/**获得参数的一个列举*/
		Enumeration<String> en = request.getParameterNames();
		SysUser user = (SysUser) request.getSession().getAttribute(BaseConstant.SESSION_USER);
		if (user != null) {
			if (BaseConstant.OPER_ADD.equals(operType)) {
				BeanUtils.setProperty(model, "entryUserId", user.getUserId());
				BeanUtils.setProperty(model, "entryUserName", user.getUserName().trim());
				BeanUtils.setProperty(model, "entryUserTime", LocalDateTime.now());
			} else if (BaseConstant.OPER_UPDATE.equals(operType)) {
				BeanUtils.setProperty(model, "updateUserId", user.getUserId());
				BeanUtils.setProperty(model, "updateUserName", user.getUserName().trim());
				BeanUtils.setProperty(model, "updateUserTime", LocalDateTime.now());
			}
		}
		/**遍历列举来获取所有的参数*/
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			Object value = request.getParameter(name);
			if (!StrUtils.isEmpty(value)) {
				if(value instanceof String){
					value = value.toString().trim();
				}
				BeanUtils.setProperty(model, name, value);
			}
		}
	}

	/**
	 * URL UTF-8 转码
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeUTF8(String str) {
		try {
			if (StringUtils.isBlank(str)) {
				return "";
			}
			if (str.equals(new String(str.getBytes("ISO8859-1"), "ISO8859-1"))) {
				return new String(str.toString().getBytes("ISO8859-1"), "UTF-8").trim();
			} else {
				return str;
			}
		} catch (UnsupportedEncodingException e) {
			
		}
		return "";
	}

	/**
	 * 获取请求者ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr2(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String proxs[] = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP","HTTP_X_FORWARDED_FOR" };
		String ip = null;
		for (String prox : proxs) {
			ip = request.getHeader(prox);
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				continue;
			} else {
				break;
			}
		}
		if (StringUtils.isBlank(ip)) {
			ip= request.getRemoteAddr();
			if ("0:0:0:0:0:0:0:1".equals(ip)) {
				ip = "127.0.0.1";
			}
		}
		return ip;
	}
	
}