package com.club.business.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.common.component.netty.service.PushService;
import com.club.business.sys.vo.SysMassage;
import com.club.business.sys.dao.SysMassageMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.sys.vo.SysMsgUser;
import com.club.business.sys.vo.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 消息推送表 服务实现类
 * </p>
 *
 * @author 
 * @date 2020-08-28
 */
@Service
public class SysMassageServiceImpl extends ServiceImpl<SysMassageMapper,SysMassage> {

    @Autowired
    private SysUserServiceImpl userService;

    @Autowired
    private SysMsgUserServiceImpl msgUserService;

    @Autowired
    private PushService pushService;

    /**
     * 分页查询
     * @param model 对象参数
     * @return
     */
    public Map<String, Object> search(SysMassage model) throws Exception {
        QueryWrapper<SysMassage> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(model.getMassage())){
            qw.like("massage",model.getMassage());
        }
        if(model.getType() != null){
            qw.eq("type",model.getType());
        }
        Page<SysMassage> pageInfo = new Page<>();
        pageInfo.setCurrent(model.getPage());
        pageInfo.setSize(model.getLimit());
        Page<SysMassage> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 新增消息
     * @param model 对象参数
     * @param user 当前登录用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(SysMassage model, SysUser user) throws Exception {
        List<Integer> uIds = new ArrayList<>();
        if(model.getType().intValue() == 1){
            uIds.add(Integer.valueOf(model.getUserId()));
        }
        if(model.getType().intValue() == 2){
            uIds = userService.list().stream().map(SysUser::getUserId).collect(Collectors.toList());
        }
        model.setCreateUserId(user.getUserId());
        model.setCreateTime(LocalDateTime.now());
        this.save(model);
        if(uIds != null && uIds.size() > 0){
            List<SysMsgUser> mus = uIds.stream().map(p -> new SysMsgUser(model.getId(), p)).collect(Collectors.toList());
            msgUserService.saveBatch(mus);
        }
        sendMsg(model);
    }

    /**
     * 发送消息
     * @param model 消息对象
     */
    public void sendMsg(SysMassage model) throws Exception {
        String msg = model.getId() + "," + model.getMassage();
        /**单独推送*/
        if(model.getType().intValue() == 1){
            pushService.pushMsgToOne(model.getUserId(),msg);
        }
        /**群发*/
        if(model.getType().intValue() == 2){
            pushService.pushMsgToAll(msg);
        }
    }

    /**
     * 消息回调
     * @param uId 用户ID
     * @param msgId 消息ID
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void callBack(String uId,String msgId) throws Exception {
        UpdateWrapper<SysMsgUser> qw = new UpdateWrapper<SysMsgUser>()
                .eq("user_id",uId)
                .eq("msg_id",msgId);
        msgUserService.remove(qw);
    }

    /**
     * 发送该用户没有收到的离线消息
     * @param userId 用户ID
     */
    public void sendOutLineMsg(String userId) throws Exception {
        QueryWrapper<SysMsgUser> qw = new QueryWrapper<SysMsgUser>().eq("user_id",userId);
        List<SysMsgUser> list = msgUserService.list(qw);
        if(list != null && list.size() > 0){
            List<Integer> ids = list.stream().map(SysMsgUser::getMsgId).collect(Collectors.toList());
            List<SysMassage> msgs = new ArrayList<>(this.listByIds(ids));
            if(msgs != null && msgs.size() > 0){
                for(SysMassage msg : msgs){
                    String msgStr = msg.getId() + "," + msg.getMassage();
                    pushService.pushMsgToOne(userId,msgStr);
                }
            }
        }
    }
}
