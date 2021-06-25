package com.club.business.sys.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.sys.service.SysCompanyServiceImpl;
import com.club.business.sys.vo.SysCompany;
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
import java.util.Map;

/**
 * @Description: 系统公司管理
 *
 * @author Tom
 * @date 2019-12-09
 */
@Controller
@RequestMapping("/sys/company")
public class CompanyController {

    private static final String SERVICE_NAME = "系统公司管理#";

    @Resource
    private SysCompanyServiceImpl companyService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/company/company_list");
        return mv;
    }

    /**
     * 公司查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = CompanyController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        String companyName = request.getParameter("companyName");
        String companyCode = request.getParameter("companyCode");
        String typeCode = request.getParameter("typeCode");
        String companyLevel = request.getParameter("companyLevel");
        String status = request.getParameter("status");
        return companyService.search(page,pageSize,companyName,companyCode,typeCode,companyLevel,status);
    }

    /**
     * 新增公司跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toAdd")
    public ModelAndView toAdd() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/company/company_add");
        return mv;
    }

    /**
     * 搜索框搜索公司数据
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
        return companyService.searchSelect(page,pageSize,keyword);
    }

    /**
     * 新增公司
     * @param model 公司对象
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = CompanyController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(SysCompany model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(model.getCompanyCode())){
            throw new BusinessException("公司编码不能为空!");
        }
        if(StringUtils.isBlank(model.getCompanyName())){
            throw new BusinessException("公司名称不能为空!");
        }
        if(model.getTypeCode() == null){
            throw new BusinessException("类型不能为空!");
        }
        if(model.getCompanyLevel() == null){
            throw new BusinessException("级别不能为空!");
        }
        if(StringUtils.isBlank(model.getAddressIds())){
            throw new BusinessException("省市区为空!");
        }
        if(StringUtils.isBlank(model.getAddressNames())){
            throw new BusinessException("省市区为空!");
        }
        SysUser user = (SysUser)session.getAttribute(BaseConstant.SESSION_USER);
        companyService.add(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 修改公司跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String id = request.getParameter("companyId");
        SysCompany model = companyService.getById(id);
        mv.addObject("model",companyService.pageBackRegions(model));
        mv.setViewName("sys/company/company_edit");
        return mv;
    }

    /**
     * 修改公司
     * @param model 公司对象
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/edit")
    @UserLog(description = CompanyController.SERVICE_NAME + BaseConstant.LOG_UPDATE)
    public Map<String, Object> edit(SysCompany model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(model.getCompanyCode())){
            throw new BusinessException("公司编码不能为空!");
        }
        if(StringUtils.isBlank(model.getCompanyName())){
            throw new BusinessException("公司名称不能为空!");
        }
        if(model.getTypeCode() == null){
            throw new BusinessException("类型不能为空!");
        }
        if(model.getCompanyLevel() == null){
            throw new BusinessException("级别不能为空!");
        }
        if(StringUtils.isBlank(model.getAddressIds())){
            throw new BusinessException("省市区为空!");
        }
        if(StringUtils.isBlank(model.getAddressNames())){
            throw new BusinessException("省市区为空!");
        }
        SysUser user = (SysUser) session.getAttribute(BaseConstant.SESSION_USER);
        companyService.edit(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 启用公司
     * @param ids 公司表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/ok")
    @UserLog(description = CompanyController.SERVICE_NAME + BaseConstant.LOG_RESUME)
    public Map<String, Object> ok(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        companyService.ok(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 停用公司
     * @param ids 公司表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    @UserLog(description = CompanyController.SERVICE_NAME + BaseConstant.LOG_DELETE)
    public Map<String, Object> del(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        companyService.del(ids);
        resultMap.put("code",0);
        return resultMap;
    }
}
