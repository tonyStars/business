package com.club.business.filter;

import com.club.business.util.BaseConstant;
import com.club.business.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 拦截控制器
 *
 * @author Tom
 * @date 2019-11-16
 */
public class AuthCommonFilter extends HandlerInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(AuthCommonFilter.class);
	/**获取的配置文件*/
	private Properties config;
	/**不拦截的请求接口URL集合*/
	private List<String> ignorurls = new ArrayList<>();
	/**不拦截的请求URL地址集合*/
	private List<String> ignorPath = new ArrayList<>();
	/**项目登录页*/
	private String loginPage;
	/**是否使用标识*/
	private String isUsed = "false";

	private static String FALSE = "false";
	private static String MASSAGE = "msg";
	private final static String CHARSET = "UTF-8";

	public AuthCommonFilter() {
		init();
	}

	/**
	 * 拦截器初始化方法,获取不拦截数据
	 */
	public void init() {
		this.config = PropertiesUtil.getProperties("config/auth.properties");
		if (this.config == null) {
			return;
		}
		/**获取登录页*/
		loginPage = config.getProperty("loginPage");
		/**获取不拦截的请求接口URL*/
		String ignorUrl = config.getProperty("ignorUrl");
		/**获取不拦截的请求URL地址*/
		String ignorPath = config.getProperty("ignorPath");
		/**获取配置是否使用标识*/
		isUsed = config.getProperty("isUsed");
		/**
		 * 遍历配置文件中不拦截的请求接口URL,放入集合
		 */
		if (ignorUrl != null && !ignorUrl.equals("")) {
			String[] iiurl = ignorUrl.split(",");
			for (String s : iiurl) {
				this.ignorurls.add(s.trim());
			}
		}
		/**
		 * 遍历配置文件中不拦截的请求URL地址,放入集合
		 */
		if (ignorPath != null && !ignorPath.equals("")) {
			String[] iPath = ignorPath.split(",");
			for (String s : iPath) {
				this.ignorPath.add(s.trim());
			}
		}
	}

	/**
	 * 拦截器执行
	 * @param req
	 * @param res
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)throws Exception {
		try {
			if (FALSE.equals(this.isUsed)) {
				return true;
			} else if (isIgnorUrl(req)) {
				return true;
			} else if (checkAuth(req)) {
				return true;
			} else {
				log.error(" 拒绝访问！请求的URL 是" + req.getRequestURI());
				if(req.getSession().getAttribute(MASSAGE) != null){
					res.sendRedirect(req.getContextPath() + loginPage +"?msg="+ URLEncoder.encode(req.getSession().getAttribute(MASSAGE).toString(),CHARSET));
				}else{
					res.sendRedirect(req.getContextPath() + loginPage);
				}
				return false;
			}
		} catch (Exception e) {
			log.error("访问如下地址导致拦截器出错："+req.getRequestURI(), e);
			throw e;
		}
	}

	/**
	 * 判断是否是忽略的路径
	 * 
	 * @param request
	 * @return
	 */
	public boolean isIgnorUrl(HttpServletRequest request) {
		boolean flag = false;
		String rUrl = request.getRequestURI();
		String cpath = request.getContextPath();
		if (rUrl.indexOf("?") > 0) {
			rUrl = rUrl.split("\\?")[0];
		}
		/**静态文件不过滤*/
		if (!rUrl.endsWith(".jsp") && !rUrl.endsWith(".do")) {
			return true;
		}
		for (String s : ignorurls) {
			if (s != "" && !s.equals("") && rUrl.equalsIgnoreCase(cpath + s)) {
				flag = true;
				break;
			}
		}
		for (String s : ignorPath) {
			if (!s.equals("") && rUrl.startsWith(cpath + s)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 判断session中的用户是否存在(存在不拦截,不存在则拦截)
	 * @param request
	 * @return
	 */
	private boolean checkAuth(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(BaseConstant.SESSION_USER) == null) {
			log.error("the user session is null");
			return false;
		}
		return true;
	}

}
