package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用户id
     */
    @TableField("USER_ID")
    private Integer userId;
    
    /**
     * 角色id
     */
    @TableField("ROLE_ID")
    private Integer roleId;
    
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
    
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    
    @Override
    public String toString() {
        return "SysUserRole{" +
        "id=" + id +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
