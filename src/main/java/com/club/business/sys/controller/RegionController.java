package com.club.business.sys.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.sys.service.SysRegionServiceImpl;
import com.club.business.sys.vo.SysRegion;
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
 * @Description: 行政区域管理
 *
 * @author Tom
 * @date 2019-12-09
 */
@Controller
@RequestMapping("/sys/region")
public class RegionController {

    private static final String SERVICE_NAME = "行政区域管理#";

    @Resource
    private SysRegionServiceImpl regionService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/region/region_list");
        return mv;
    }

    /**
     * 行政区域查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = RegionController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        String name = request.getParameter("name");
        String regionType = request.getParameter("regionType");
        String status = request.getParameter("status");
        return regionService.search(page,pageSize,name,regionType,status);
    }

    /**
     * 新增行政区域跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toAdd")
    public ModelAndView toAdd() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/region/region_add");
        return mv;
    }

    /**
     * 搜索框搜索行政区域数据
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/searchSelect")
    public Map<String, Object> searchSelect(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        String keyword = request.getParameter("keyword");
        return regionService.searchSelect(page,pageSize,keyword);
    }

    /**
     * 新增行政区域
     * @param model 行政区域
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = RegionController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(SysRegion model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(model.getCode())){
            throw new BusinessException("区域编码不能为空!");
        }
        if(StringUtils.isBlank(model.getName())){
            throw new BusinessException("区域名称不能为空!");
        }
        if(model.getParentId() == null){
            throw new BusinessException("父地区名称为空!");
        }
        if(StringUtils.isBlank(model.getParentName())){
            throw new BusinessException("父地区名称为空!");
        }
        if(model.getRegionType() == null){
            throw new BusinessException("类型不能为空!");
        }
        if(model.getIsservice() == null){
            throw new BusinessException("可否操作不能为空!");
        }
        SysUser user = (SysUser)session.getAttribute(BaseConstant.SESSION_USER);
        regionService.add(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 修改行政区域跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String id = request.getParameter("regionId");
        SysRegion model = regionService.getById(id);
        if(model.getIsservice() == 0){
            throw new BusinessException("该数据不可编辑,请选择可编辑数据操作!");
        }
        mv.addObject("model",model);
        mv.setViewName("sys/region/region_edit");
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
    @UserLog(description = RegionController.SERVICE_NAME + BaseConstant.LOG_UPDATE)
    public Map<String, Object> edit(SysRegion model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(model.getCode())){
            throw new BusinessException("区域编码不能为空!");
        }
        if(StringUtils.isBlank(model.getName())){
            throw new BusinessException("区域名称不能为空!");
        }
        if(model.getParentId() == null){
            throw new BusinessException("父地区名称为空!");
        }
        if(StringUtils.isBlank(model.getParentName())){
            throw new BusinessException("父地区名称为空!");
        }
        if(model.getRegionType() == null){
            throw new BusinessException("类型不能为空!");
        }
        if(model.getIsservice() == null){
            throw new BusinessException("可否操作不能为空!");
        }
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        regionService.edit(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 启用行政区域
     * @param ids 行政区域表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/ok")
    @UserLog(description = RegionController.SERVICE_NAME + BaseConstant.LOG_RESUME)
    public Map<String, Object> ok(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        regionService.ok(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 停用行政区域
     * @param ids 行政区域表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    @UserLog(description = RegionController.SERVICE_NAME + BaseConstant.LOG_DELETE)
    public Map<String, Object> del(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        regionService.del(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 省市区选择跳转(单选)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toRegionSingle")
    public ModelAndView toRegion() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/region/region_tree_single");
        return mv;
    }

    /**
     * 获取省市区树(单选)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/singleRegionTree")
    public Map<String, Object> singleRegionTree() throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Map<String, Object>> resultList = regionService.singleRegionTree();
        resultMap.put("treeNodes", resultList);
        return resultMap;
    }
}
