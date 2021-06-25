package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 菜单功能表
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
public class SysPrivilege implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId(value = "PRIVILEGE_ID", type = IdType.AUTO)
    private Integer privilegeId;
    
    /**
     * 父权限id
     */
    @TableField("PARENT_ID")
    private Integer parentId;
    
    /**
     * 权限名
     */
    @TableField("PRIVILEGE_NAME")
    private String privilegeName;
    
    /**
     * 父权限名
     */
    @TableField("PARENT_NAME")
    private String parentName;
    
    /**
     * URL
     */
    @TableField("URL")
    private String url;
    
    /**
     * 类型；0-菜单；1-按钮；2-导航
     */
    @TableField("TP")
    private Integer tp;
    
    /**
     * 菜单级别
     */
    @TableField("GRADE")
    private Integer grade;
    
    /**
     * 权限代码
     */
    @TableField("CODE")
    private String code;
    
    /**
     * 排序
     */
    @TableField("SORT")
    private Integer sort;
    
    /**
     * 状态：0-正常、-1-作废
     */
    @TableField("STATUS")
    private Integer status;
    
    /**
     * 图标
     */
    @TableField("ICON")
    private String icon;
    
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

    /**
     * 按钮父级菜单id(新增表单使用)
     */
    @TableField(exist = false)
    private Integer parentIdB;

    /**
     * 按钮父级菜单名称(新增表单使用)
     */
    @TableField(exist = false)
    private String parentNameB;

    /**
     * 按钮名称(新增表单使用)
     */
    @TableField(exist = false)
    private String privilegeNameB;

    /**
     * 按钮URL(新增表单使用)
     */
    @TableField(exist = false)
    private String urlB;
    
    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }
    
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }
    
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public Integer getTp() {
        return tp;
    }

    public void setTp(Integer tp) {
        this.tp = tp;
    }
    
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Integer getParentIdB() {
        return parentIdB;
    }

    public void setParentIdB(Integer parentIdB) {
        this.parentIdB = parentIdB;
    }

    public String getParentNameB() {
        return parentNameB;
    }

    public void setParentNameB(String parentNameB) {
        this.parentNameB = parentNameB;
    }

    public String getPrivilegeNameB() {
        return privilegeNameB;
    }

    public void setPrivilegeNameB(String privilegeNameB) {
        this.privilegeNameB = privilegeNameB;
    }

    public String getUrlB() {
        return urlB;
    }

    public void setUrlB(String urlB) {
        this.urlB = urlB;
    }

    @Override
    public String toString() {
        return "SysPrivilege{" +
        "privilegeId=" + privilegeId +
        ", parentId=" + parentId +
        ", privilegeName=" + privilegeName +
        ", parentName=" + parentName +
        ", url=" + url +
        ", tp=" + tp +
        ", grade=" + grade +
        ", code=" + code +
        ", sort=" + sort +
        ", status=" + status +
        ", icon=" + icon +
        ", entryUserId=" + entryUserId +
        ", entryUserName=" + entryUserName +
        ", entryUserTime=" + entryUserTime +
        ", updateUserId=" + updateUserId +
        ", updateUserName=" + updateUserName +
        ", updateUserTime=" + updateUserTime +
        "}";
    }
}
