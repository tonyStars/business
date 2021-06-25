package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
public class SysRolePrivilege implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 角色id
     */
    @TableField("ROLE_ID")
    private Integer roleId;
    
    /**
     * 权限id
     */
    @TableField("PRIVILEGE_ID")
    private Integer privilegeId;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    
    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }
    
    @Override
    public String toString() {
        return "SysRolePrivilege{" +
        "id=" + id +
        ", roleId=" + roleId +
        ", privilegeId=" + privilegeId +
        "}";
    }
}
