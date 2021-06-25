package com.club.business.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.sys.vo.*;
import com.club.business.sys.dao.SysRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> {

    @Autowired
    private SysRolePrivilegeServiceImpl rolePrivilegeService;

    @Autowired
    private SysUserRoleServiceImpl userRoleService;
    /**
     * 角色查询
     * @param page 当前页
     * @param pageSize 页面大小
     * @param roleName 角色名称
     * @param status 状态
     * @return
     * @throws Exception
     */
    public Map<String, Object> search(int page, int pageSize, String roleName, String status) throws Exception{
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(roleName)){
            qw.like("ROLE_NAME",roleName);
        }
        if(StringUtils.isNotBlank(status)){
            qw.eq("STATUS",status);
        }
        Page<SysRole> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysRole> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 新增角色
     * @param role 角色对象
     * @param user 登陆用户
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void add(SysRole role, SysUser user) throws Exception{
        role.setEntryUserId(user.getUserId());
        role.setEntryUserName(user.getUserName());
        role.setEntryUserTime(LocalDateTime.now());
        this.save(role);
    }

    /**
     * 修改角色
     * @param model
     * @param user
     */
    public void edit(SysRole model, SysUser user) throws Exception {
        model.setUpdateUserId(user.getUserId());
        model.setUpdateUserName(user.getUserName());
        model.setUpdateUserTime(LocalDateTime.now());
        this.updateById(model);
    }

    /**
     * 启用角色
     * @param ids
     */
    public void ok(String ids) throws Exception {
        List<SysRole> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysRole model = new SysRole();
            model.setRoleId(Integer.valueOf(idsTemp[i]));
            model = this.getById(idsTemp[i]);
            if (model == null) {
                throw new BusinessException("未找到要操作的数据！");
            }
            /**系统数据不允许删除*/
            if (model.getRoleName().equals("系统管理员")) {
                throw new BusinessException("系统管理员禁止操作！");
            }
            if (model.getStatus().toString().equals("0")) {
                throw new BusinessException("请勿选择已启用的数据！");
            } else {
                model.setStatus(0);
            }
            list.add(model);
        }
        this.updateBatchById(list);
    }

    /**
     * 停用角色
     * @param ids
     */
    public void del(String ids) throws Exception {
        List<SysRole> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysRole model = new SysRole();
            model.setRoleId(Integer.valueOf(idsTemp[i]));
            model = this.getById(idsTemp[i]);
            if (model == null) {
                throw new BusinessException("未找到要操作的数据！");
            }
            /**系统数据不允许删除*/
            if (model.getRoleName().equals("系统管理员")) {
                throw new BusinessException("系统管理员禁止操作！");
            }
            if (model.getStatus().toString().equals("-1")) {
                throw new BusinessException("请勿选择已停用的数据！");
            } else {
                model.setStatus(-1);
            }
            list.add(model);
        }
        this.updateBatchById(list);
    }

    /**
     * 保存选择的菜单权限
     * @param menuIds 保存的菜单权限id集合
     * @param roleId 保存菜单权限的角色id
     */
    @Transactional(rollbackFor=Exception.class)
    public void addRoleMenuAuth(String menuIds, String roleId) throws Exception {
        SysRolePrivilege rp = new SysRolePrivilege();
        String[] ids = menuIds.split(",");
        rp.setRoleId(Integer.valueOf(roleId));
        List<SysRolePrivilege> ifHas = rolePrivilegeService.list(new QueryWrapper<>(rp));
        boolean b;
        if(ifHas != null && ifHas.size() > 0){
            b = rolePrivilegeService.remove(new UpdateWrapper<>(rp));
        }else{
            b = true;
        }
        if(b) {
            if(ids != null && ids.length > 0 && ids[0] !=""){
                List<SysRolePrivilege> list = new ArrayList<>();
                for (int i = 0; i < ids.length; i++) {
                    SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
                    sysRolePrivilege.setPrivilegeId(Integer.valueOf(ids[i]));
                    sysRolePrivilege.setRoleId(Integer.valueOf(roleId));
                    list.add(sysRolePrivilege);
                }
                rolePrivilegeService.saveBatch(list);
            }
        }
    }

    /**
     * 查询所有角色(正常)
     * @param userId
     * @return
     * @throws Exception
     */
    public List<RoleBaseModel> searchList(String userId) throws Exception{
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("STATUS",0);
        List<SysRole> list = this.list(qw);
        QueryWrapper<SysUserRole> ur = new QueryWrapper<>();
        ur.eq("USER_ID",userId);
        List<SysUserRole> urList = userRoleService.list(ur);
        List<Integer> roSelect = new ArrayList<>();
        if(urList != null && urList.size() > 0){
            for(SysUserRole rr : urList){
                roSelect.add(rr.getRoleId());
            }
        }
        List<RoleBaseModel> models = new ArrayList<>();
        if(list != null && list.size() > 0){
            for(SysRole r : list){
                RoleBaseModel model = new RoleBaseModel();
                model.setName(r.getRoleName());
                model.setValue(r.getRoleId());
                if(roSelect.contains(r.getRoleId())){
                    model.setSelected(true);
                }else{
                    model.setSelected(false);
                }
                models.add(model);
            }
        }
        return models;
    }
}
