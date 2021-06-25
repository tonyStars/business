package com.club.business.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.club.business.common.vo.MenuBean;
import com.club.business.sys.vo.SysPrivilege;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
    * 登录 服务实现类
    * </p>
* @author Tom
* @since 2019-11-19
*/
@Service
public class SysLoginServiceImpl{

	private final static String USER_ADMIN = "admin";

    @Autowired
    private SysUserServiceImpl userService;

    @Autowired
    private SysPrivilegeServiceImpl rivilegeService;

    /**
     * 登录接口
     * @param account 登陆账户
     * @param password 登陆密码
     * @return
     * @throws Exception
     */
	public Map<String, Object> login(String account, String password) throws Exception{
		Map<String, Object> returnMap = new HashMap<>(3);
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("USER_CODE",account);
		SysUser user = userService.getOne(qw);
		if(user == null){
            throw new BusinessException("该账号不存在!");
        }
        if(user.getStatus().intValue() == -1){
            throw new BusinessException("该账号已停用!");
        }
        if(!user.getPassword().equals(password)){
            throw new BusinessException("密码输入错误!");
        }
		List<SysPrivilege> menuList = this.getRoleMenu(user);
		List<SysPrivilege> buttonList = this.getRoleButton(user);
		returnMap.put("user",user);
		returnMap.put("menuList",menuList);
		returnMap.put("buttonList",buttonList);
		return returnMap;
	}

    /**
     * 遍历当前账号所有角色获取所有权限菜单(去除作废角色菜单)
     * @param user 用户对象
     * @return
     * @throws Exception
     */
	public List<SysPrivilege> getRoleMenu(SysUser user) throws Exception {
		List<SysPrivilege> list = new ArrayList<>();
        QueryWrapper<SysPrivilege> qw = new QueryWrapper<>();
		/**判断是否是系统管理员*/
		if (USER_ADMIN.equals(user.getUserCode())) {
            qw.eq("TP", 0).eq("STATUS", 0);
            list = rivilegeService.list(qw);
		} else {
            list = rivilegeService.queryPrivilegesByUserId(user.getUserId());
		}
		return list;
	}

	/**
	 * 遍历当前账号所有角色获取所有按钮菜单(去除作废角色按钮)
	 */
	public List<SysPrivilege> getRoleButton(SysUser user) throws Exception {
		List<SysPrivilege> list = new ArrayList<>();
        /**"admin"用户不受权限管理控制*/
		if (USER_ADMIN.equals(user.getUserCode())) {
			QueryWrapper<SysPrivilege> qw = new QueryWrapper<>();
            qw.eq("TP", 1).eq("STATUS", 0);
            list = rivilegeService.list(qw);
		} else {
            list = rivilegeService.queryButtomsByUserId(user.getUserId());
		}
		return list;
	}

	/**
	 * 系统菜单转换 - (最多三级菜单)
	 * @param menuList 菜单集合
	 * @return
	 */
	public List<MenuBean> menuTransfer(List<SysPrivilege> menuList) throws Exception {
		List<MenuBean> result = new ArrayList<>();
		if(menuList != null && menuList.size() > 0){
			List<SysPrivilege> menuLevel1 = new ArrayList<>();
			List<SysPrivilege> menuLevel2 = new ArrayList<>();
			List<SysPrivilege> menuLevel3 = new ArrayList<>();
			for (SysPrivilege mm : menuList) {
				/**遍历获取每个菜单的菜单等级grade,目前只有三级菜单,推荐最多三级菜单,如有更多则继续添加*/
				int grade = mm.getGrade();
				if (grade == 1) {
					menuLevel1.add(mm);
				}
				if (grade == 2) {
					menuLevel2.add(mm);
				}
				if (grade == 3) {
					menuLevel3.add(mm);
				}
			}
			/**对一级,二级,三级菜单按照 getSort(排序字段) 进行升序排序*/
			menuLevel1 = menuLevel1.stream().sorted(Comparator.comparing(SysPrivilege::getSort)).collect(Collectors.toList());
			menuLevel2 = menuLevel2.stream().sorted(Comparator.comparing(SysPrivilege::getSort)).collect(Collectors.toList());
			menuLevel3 = menuLevel3.stream().sorted(Comparator.comparing(SysPrivilege::getSort)).collect(Collectors.toList());
			result = getResultMenu(menuLevel1,menuLevel2,menuLevel3);
		}
		return result;
	}

	/**
	 * 获取菜单MenuBean封装完成的集合
	 * @param menuLevel1 一级菜单
	 * @param menuLevel2 二级菜单
	 * @param menuLevel3 三级菜单
	 * @return
	 * @throws Exception
	 */
	public List<MenuBean> getResultMenu(List<SysPrivilege> menuLevel1,List<SysPrivilege> menuLevel2,List<SysPrivilege> menuLevel3) throws Exception{
		List<MenuBean> result = new ArrayList<>();
		if(menuLevel1 != null && menuLevel1.size() > 0){
			for (int i = 0; i < menuLevel1.size(); i++) {
				List<MenuBean> childrenList2 = new ArrayList<>();
				SysPrivilege func1 = menuLevel1.get(i);
				MenuBean bean = new MenuBean();
				bean = getMenuBean(i,func1);
				if(menuLevel2 != null && menuLevel2.size() > 0){
					for (int j = 0; j < menuLevel2.size(); j++) {
						List<MenuBean> childrenList3 = new ArrayList<>();
						SysPrivilege func2 = menuLevel2.get(j);
						MenuBean beanChildren2 = new MenuBean();
						if (func2.getParentId().toString().equals(func1.getPrivilegeId() + "")) {
							beanChildren2 = getMenuBean(j,func2);
							if(menuLevel3 != null && menuLevel3.size() > 0){
								for (int k = 0; k < menuLevel3.size(); k++) {
									SysPrivilege func3 = menuLevel3.get(k);
									if (func3.getParentId().toString().equals(func2.getPrivilegeId() + "")) {
										MenuBean beanChildren3 = new MenuBean();
										beanChildren3 = getMenuBean(k,func3);
										beanChildren3.setChildren(null);
										childrenList3.add(beanChildren3);
									}
								}
							}
							beanChildren2.setChildren(childrenList3);
							childrenList2.add(beanChildren2);
						}
					}
				}
				bean.setChildren(childrenList2);
				result.add(bean);
			}
		}
		return result;
	}

	/**
	 * 封装每个MenuBean菜单对象
	 * @param i 循环参数 i
	 * @param func 菜单对象SysPrivilege
	 * @return
	 * @throws Exception
	 */
	public MenuBean getMenuBean(int i,SysPrivilege func) throws Exception{
		MenuBean bean = new MenuBean();
		bean.setPrivilegeId(func.getPrivilegeId().toString());
		bean.setPrivilegeName(func.getPrivilegeName());
		bean.setTitle(func.getPrivilegeName());
		bean.setIcon(func.getIcon());
		bean.setHref(func.getUrl() + "?menuId=" + func.getPrivilegeId());
		bean.setParentId(func.getParentId().toString());
		bean.setParentName(func.getParentName());
		bean.setOrders(func.getSort());
		/**判断是否是第一个菜单,第一个菜单设置展开,其它都设置闭合*/
		if (i == 0) {
			bean.setIsCurrent(true);
		} else {
			bean.setIsCurrent(false);
		}
		return bean;
	}

	/**
	 * 修改用户密码
	 * @param orgpass
	 * @param password
	 * @param user
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updatePwd(String orgpass, String password, SysUser user) throws Exception {
		if(!user.getPassword().equals(orgpass)){
			throw new BusinessException("旧密码输入不正确!");
		}
		if(orgpass.equals(password)){
			throw new BusinessException("新密码和旧密码相同!");
		}
		user.setPassword(password);
		userService.updateById(user);
	}
}
