package com.club.business.sys.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.common.vo.MenuBean;
import com.club.business.sys.service.SysLoginServiceImpl;
import com.club.business.sys.vo.SysPrivilege;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.BaseConstant;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户登录控制器
 * @author Tom
 * @date 2019-11-16
 */
@Controller
@RequestMapping("/")
public class LoginController {

    private static final String SERVICE_NAME = "系统登录控制器#";

    /**自定义实现类,非plus自动生成*/
    @Resource
    private SysLoginServiceImpl loginService;

    /**
     * 项目请求URL
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("business")
    public ModelAndView business() throws Exception {
        ModelAndView mv = new ModelAndView();
//        mv.setViewName("sys/login/login");
        mv.setViewName("sys/login/login-2");
        return mv;
    }

    /**
     * 系统登陆接口
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("login")
    @UserLog(description = LoginController.SERVICE_NAME + BaseConstant.LOG_LOGIN)
    public Map<String, Object> login(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(3);
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            throw new BusinessException("用户名或密码为空");
        }
        Map<String, Object> returnMap = loginService.login(account, password);
        SysUser user = (SysUser) returnMap.get("user");
        List<Object> menuList = (List<Object>) returnMap.get("menuList");
        List<Object> buttonList = (List<Object>) returnMap.get("buttonList");
        /**把用户信息放入session*/
        request.getSession().setAttribute(BaseConstant.SESSION_USER, user);
        /**把用户菜单信息放入session*/
        request.getSession().setAttribute(BaseConstant.SESSION_MENU, menuList);
        /**把用户按钮信息放入session*/
        request.getSession().setAttribute(BaseConstant.SESSION_BUTTON, buttonList);
        resultMap.put("user", user);
        resultMap.put("code", 0);
        return resultMap;
    }

    /**
     * 登陆成功之后跳转到主界面
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView();
        //TODO 可加载token校验
        mv.setViewName("/sys/login/index");
        return mv;
    }

    /**
     * 首页加载菜单请求接口
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getMenu")
    public Map<String, Object> getMenu(HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        /**获取session对象*/
        List<SysPrivilege> privilegeList = (List<SysPrivilege>) session.getAttribute(BaseConstant.SESSION_MENU);
        /**菜单获取及转换处理*/
        List<MenuBean> list = loginService.menuTransfer(privilegeList);
        resultMap.put("systemMenu", list);
        return resultMap;
    }

    /**
     * 退出登陆
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("exit")
    public ModelAndView exit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/login/login");
        return mv;
    }

    /**
     * 修改密码跳转
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("toPwdEdit")
    public ModelAndView editPassWord(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView();
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        if(user == null){
            throw new BusinessException("登陆超时,请重新登陆!");
        }
        mv.addObject("user",user);
        mv.setViewName("sys/login/pwdEdit");
        return mv;
    }

    /**
     * 修改用户密码
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("updatePwd")
    public Map<String,Object> updatePwd(HttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        if(user == null){
            throw new BusinessException("登陆超时,请重新登陆!");
        }
        String orgpass = request.getParameter("orgpass");
        String password = request.getParameter("password");
        if (StringUtils.isBlank(orgpass)) {
            throw new BusinessException("旧密码为空!");
        }
        if (StringUtils.isBlank(password)) {
            throw new BusinessException("新密码为空!");
        }
        loginService.updatePwd(orgpass,password,user);
        /**更新用户信息*/
        session.setAttribute(BaseConstant.SESSION_USER, user);
        resultMap.put("code", 0);
        return resultMap;
    }

    /**
     * 查询用户基本资料
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("userView")
    public ModelAndView userView(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView();
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        if(user == null){
            throw new BusinessException("登陆超时,请重新登陆!");
        }
        mv.addObject("user",user);
        mv.setViewName("sys/login/userView");
        return mv;
    }

}
