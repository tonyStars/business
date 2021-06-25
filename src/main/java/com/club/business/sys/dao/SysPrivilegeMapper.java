package com.club.business.sys.dao;

import com.club.business.sys.vo.SysPrivilege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单功能表 Mapper 接口
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {

    /**
     * 根据用户所有的角色集合查询功能权限菜单集合
     * @param userId 用户id
     * @return
     */
    List<SysPrivilege> queryPrivilegesByUserId(Integer userId);

    /**
     * 遍历当前账号所有角色获取所有按钮菜单(去除作废角色按钮)
     * @param userId 用户id
     * @return
     */
    List<SysPrivilege> queryButtomsByUserId(Integer userId);
}
