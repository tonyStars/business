package com.club.business.sys.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.sys.service.SysDictionaryServiceImpl;
import com.club.business.sys.vo.SysDictionary;
import com.club.business.util.BaseConstant;
import com.club.business.util.JsonUtils;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 系统数据字典
 *
 * @author Tom
 * @date 2019-12-04
 */
@Controller
@RequestMapping("/sys/dictionary")
public class DictionaryController {

    private static final String SERVICE_NAME = "系统数据字典#";

    @Resource
    private SysDictionaryServiceImpl dictionaryService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/dictionary/dictionary_list");
        return mv;
    }

    /**
     * 数据字典查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = DictionaryController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        String typeName = request.getParameter("typeName");
        String itemName = request.getParameter("itemName");
        String status = request.getParameter("status");
        return dictionaryService.search(page,pageSize,typeName,itemName,status);
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
        mv.setViewName("sys/dictionary/dictionary_add");
        return mv;
    }

    /**
     * 新增数据字典
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = DictionaryController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        String data = request.getParameter("data");
        if(StringUtils.isBlank(data)){
            throw new BusinessException("参数为空!");
        }
        List<SysDictionary> list = JsonUtils.getJsonToList(data,SysDictionary.class);
        if(list != null && list.size() > 0){
            for(SysDictionary sd : list){
                if(StringUtils.isBlank(sd.getTypeName())){
                    throw new BusinessException("类型名称存在空值!");
                }
                if(StringUtils.isBlank(sd.getItemName())){
                    throw new BusinessException("子项目名称存在空值!");
                }
                if(sd.getValue() == null){
                    throw new BusinessException("设定值存在空值!");
                }
                if(sd.getOrderId() == null){
                    throw new BusinessException("排序存在空值!");
                }
            }
            dictionaryService.add(list);
        }else{
            throw new BusinessException("参数为空!");
        }
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 修改数据字典跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(String id) throws Exception {
        ModelAndView mv = new ModelAndView();
        SysDictionary model = dictionaryService.getById(id);
        mv.addObject("model",model);
        mv.setViewName("sys/dictionary/dictionary_edit");
        return mv;
    }

    /**
     * 修改数据字典
     * @param model 数据字典对象
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/edit")
    @UserLog(description = DictionaryController.SERVICE_NAME + BaseConstant.LOG_UPDATE)
    public Map<String, Object> edit(SysDictionary model) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        dictionaryService.edit(model);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 启用数据字典
     * @param ids 数据字典表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/ok")
    @UserLog(description = DictionaryController.SERVICE_NAME + BaseConstant.LOG_RESUME)
    public Map<String, Object> ok(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        dictionaryService.ok(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 停用数据字典
     * @param ids 数据字典表id集合
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    @UserLog(description = DictionaryController.SERVICE_NAME + BaseConstant.LOG_DELETE)
    public Map<String, Object> del(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        if(StringUtils.isBlank(ids)){
            throw new BusinessException("数据异常!");
        }
        dictionaryService.del(ids);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 根据TypeName查询数据字典集合
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryByName")
    public Map<String, Object> queryByName(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        String typeName = request.getParameter("typeName");
        String itemName = request.getParameter("itemName");
        List<SysDictionary> list = dictionaryService.queryByName(typeName,itemName);
        resultMap.put("data",list);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 查询所有类型
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryAllType")
    public Map<String, Object> queryAllType() throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<SysDictionary> list = dictionaryService.queryAllType();
        resultMap.put("data",list);
        resultMap.put("code",0);
        return resultMap;
    }
}
