package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author 
 * @date 2019-12-11
 */
public class SysOperLog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId("ID")
    private Integer id;
    
    /**
     * 操作用户ID
     */
    @TableField("USER_ID")
    private Integer userId;
    
    /**
     * 操作用户名称
     */
    @TableField("USER_NAME")
    private String userName;
    
    /**
     * 操作用户账号
     */
    @TableField("USER_ACCOUNT")
    private String userAccount;
    
    /**
     * 操作模块
     */
    @TableField("MENU_NAME")
    private String menuName;
    
    /**
     * 操作类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 操作类型名称
     */
    @TableField("TYPE_NAME")
    private String typeName;
    
    /**
     * 操作时间
     */
    @TableField("OPER_TIME")
    private LocalDateTime operTime;
    
    /**
     * 操作IP
     */
    @TableField("OPER_IP")
    private String operIp;
    
    /**
     * 操作状态
     */
    @TableField("STATUS")
    private Integer status;
    
    /**
     * 操作描述
     */
    @TableField("MEMO")
    private String memo;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public LocalDateTime getOperTime() {
        return operTime;
    }

    public void setOperTime(LocalDateTime operTime) {
        this.operTime = operTime;
    }
    
    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Override
    public String toString() {
        return "SysOperLog{" +
        "id=" + id +
        ", userId=" + userId +
        ", userName=" + userName +
        ", userAccount=" + userAccount +
        ", menuName=" + menuName +
        ", type=" + type +
        ", operTime=" + operTime +
        ", operIp=" + operIp +
        ", status=" + status +
        ", memo=" + memo +
        "}";
    }
}
