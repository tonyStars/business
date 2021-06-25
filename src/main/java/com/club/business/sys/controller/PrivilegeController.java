package com.club.business.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.club.business.common.annotation.UserLog;
import com.club.business.sys.service.SysPrivilegeServiceImpl;
import com.club.business.sys.vo.SysPrivilege;
import com.club.business.sys.vo.SysRole;
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
 * @Description: 菜单权限管理
 *
 * @author Tom
 * @date 2019-11-16
 */
@Controller
@RequestMapping("/sys/privilege")
public class PrivilegeController {

    private static final String SERVICE_NAME = "菜单权限管理#";

    @Resource
    private SysPrivilegeServiceImpl privilegeService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/privilege/privilege_list");
        return mv;
    }

    /**
     * 菜单查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = PrivilegeController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search() throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<SysPrivilege> list = privilegeService.search();
        resultMap.put("data",list);
        resultMap.put("code",0);
        return resultMap;
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
        mv.setViewName("sys/privilege/privilege_add");
        return mv;
    }

    /**
     * 获取一级菜单集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/firstGrade")
    public Map<String, Object> firstGrade() throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<SysPrivilege> list = privilegeService.firstGrade();
        resultMap.put("data",list);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 获取二级菜单集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/secondGrade")
    public Map<String, Object> secondGrade() throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<SysPrivilege> list = privilegeService.secondGrade();
        resultMap.put("data",list);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 父级菜单树跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/dialog")
    public ModelAndView dialog() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/privilege/privilege_dialog");
        return mv;
    }

    /**
     * 获取菜单树 - 获取系统所有的功能菜单
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getMenuTree")
    public Map<String, Object> getMenuTree() {
        return privilegeService.getMenuTree();
    }

    /**
     * 新增功能权限
     * @param pvg 权限对象
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = PrivilegeController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(SysPrivilege pvg, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        SysPrivilege model = getAddModel(pvg);
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        privilegeService.add(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 修改权限跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String id = request.getParameter("privilegeId");
        SysPrivilege model = privilegeService.toEdit(id);
        mv.addObject("model",model);
        mv.setViewName("sys/privilege/privilege_edit");
        return mv;
    }

    /**
     * 修改功能权限
     * @param pvg 权限对象
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/edit")
    @UserLog(description = PrivilegeController.SERVICE_NAME + BaseConstant.LOG_UPDATE)
    public Map<String, Object> edit(SysPrivilege pvg, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        SysPrivilege model = getAddModel(pvg);
        model.setPrivilegeId(pvg.getPrivilegeId());
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        privilegeService.edit(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 删除功能权限
     * @param privilegeId 权限表id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    @UserLog(description = PrivilegeController.SERVICE_NAME + BaseConstant.LOG_REMOVE)
    public Map<String, Object> del(String privilegeId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        privilegeService.del(privilegeId);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 功能权限排序跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toSort")
    public ModelAndView toSort() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/privilege/privilege_sort");
        return mv;
    }

    /**
     * 排序页面按条件查询菜单集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sortSearch")
    public Map<String, Object> sortSearch(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        String grade = request.getParameter("grade");
        String privilegeId = request.getParameter("privilegeId");
        if(StringUtils.isBlank(grade)){
            throw new BusinessException("请选择菜单等级!");
        }
        List<SysPrivilege> list = privilegeService.sortSearch(grade,privilegeId);
        resultMap.put("data",list);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 保存排序操作
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sort")
    @UserLog(description = PrivilegeController.SERVICE_NAME + BaseConstant.LOG_SORT)
    public Map<String, Object> sort(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        String json = request.getParameter("data");
        if(StringUtils.isBlank(json)){
            throw new BusinessException("数据为空!");
        }
        List<SysPrivilege> pvg = JSONObject.parseArray(json,SysPrivilege.class);
        privilegeService.sort(pvg);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 根据前端传来的对象参数,对不同选择的参数进行校验,并返回一个新封装的model
     * @return
     * @throws Exception
     */
    public SysPrivilege getAddModel(SysPrivilege pvg) throws Exception{
        SysPrivilege model = new SysPrivilege();
        if(pvg.getTp() == null){
            throw new BusinessException("功能类型为空!");
        }
        model.setTp(pvg.getTp());
        if(StringUtils.isBlank(pvg.getIcon())){
            throw new BusinessException("功能图标为空!");
        }
        /**对选择的功能图标加上"fa "前缀,以便页面展示*/
        model.setIcon("fa " + pvg.getIcon());
        /**校验菜单*/
        if(pvg.getTp().intValue() == 0){
            if(pvg.getGrade() == null){
                throw new BusinessException("菜单等级为空!");
            }
            model.setGrade(pvg.getGrade());
            if(StringUtils.isBlank(pvg.getPrivilegeName())){
                throw new BusinessException("菜单名称为空!");
            }
            model.setPrivilegeName(pvg.getPrivilegeName());
            if(pvg.getGrade().intValue() == 2){
                if(pvg.getParentId() == null){
                    throw new BusinessException("父级菜单id为空!");
                }
                model.setParentId(pvg.getParentId());
                if(StringUtils.isBlank(pvg.getParentName())){
                    throw new BusinessException("父级菜单名称为空,请联系管理员!");
                }
                model.setParentName(pvg.getParentName());
            }
            if(pvg.getGrade().intValue() == 3){
                if(pvg.getParentId() == null){
                    throw new BusinessException("父级菜单id为空!");
                }
                model.setParentId(pvg.getParentId());
                if(StringUtils.isBlank(pvg.getParentName())){
                    throw new BusinessException("父级菜单名称为空,请联系管理员!");
                }
                model.setParentName(pvg.getParentName());
                if(StringUtils.isBlank(pvg.getUrl())){
                    throw new BusinessException("菜单URL为空!");
                }
                model.setUrl(pvg.getUrl());
            }
        }
        /**校验按钮*/
        if(pvg.getTp().intValue() == 1){
            if(pvg.getParentIdB() == null){
                throw new BusinessException("父级菜单id为空!");
            }
            if(StringUtils.isBlank(pvg.getParentNameB())){
                throw new BusinessException("父级菜单名称为空!");
            }
            if(StringUtils.isBlank(pvg.getPrivilegeNameB())){
                throw new BusinessException("按钮名称为空!");
            }
            if(StringUtils.isBlank(pvg.getUrlB())){
                throw new BusinessException("按钮URL为空!");
            }
            model.setParentId(pvg.getParentIdB());
            model.setParentName(pvg.getParentNameB());
            model.setPrivilegeName(pvg.getPrivilegeNameB());
            model.setUrl(pvg.getUrlB());
        }
        return model;
    }

    /**
     * 获取菜单权限树
     * @param role 角色对象
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getFuncTree")
    public Map<String, Object> getFuncTree(SysRole role) throws Exception {
        return privilegeService.getFuncTree(role);
    }
}
