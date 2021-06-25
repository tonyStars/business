package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 消息用户表
 * </p>
 *
 * @author 
 * @date 2020-08-31
 */
public class SysMsgUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 消息表id
     */
    private Integer msgId;
    
    /**
     * 用户id
     */
    private Integer userId;

    public SysMsgUser() {
    }

    public SysMsgUser(Integer msgId, Integer userId) {
        this.msgId = msgId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "SysMsgUser{" +
        "id=" + id +
        ", msgId=" + msgId +
        ", userId=" + userId +
        "}";
    }
}
