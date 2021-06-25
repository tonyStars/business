package com.club.business.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.club.business.common.annotation.UserLog;
import com.club.business.excel.common.ExcelExportlHelper;
import com.club.business.excel.common.ExportView;
import com.club.business.sys.service.SysUserServiceImpl;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.BaseConstant;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 系统用户管理
 *
 * @author Tom
 * @date 2019-12-04
 */
@Controller
@RequestMapping("/sys/user")
public class UserController {

    private static final String SERVICE_NAME = "系统用户管理#";

    @Resource
    private SysUserServiceImpl userService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/user/user_list");
        return mv;
    }

    /**
     * 用户查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = UserController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        String userName = request.getParameter("userName");
        String sex = request.getParameter("sex");
        String status = request.getParameter("status");
        return userService.search(page,pageSize,userName,sex,status);
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
        mv.setViewName("sys/user/user_add");
        return mv;
    }

    /**
     * 新增用户
     * @param model 用户对象
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = UserController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(SysUser model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(model.getUserCode())){
            throw new BusinessException("员工账号不能为空!");
        }
        if(StringUtils.isBlank(model.getUserName())){
            throw new BusinessException("员工姓名不能为空!");
        }
        if(model.getSex() == null){
            throw new BusinessException("性别不能为空!");
        }
        if(StringUtils.isBlank(model.getPassword())){
            throw new BusinessException("密码不能为空!");
        }
        if(model.getType() == null){
            throw new BusinessException("工作类型不能为空!");
        }
        if(model.getUserDuty() == null){
            throw new BusinessException("员工职务不能为空!");
        }
        if(model.getCompanyId() == null){
            throw new BusinessException("所属公司不能为空!");
        }
        SysUser user = (SysUser)session.getAttribute(BaseConstant.SESSION_USER);
        userService.add(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 修改用户跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String id = request.getParameter("userId");
        SysUser model = userService.getModelById(id);
        mv.addObject("model",model);
        mv.setViewName("sys/user/user_edit");
        return mv;
    }

    /**
     * 修改用户
     * @param model 用户对象
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/edit")
    @UserLog(description = UserController.SERVICE_NAME + BaseConstant.LOG_UPDATE)
    public Map<String, Object> edit(SysUser model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(model.getUserCode())){
            throw new BusinessException("员工账号不能为空!");
        }
        if(StringUtils.isBlank(model.getUserName())){
            throw new BusinessException("员工姓名不能为空!");
        }
        if(model.getSex() == null){
            throw new BusinessException("性别不能为空!");
        }
        if(StringUtils.isBlank(model.getPassword())){
            throw new BusinessException("密码不能为空!");
        }
        if(model.getType() == null){
            throw new BusinessException("工作类型不能为空!");
        }
        if(model.getUserDuty() == null){
            throw new BusinessException("员工职务不能为空!");
        }
        if(model.getCompanyId() == null){
            throw new BusinessException("所属公司不能为空!");
        }
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        userService.edit(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 启用用户
     * @param ids 用户表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/ok")
    @UserLog(description = UserController.SERVICE_NAME + BaseConstant.LOG_RESUME)
    public Map<String, Object> ok(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        userService.ok(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 停用用户
     * @param ids 用户表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    @UserLog(description = UserController.SERVICE_NAME + BaseConstant.LOG_DELETE)
    public Map<String, Object> del(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        userService.del(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 用户授权跳转
     * @param model 用户对象(含id的对象)
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toAuthorize")
    public ModelAndView toAuthorize(SysUser model) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("model",model);
        mv.setViewName("sys/user/user_role");
        return mv;
    }

    /**
     * 保存选择的角色
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRoleAuth")
    public Map<String, Object> addRoleAuth(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        String userId = request.getParameter("userId");
        String datas = request.getParameter("datas");
        if(StringUtils.isBlank(userId)){
            throw new BusinessException("数据异常!");
        }
        userService.addRoleAuth(userId, datas);
        resultMap.put("code", 0);
        return resultMap;
    }

    /**
     * Excel导出
     * @param content
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/toExport")
    public String toExport(@RequestParam String content, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> paramMap = JSONObject.parseObject(content);
        paramMap = paramMap.entrySet().stream()
                .filter(map -> null != map.getValue() && StringUtils.isNotBlank(map.getValue()+""))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())) ;
        ExportView exportView = userService.getExportView(paramMap);
        ExcelExportlHelper.exportExcel(exportView,request,response);
        return null;
    }

    /**
     * 下载模板
     * @param content
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/templetExport")
    public String templetExport(@RequestParam String content, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object> paramMap = JSONObject.parseObject(content);
        ExportView exportView = userService.getTempExportView();
        ExcelExportlHelper.exportExcel(exportView,request,response);
        return null;
    }

    /**
     * 导入excel数据
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/toImport",method = RequestMethod.POST)
    public Map<String,Object> toImport(MultipartFile file, HttpServletRequest request) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        SysUser user = (SysUser)request.getSession().getAttribute(BaseConstant.SESSION_USER);
        List<SysUser> userLists = userService.analysisExcel(file,user);
        map.put("code",0);
        map.put("data",userLists);
        return map;
    }

    /**
     * 保存excel上传的数据
     * @param lists excel的订单数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveExcelData",method = RequestMethod.POST)
    public Map<String,Object> saveExcelData(String lists) throws Exception {
        return userService.saveExcelData(lists);
    }

    /**
     * 上传头像保存
     * @param files 图片文件(支持多张)
     * @return
     * @throws BusinessException
     */
    @ResponseBody
    @RequestMapping("/saveImg")
    public Map<String,Object> saveImg(MultipartFile[] files) throws Exception {
        Map<String,Object> resMap = new HashMap<>();
        if (files == null || files.length == 0) {
            throw new BusinessException("请选择一张图片！");
        }
        resMap = userService.saveImg(files);
        resMap.put("code", "0");
        return resMap;
    }

    /**
     * 删除头像图片
     * @param picUrl 图片访问路径
     * @return
     * @throws BusinessException
     */
    @ResponseBody
    @RequestMapping("delImg")
    public Map<String,Object> delImg(String picUrl) throws Exception{
        Map<String,Object> resMap = new HashMap<>();
        userService.delImg(picUrl);
        resMap.put("code", "0");
        return resMap;
    }

    /**
     * 搜索框搜索用户数据
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/searchSelect")
    public Map<String, Object> searchSelect(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        String userName = request.getParameter("keyword");
        return userService.searchSelect(page,pageSize,userName);
    }

}
