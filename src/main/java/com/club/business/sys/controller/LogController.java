package com.club.business.sys.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.sys.service.SysOperLogServiceImpl;
import com.club.business.util.BaseConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: 系统日志管理
 *
 * @author Tom
 * @date 2019-12-12
 */
@Controller
@RequestMapping("/sys/log")
public class LogController {

    private static final String SERVICE_NAME = "系统日志管理#";

    @Resource
    private SysOperLogServiceImpl operLogService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/log/log_list");
        return mv;
    }

    /**
     * 日志查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = LogController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        String menuName = request.getParameter("menuName");
        String operIp = request.getParameter("operIp");
        String typeName = request.getParameter("typeName");
        String status = request.getParameter("status");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        return operLogService.search(page,pageSize,menuName,operIp,typeName,status,startTime,endTime);
    }

}
