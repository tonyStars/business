package com.club.business.common;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.club.business.contants.ExceptionIconEnum;
import com.club.business.util.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常
 *
 * @author Tom
 * @date 2019-11-19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 异常处理方法
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	private ModelAndView errorResult(Exception ex, HttpServletRequest request) {
		if (isAjax(request)) {
			String message = null;
			if (ex instanceof BusinessException) {
				message = ex.getMessage();
				BusinessException myEx = (BusinessException)ex;
				ExceptionIconEnum iconEnum = myEx.getIconEnum();
				if(iconEnum!=null){
					return jsonResult(message,iconEnum.getIconType());
				}else{
					return jsonResult(message);
				}
			} else {
				log.error(ex.getMessage(), ex);
				message = "请求异常，请联系管理员！";
				return jsonResult(message);
			}
		} else {
			log.error(ex.getMessage(), ex);
			return normalResult("error");
		}
	}

	/**
	 * 以json形式返回异常信息
	 * @param message 异常信息
	 * @param iconType 异常提示图标
	 * @return
	 */
	private ModelAndView jsonResult(String message, int iconType) {
		ModelAndView mv = new ModelAndView();
		FastJsonJsonView view = new FastJsonJsonView();
		Map<String,Object> map = new HashMap<>(3);
		map.put("code", 1);
		map.put("msg", message);
		map.put("icon",iconType);
		view.setAttributesMap(map);
		mv.setView(view);
		return mv;
	}

	/**
     * 返回错误页
     *
     * @param url 错误页url
     * @return 模型视图对象
     */
    private ModelAndView normalResult(String url) {
        return new ModelAndView(url);
    }
    
    /**
     * 返回错误数据(json)
     *
     * @param message 错误信息
     * @return 模型视图对象
     */
    private ModelAndView jsonResult(String message) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String,Object> map = new HashMap<>(2);
        map.put("code", "1");
        map.put("msg", message);
        view.setAttributesMap(map);
        mv.setView(view);
        return mv;
    }
	
	 /**
     * 判断是否ajax请求
     * @param request 请求对象
     * @return true:ajax请求  false:非ajax请求
     */
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
	
}
