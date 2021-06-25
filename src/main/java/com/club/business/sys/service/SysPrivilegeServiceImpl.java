package com.club.business.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.club.business.sys.vo.SysPrivilege;
import com.club.business.sys.dao.SysPrivilegeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.sys.vo.SysRole;
import com.club.business.sys.vo.SysRolePrivilege;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>
 * 菜单功能表 服务实现类
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper,SysPrivilege> {

    @Autowired
    private SysRoleServiceImpl roleService;

    @Autowired
    private SysRolePrivilegeServiceImpl rolePrivilegeService;

    /**
     * 根据用户所有的角色集合查询功能权限菜单集合
     * @param userId 用户id
     * @return
     */
    public List<SysPrivilege> queryPrivilegesByUserId(Integer userId) throws Exception {
        return baseMapper.queryPrivilegesByUserId(userId);
    }

    /**
     * 遍历当前账号所有角色获取所有按钮菜单(去除作废角色按钮)
     * @param userId 用户id
     * @return
     */
    public List<SysPrivilege> queryButtomsByUserId(Integer userId) throws Exception {
        return baseMapper.queryButtomsByUserId(userId);
    }

    /**
     * 菜单查询
     * @return
     */
    public List<SysPrivilege> search() throws Exception {
        QueryWrapper<SysPrivilege> qw = new QueryWrapper();
        qw.eq("STATUS",0);
        return this.list(qw);
    }

    /**
     * 获取一级菜单集合
     * @return
     */
    public List<SysPrivilege> firstGrade() throws Exception {
        QueryWrapper<SysPrivilege> qw = new QueryWrapper();
        qw.eq("GRADE",1);
        qw.eq("STATUS",0);
        return this.list(qw);
    }

    /**
     * 获取二级菜单集合
     * @return
     */
    public List<SysPrivilege> secondGrade() throws Exception {
        QueryWrapper<SysPrivilege> qw = new QueryWrapper();
        qw.eq("GRADE",2);
        qw.eq("STATUS",0);
        return this.list(qw);
    }

    /**
     * 获取菜单树 - 获取系统所有的功能菜单
     * @return
     */
    public Map<String, Object> getMenuTree() {
        Map<String, Object> resultMap = new HashMap<>(1);
        try {
            /**只查找菜单*/
            QueryWrapper<SysPrivilege> qw = new QueryWrapper<>();
            qw.eq("TP",0);
            qw.eq("STATUS",0);
            List<SysPrivilege> menuList = this.list(qw);
            if(menuList != null && menuList.size() > 0){
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (SysPrivilege func :menuList){
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("id", func.getPrivilegeId());
                    map.put("pId", func.getParentId());
                    map.put("name", func.getPrivilegeName());
                    if("0".equals(func.getCode())){
                        map.put("clickExpand", true);
                    }
                    mapList.add(map);
                }
                resultMap.put("treeNodes", mapList);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 新增功能权限
     * @param model 权限对象
     * @param user 登陆用户
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void add(SysPrivilege model, SysUser user) throws Exception {
        QueryWrapper<SysPrivilege> qw = new QueryWrapper<>();
        qw.eq("STATUS",0);
        /**菜单的情况*/
        if(model.getTp().intValue() == 0){
            qw.eq("TP",0);
            if(model.getGrade().intValue() == 1){
                qw.eq("PRIVILEGE_NAME",model.getPrivilegeName());
                List<SysPrivilege> list = this.list(qw);
                if(list != null && list.size() > 0){
                    throw new BusinessException("该菜单名称已存在,请重新输入!");
                }
            }
            if(model.getGrade().intValue() == 2){
                qw.eq("PRIVILEGE_NAME",model.getPrivilegeName());
                qw.eq("PARENT_ID",model.getParentId());
                List<SysPrivilege> list = this.list(qw);
                if(list != null && list.size() > 0){
                    throw new BusinessException("在父级菜单为[ " + model.getParentName() + " ]中,该菜单名称已存在,请重新输入!");
                }
            }
            if(model.getGrade().intValue() == 3){
                qw.eq("URL",model.getUrl());
                List<SysPrivilege> list = this.list(qw);
                if(list != null && list.size() > 0){
                    throw new BusinessException("菜单URL为[ " + model.getUrl() + " ]的URL已存在,请重新输入!");
                }
                QueryWrapper<SysPrivilege> qw1 = new QueryWrapper<>();
                qw1.eq("STATUS",0);
                qw1.eq("TP",0);
                qw1.eq("PRIVILEGE_NAME",model.getPrivilegeName());
                qw1.eq("PARENT_ID",model.getParentId());
                List<SysPrivilege> list1 = this.list(qw1);
                if(list1 != null && list1.size() > 0){
                    throw new BusinessException("在父级菜单为[ " + model.getParentName() + " ]中,该菜单名称已存在,请重新输入!");
                }
            }
        }
        /**按钮的情况*/
        if(model.getTp().intValue() == 1){
            qw.eq("TP",1);
            qw.eq("PARENT_ID",model.getParentId());
            qw.eq("PRIVILEGE_NAME",model.getPrivilegeName());
            List<SysPrivilege> list = this.list(qw);
            if(list != null && list.size() > 0){
                throw new BusinessException("在父级菜单为[ " + model.getParentName() + " ]中,该按钮名称已存在,请重新输入!");
            }
            QueryWrapper<SysPrivilege> qw1 = new QueryWrapper<>();
            qw1.eq("STATUS",0);
            qw1.eq("TP",1);
            qw1.eq("PARENT_ID",model.getParentId());
            qw1.eq("URL",model.getUrl());
            List<SysPrivilege> list1 = this.list(qw1);
            if(list1 != null && list1.size() > 0){
                throw new BusinessException("在父级菜单为[ " + model.getParentName() + " ]中,按钮URL为[ " + model.getUrl() + " ]的URL已存在,请重新输入!");
            }
        }
        QueryWrapper<SysPrivilege> qwsort = new QueryWrapper<>();
        qwsort.eq("STATUS",0);
        qwsort.eq("PARENT_ID",model.getParentId());
        List<SysPrivilege> listSort = this.list(qwsort);
        Integer sortSign = 0;
        if(listSort != null && listSort.size() > 0){
            /**将查询的同级菜单根据sort字段进行升序排序,返回新的对象集合*/
            listSort = listSort.stream().sorted(Comparator.comparing(SysPrivilege::getSort)).collect(Collectors.toList());
            sortSign = listSort.get(listSort.size()-1).getSort();
        }
        model.setSort(sortSign + 1);
        model.setEntryUserId(user.getUserId());
        model.setEntryUserName(user.getUserName());
        model.setEntryUserTime(LocalDateTime.now());
        this.save(model);
    }

    /**
     * 修改权限跳转封装回显参数
     * @param id 权限表id
     * @return
     */
    public SysPrivilege toEdit(String id) throws Exception {
        SysPrivilege model = new SysPrivilege();
        SysPrivilege pvg = this.getById(id);
        /**按钮的情况封装一些参数*/
        if(pvg.getTp().intValue() == 1){
            model.setPrivilegeId(pvg.getPrivilegeId());
            model.setParentIdB(pvg.getParentId());
            model.setPrivilegeNameB(pvg.getPrivilegeName());
            model.setParentNameB(pvg.getParentName());
            model.setUrlB(pvg.getUrl());
            model.setTp(pvg.getTp());
            model.setGrade(pvg.getGrade());
            model.setCode(pvg.getCode());
            model.setSort(pvg.getSort());
            model.setStatus(pvg.getStatus());
            model.setIcon(pvg.getIcon());
        }
        /**菜单的情况封装一些参数*/
        if(pvg.getTp().intValue() == 0){
            BeanUtils.copyProperties(pvg,model);
        }
        return model;
    }

    /**
     * 修改功能权限
     * @param model 权限对象
     * @param user 登陆用户
     */
    @Transactional(rollbackFor=Exception.class)
    public void edit(SysPrivilege model, SysUser user) throws Exception {
        QueryWrapper<SysPrivilege> qw = new QueryWrapper<>();
        qw.eq("STATUS",0);
        /**菜单的情况*/
        if(model.getTp().intValue() == 0){
            qw.eq("TP",0);
            if(model.getGrade().intValue() == 1){
                qw.eq("PRIVILEGE_NAME",model.getPrivilegeName());
                /**排除自己本身,去查询和其他菜单名称是否重复*/
                qw.ne("PRIVILEGE_ID",model.getPrivilegeId());
                List<SysPrivilege> list = this.list(qw);
                if(list != null && list.size() > 0){
                    throw new BusinessException("该菜单名称已存在,请重新输入!");
                }
            }
            if(model.getGrade().intValue() == 2){
                qw.eq("PRIVILEGE_NAME",model.getPrivilegeName());
                qw.eq("PARENT_ID",model.getParentId());
                qw.ne("PRIVILEGE_ID",model.getPrivilegeId());
                List<SysPrivilege> list = this.list(qw);
                if(list != null && list.size() > 0){
                    throw new BusinessException("在父级菜单为[ " + model.getParentName() + " ]中,该菜单名称已存在,请重新输入!");
                }
            }
            if(model.getGrade().intValue() == 3){
                qw.eq("URL",model.getUrl());
                qw.ne("PRIVILEGE_ID",model.getPrivilegeId());
                List<SysPrivilege> list = this.list(qw);
                if(list != null && list.size() > 0){
                    throw new BusinessException("菜单URL为[ " + model.getUrl() + " ]的URL已存在,请重新输入!");
                }
                QueryWrapper<SysPrivilege> qw1 = new QueryWrapper<>();
                qw1.eq("STATUS",0);
                qw1.eq("TP",0);
                qw1.eq("PRIVILEGE_NAME",model.getPrivilegeName());
                qw1.eq("PARENT_ID",model.getParentId());
                qw1.ne("PRIVILEGE_ID",model.getPrivilegeId());
                List<SysPrivilege> list1 = this.list(qw1);
                if(list1 != null && list1.size() > 0){
                    throw new BusinessException("在父级菜单为[ " + model.getParentName() + " ]中,该菜单名称已存在,请重新输入!");
                }
            }
        }
        /**按钮的情况*/
        if(model.getTp().intValue() == 1){
            qw.eq("TP",1);
            qw.eq("PARENT_ID",model.getParentId());
            qw.eq("PRIVILEGE_NAME",model.getPrivilegeName());
            qw.ne("PRIVILEGE_ID",model.getPrivilegeId());
            List<SysPrivilege> list = this.list(qw);
            if(list != null && list.size() > 0){
                throw new BusinessException("在父级菜单为[ " + model.getParentName() + " ]中,该按钮名称已存在,请重新输入!");
            }
            QueryWrapper<SysPrivilege> qw1 = new QueryWrapper<>();
            qw1.eq("STATUS",0);
            qw1.eq("TP",1);
            qw1.eq("PARENT_ID",model.getParentId());
            qw1.eq("URL",model.getUrl());
            qw1.ne("PRIVILEGE_ID",model.getPrivilegeId());
            List<SysPrivilege> list1 = this.list(qw1);
            if(list1 != null && list1.size() > 0){
                throw new BusinessException("在父级菜单为[ " + model.getParentName() + " ]中,按钮URL为[ " + model.getUrl() + " ]的URL已存在,请重新输入!");
            }
        }
        model.setUpdateUserId(user.getUserId());
        model.setUpdateUserName(user.getUserName());
        model.setUpdateUserTime(LocalDateTime.now());
        this.updateById(model);
    }

    /**
     * 删除功能权限
     * @param privilegeId 权限表id
     */
    @Transactional(rollbackFor=Exception.class)
    public void del(String privilegeId) throws Exception{
        QueryWrapper<SysPrivilege> qw = new QueryWrapper<>();
        qw.eq("PARENT_ID",privilegeId);
        qw.eq("STATUS",0);
        List<SysPrivilege> list = this.list(qw);
        if(list != null && list.size() > 0){
            throw new BusinessException("该菜单有子菜单,若要删除此菜单,请删除其子菜单再操作!");
        }
        this.removeById(privilegeId);
    }

    /**
     * 排序页面按条件查询菜单集合
     * @param grade
     * @param privilegeId
     * @return
     */
    public List<SysPrivilege> sortSearch(String grade, String privilegeId) throws Exception {
        List<SysPrivilege> list = new ArrayList<>();
        QueryWrapper<SysPrivilege> qw = new QueryWrapper();
        qw.eq("STATUS",0);
        qw.orderByAsc("SORT");
        if(grade.equals("1")){
            qw.eq("GRADE",1);
            list = this.list(qw);
        }
        if(grade.equals("2") || grade.equals("3")){
            if(StringUtils.isNotBlank(privilegeId)){
                qw.eq("PARENT_ID",privilegeId);
                list = this.list(qw);
            }
        }
        return list;
    }

    /**
     * 保存排序操作
     * @param pvg 权限对象集合
     */
    @Transactional(rollbackFor=Exception.class)
    public void sort(List<SysPrivilege> pvg) throws Exception {
        if(pvg != null && pvg.size() > 0){
            /**利用java8新特性将集合对象中的排序参数去重,并返回一个新对象集合*/
            List<SysPrivilege> list = pvg.stream().collect(
                collectingAndThen(
                    toCollection(() -> new TreeSet<>(comparingInt(SysPrivilege::getSort))), ArrayList::new)
            );
            /**判断去重后的集合和去重前集合是否有相同元素*/
            if(pvg.size() == list.size()){
                this.updateBatchById(pvg);
            }else{
                throw new BusinessException("请勿设置相同的排序数字!");
            }
        }else{
            throw new BusinessException("数据异常!");
        }
    }

    /**
     * 获取菜单权限树
     * @param role 角色对象
     * @return
     */
    public Map<String, Object> getFuncTree(SysRole role) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Map<String, Object>> resultList = new ArrayList();
        /**获取所有的功能*/
        QueryWrapper<SysPrivilege> qw = new QueryWrapper<>();
        qw.eq("STATUS",0);
        List<SysPrivilege> funcList = this.list(qw);
        /**获取角色的权限*/
        role = roleService.getById(role.getRoleId());
        SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
        sysRolePrivilege.setRoleId(role.getRoleId());
        /**查询出当前角色所拥有的所有功能权限*/
        List<SysRolePrivilege> list1 = rolePrivilegeService.list(new QueryWrapper<>(sysRolePrivilege));
        List<Integer> listOp = new ArrayList<>();
        if(list1 != null && list1.size() > 0){
            for (SysRolePrivilege rp : list1) {
                listOp.add(rp.getPrivilegeId());
            }
        }
        if(funcList != null && funcList.size() > 0){
            for (SysPrivilege func : funcList){
                Map<String, Object> map = new HashMap<>(4);
                map.put("id", func.getPrivilegeId());
                map.put("pId", func.getParentId());
                map.put("name", func.getPrivilegeName());
                if("0".equals(func.getCode())){
                    map.put("open", "true");
                }
                /**回显已经有的权限*/
                if(listOp.contains(func.getPrivilegeId())) {
                    map.put("checked", "true");
                }
                resultList.add(map);
            }
        }
        resultMap.put("treeNodes", resultList);
        return resultMap;
    }
}
