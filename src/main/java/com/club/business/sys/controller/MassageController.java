package com.club.business.sys.controller;

import com.club.business.common.annotation.UserLog;
import com.club.business.common.component.netty.service.PushService;
import com.club.business.sys.service.SysMassageServiceImpl;
import com.club.business.sys.service.SysUserServiceImpl;
import com.club.business.sys.vo.SysMassage;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.BaseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统消息管理Controller
 *
 * @author Tom
 * @date 2020-08-28
 */
@RestController
@RequestMapping("/sys/massage")
public class MassageController {

    private static final String SERVICE_NAME = "系统消息管理#";

    @Resource
    private SysMassageServiceImpl massageService;

    @Resource
    private SysUserServiceImpl userService;

    @Autowired
    private PushService pushService;

    /**
     * 查询跳转
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/massage/massage_list");
        return mv;
    }

    /**
     * 分页查询
     * @param model 对象参数
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    @UserLog(description = MassageController.SERVICE_NAME + BaseConstant.LOG_SEARCH)
    public Map<String, Object> search(SysMassage model) throws Exception {
        return massageService.search(model);
    }

    /**
     * 新增跳转
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toAdd")
    public ModelAndView toAdd() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/massage/massage_add");
        return mv;
    }

    /**
     * 新增消息
     * @param model 消息对象
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @UserLog(description = MassageController.SERVICE_NAME + BaseConstant.LOG_ADD)
    public Map<String, Object> add(SysMassage model, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        SysUser user = (SysUser)session.getAttribute(BaseConstant.SESSION_USER);
        massageService.add(model,user);
        resultMap.put("code",0);
        return resultMap;
    }

    /**
     * 消息回调
     * @param uid 用户id
     * @param msgId 消息id
     * @throws Exception
     */
    @RequestMapping(value = "/callBack")
    public void callBack(@RequestParam("userId") String uid,@RequestParam("msgId") String msgId) throws Exception {
        massageService.callBack(uid,msgId);
    }

    /**
     * 用户获取离线消息接口
     * @param userId 用户id
     */
    @RequestMapping(value = "/outLineMsg")
    public void outLineMsg(@RequestParam(value = "userId",required = false) String userId) throws Exception {
        massageService.sendOutLineMsg(userId);
    }

    /**
     * 推送给所有用户
     * @param msg 消息信息
     */
    @PostMapping("/pushAll")
    public void pushToAll(@RequestParam("msg") String msg){
        pushService.pushMsgToAll(msg);
    }

    /**
     * 推送给指定用户
     * @param userId 用户ID
     * @param msg 消息信息
     */
    @PostMapping("/pushOne")
    public void pushMsgToOne(@RequestParam("userId") String userId, @RequestParam("msg") String msg){
        pushService.pushMsgToOne(userId,msg);
    }

    /**
     * 获取当前连接数
     */
    @GetMapping("/getConnectCount")
    public int getConnectCout(){
        return pushService.getConnectCount();
    }
}