package com.club.business.common.annotation;

import com.club.business.sys.service.SysOperLogServiceImpl;
import com.club.business.sys.vo.SysOperLog;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.BaseConstant;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志切点类
 * @author Tom
 * @since 2019-12-12
 * @version 1.0
 * @source http://blog.csdn.net/czmchen/article/details/42392985
 */
@Aspect
@Component
public class LogAspect implements Ordered {

	/**本地异常日志记录对象*/
	private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Resource
	private SysOperLogServiceImpl operLogService;

	/**Controller层切点*/
	@Pointcut("@annotation(com.club.business.common.annotation.UserLog)")
	public void controllerAspect() {}
		
	/**
	 * 实现org.springframework.core.Ordered接口来定义order的顺序，order的值越小，越先被执行。 https://my.oschina.net/HuifengWang/blog/304188
	 */
	@Override
	public int getOrder() {
		return 1;
	}
	
	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * @param point
	 * @throws Throwable 
	 */
	@Around("controllerAspect()")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		Object result = null;
		/**获取请求IP*/
		String IP = "";
		if (request.getHeader("x-forwarded-for") == null) {
			IP = request.getRemoteAddr();  
	    }else{
	    	IP = request.getHeader("x-forwarded-for");  
	    }
		if("0:0:0:0:0:0:0:1".equals(IP)){
			IP = "127.0.0.1";
		}
		String methodDescription = "";
		try {
			result = point.proceed(point.getArgs());
			HashMap resultMap = new HashMap();
			if(result instanceof Map){
				resultMap = (HashMap)result;
			}
			methodDescription = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(UserLog.class).description();
			String[] description = methodDescription.split("#");
			//保存数据库
			SysOperLog log = new SysOperLog();
			if(StringUtils.isNotBlank((String)(resultMap.get("msg")))){
				if(resultMap.get("code").toString().equals("0")){
					log.setStatus(0);
				}else{
					log.setStatus(-1);
					log.setMemo((String)(resultMap.get("msg")));
				}
			}else{
				log.setStatus(0);
			}
			log.setMenuName(description[0]);
			log.setType(description[1]);
			log.setTypeName(description[2]);
			SysUser user = new SysUser();
			if("00".equals(description[1])){//如果是登陆的情况
				if(StringUtils.isBlank((String)(resultMap.get("msg")))){
					//读取session中的用户
					user = (SysUser)session.getAttribute(BaseConstant.SESSION_USER);
					if(user == null){
						return result;
					}
				}else{
					log.setUserAccount((request.getParameter("account")));
				}
			}else{
				//读取session中的用户
				user = (SysUser)session.getAttribute(BaseConstant.SESSION_USER);
				if(user == null){
					return result;
				}
			}
			if(user.getUserId() != null){
				log.setUserId(user.getUserId());
			}
			if(StringUtils.isNotBlank(user.getUserName())){
				log.setUserName(user.getUserName());
			}
			if(StringUtils.isBlank(log.getUserAccount())){
				log.setUserAccount(user.getUserCode());
			}
			log.setOperIp(IP);
			log.setOperTime(LocalDateTime.now());
			operLogService.save(log);
		} catch (Exception e) {
			/**记录本地异常日志*/
			e.printStackTrace();
			logger.info("异常信息:{"+e.getMessage()+"}");
    	}
		return result;
	}
}