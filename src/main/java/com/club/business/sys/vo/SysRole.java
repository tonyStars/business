package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId(value = "ROLE_ID", type = IdType.AUTO)
    private Integer roleId;
    
    /**
     * 角色名
     */
    @TableField("ROLE_NAME")
    private String roleName;
    
    /**
     * 描述
     */
    @TableField("ROLE_DESCRIBE")
    private String roleDescribe;
    
    /**
     * 状态：0-正常、-1-作废
     */
    @TableField("STATUS")
    private Integer status;
    
    /**
     * 创建人id
     */
    @TableField("ENTRY_USER_ID")
    private Integer entryUserId;
    
    /**
     * 创建人
     */
    @TableField("ENTRY_USER_NAME")
    private String entryUserName;
    
    /**
     * 创建时间
     */
    @TableField("ENTRY_USER_TIME")
    private LocalDateTime entryUserTime;
    
    /**
     * 最后更新人id
     */
    @TableField("UPDATE_USER_ID")
    private Integer updateUserId;
    
    /**
     * 最后更新人
     */
    @TableField("UPDATE_USER_NAME")
    private String updateUserName;
    
    /**
     * 最后更新时间
     */
    @TableField("UPDATE_USER_TIME")
    private LocalDateTime updateUserTime;
    
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getEntryUserId() {
        return entryUserId;
    }

    public void setEntryUserId(Integer entryUserId) {
        this.entryUserId = entryUserId;
    }
    
    public String getEntryUserName() {
        return entryUserName;
    }

    public void setEntryUserName(String entryUserName) {
        this.entryUserName = entryUserName;
    }
    
    public LocalDateTime getEntryUserTime() {
        return entryUserTime;
    }

    public void setEntryUserTime(LocalDateTime entryUserTime) {
        this.entryUserTime = entryUserTime;
    }
    
    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
    
    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
    
    public LocalDateTime getUpdateUserTime() {
        return updateUserTime;
    }

    public void setUpdateUserTime(LocalDateTime updateUserTime) {
        this.updateUserTime = updateUserTime;
    }
    
    @Override
    public String toString() {
        return "SysRole{" +
        "roleId=" + roleId +
        ", roleName=" + roleName +
        ", roleDescribe=" + roleDescribe +
        ", status=" + status +
        ", entryUserId=" + entryUserId +
        ", entryUserName=" + entryUserName +
        ", entryUserTime=" + entryUserTime +
        ", updateUserId=" + updateUserId +
        ", updateUserName=" + updateUserName +
        ", updateUserTime=" + updateUserTime +
        "}";
    }
}
