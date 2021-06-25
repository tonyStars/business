package com.club.business.util;

import com.club.business.sys.vo.SysPrivilege;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * 自定义jsp标签(用户控制权限按钮显示)
 *
 * @author Tom
 * @date 2019-11-20
 * 
 */
public class ButtonAuthorTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 权限对应的code
	 */
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int doStartTag() throws JspException {
		boolean result = false;
		if (code == null || code.equals("")) {
			return SKIP_BODY;
		}
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		List<SysPrivilege> buttonPermissionList = (List<SysPrivilege>) request.getSession().getAttribute(BaseConstant.SESSION_BUTTON);
		if (buttonPermissionList == null) {
			return SKIP_BODY;
		}
		for (SysPrivilege provilege : buttonPermissionList) {
			/**将后台session中的按钮？及后面字符串去除*/
			if (code.equals(provilege.getUrl())) {
				result = true;
			}
		}
		return result ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
	
}
