package com.club.business.goodluck.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.goodluck.service.BusinessBichromaticSphereServiceImpl;
import com.club.business.goodluck.vo.BusinessBichromaticSphere;
import com.club.business.util.BaseConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 双色球管理
 *
 * @author Tom
 * @date 2020-08-27
 */
@Controller
@RequestMapping("/luck/sphere")
public class SphereController {

    private static final String SERVICE_NAME = "双色球管理#";

    @Resource
    private BusinessBichromaticSphereServiceImpl bichromaticSphereService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("luck/sphere/sphere_list");
        return mv;
    }

    /**
     * 往期数据查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = SphereController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(BusinessBichromaticSphere sphere) throws Exception {
        return bichromaticSphereService.search(sphere);
    }

    /**
     * 新增号码跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toAdd")
    public ModelAndView toAdd() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("luck/sphere/sphere_add");
        return mv;
    }

    /**
     * 新增号码
     * @param model 双色球对象
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = SphereController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(BusinessBichromaticSphere model) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        bichromaticSphereService.add(model);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 修改号码跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(Integer id) throws Exception {
        ModelAndView mv = new ModelAndView();
        BusinessBichromaticSphere model = bichromaticSphereService.getById(id);
        mv.addObject("model",model);
        mv.setViewName("luck/sphere/sphere_edit");
        return mv;
    }

    /**
     * 修改号码
     * @param model 双色球对象
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/edit")
    @UserLog(description = SphereController.SERVICE_NAME + BaseConstant.LOG_UPDATE)
    public Map<String, Object> edit(BusinessBichromaticSphere model) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        bichromaticSphereService.edit(model);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 幸运号码跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toLuck")
    public ModelAndView toLuck() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("luck/sphere/sphere_luck");
        return mv;
    }

    /**
     * 抽取幸运号码
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/luck")
    @UserLog(description = SphereController.SERVICE_NAME + BaseConstant.LOG_LUCK)
    public Map<String, Object> luck() throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        BusinessBichromaticSphere model = bichromaticSphereService.luck();
        resultMap.put("code",0);
        resultMap.put("data",model);
        return resultMap;
    }

}
