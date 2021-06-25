package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.club.business.goodluck.vo.BaseModel;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 消息推送表
 * </p>
 *
 * @author 
 * @date 2020-08-28
 */
public class SysMassage extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 消息内容
     */
    private String massage;
    
    /**
     * 消息类型(1.单独推送 2.群发)
     */
    private Integer type;
    
    /**
     * 发送用户id
     */
    private Integer createUserId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户ID - 针对单独发送需要此字段
     */
    @TableField(exist = false)
    private String userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
    
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SysMassage{" +
                "id=" + id +
                ", massage='" + massage + '\'' +
                ", type=" + type +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                '}';
    }
}
