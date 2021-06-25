package com.club.business.sys.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.sys.service.SysRoleServiceImpl;
import com.club.business.sys.vo.SysRole;
import com.club.business.sys.vo.SysUser;
import com.club.business.sys.vo.RoleBaseModel;
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
 * @Description: 系统角色管理
 *
 * @author Tom
 * @date 2019-11-24
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController {

    private static final String SERVICE_NAME = "系统角色管理#";

    @Resource
    private SysRoleServiceImpl roleService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/role/role_list");
        return mv;
    }

    /**
     * 角色查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = RoleController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        String roleName = request.getParameter("roleName");
        String status = request.getParameter("status");
        return roleService.search(page,pageSize,roleName,status);
    }

    /**
     * 新增权限跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toAdd")
    public ModelAndView toAdd() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/role/role_add");
        return mv;
    }

    /**
     * 新增角色
     * @param model 角色对象
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = RoleController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(SysRole model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(model.getRoleName())){
            throw new BusinessException("请输入角色名称");
        }
        SysUser user = (SysUser)session.getAttribute(BaseConstant.SESSION_USER);
        roleService.add(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 修改角色跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String id = request.getParameter("roleId");
        SysRole model = roleService.getById(id);
        mv.addObject("model",model);
        mv.setViewName("sys/role/role_edit");
        return mv;
    }

    /**
     * 修改角色
     * @param model 角色对象
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/edit")
    @UserLog(description = RoleController.SERVICE_NAME + BaseConstant.LOG_UPDATE)
    public Map<String, Object> edit(SysRole model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        roleService.edit(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 启用角色
     * @param ids 角色表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/ok")
    @UserLog(description = RoleController.SERVICE_NAME + BaseConstant.LOG_RESUME)
    public Map<String, Object> ok(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        roleService.ok(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 停用角色
     * @param ids 角色表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    @UserLog(description = RoleController.SERVICE_NAME + BaseConstant.LOG_DELETE)
    public Map<String, Object> del(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        roleService.del(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 角色授权跳转
     * @param model 角色对象(含id的对象)
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toAuthorize")
    public ModelAndView toAuthorize(SysRole model) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("model",model);
        mv.setViewName("sys/role/role_privilege");
        return mv;
    }

    /**
     * 保存选择的菜单权限
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMenuAuth")
    public Map<String, Object> addRoleMenuAuth(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        /**获取保存的菜单权限*/
        String menuIds = request.getParameter("menuIds");
        /**获取需要保存菜单权限的角色*/
        String roleId = request.getParameter("roleId");
        roleService.addRoleMenuAuth(menuIds, roleId);
        resultMap.put("code", 0);
        return resultMap;
    }

    /**
     * 查询所有角色(正常)
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/searchList")
    public Map<String, Object> searchList(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        String userId = request.getParameter("userId");
        List<RoleBaseModel> list = roleService.searchList(userId);
        resultMap.put("data",list);
        resultMap.put("code",0);
        return resultMap;
    }

}
