package com.club.business.goodluck.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.goodluck.service.BusinessSuperLottoServiceImpl;
import com.club.business.goodluck.vo.BusinessSuperLotto;
import com.club.business.util.BaseConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 大乐透管理
 *
 * @author Tom
 * @date 2020-08-26
 */
@Controller
@RequestMapping("/luck/lotto")
public class LottoController {

    private static final String SERVICE_NAME = "大乐透管理#";

    @Resource
    private BusinessSuperLottoServiceImpl superLottoService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("luck/lotto/lotto_list");
        return mv;
    }

    /**
     * 往期数据查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = LottoController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(BusinessSuperLotto lotto) throws Exception {
        return superLottoService.search(lotto);
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
        mv.setViewName("luck/lotto/lotto_add");
        return mv;
    }

    /**
     * 新增号码
     * @param model 大乐透对象
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = LottoController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(BusinessSuperLotto model) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        superLottoService.add(model);
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
        BusinessSuperLotto model = superLottoService.getById(id);
        mv.addObject("model",model);
        mv.setViewName("luck/lotto/lotto_edit");
        return mv;
    }

    /**
     * 修改号码
     * @param model 大乐透对象
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/edit")
    @UserLog(description = LottoController.SERVICE_NAME + BaseConstant.LOG_UPDATE)
    public Map<String, Object> edit(BusinessSuperLotto model) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        superLottoService.edit(model);
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
        mv.setViewName("luck/lotto/lotto_luck");
        return mv;
    }

    /**
     * 抽取幸运号码
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/luck")
    @UserLog(description = LottoController.SERVICE_NAME + BaseConstant.LOG_LUCK)
    public Map<String, Object> luck() throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        BusinessSuperLotto model = superLottoService.luck();
        resultMap.put("code",0);
        resultMap.put("data",model);
        return resultMap;
    }

    /**
     * 随机抽奖跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toRollLuck")
    public ModelAndView toRollLuck() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("luck/lotto/lotto_rollluck");
        return mv;
    }

}
