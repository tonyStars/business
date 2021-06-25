package com.club.business.common.vo;

import java.util.List;

/**
 * 菜单对象
 *
 * @author tony
 * @date 2019-11-19
 */
public class MenuBean {

	/**菜单ID*/
	private String privilegeId;
	/**菜单名称*/
	private String privilegeName;
	/**父级菜单id*/
	private String parentId;
	/**父级菜单名称*/
	private String parentName;
	/**跳转地址*/
	private String href;
	/**菜单标题*/
	private String title;
	/**菜单图标*/
	private String icon;
	/**是否展开*/
	private Boolean isCurrent;
	/**排序*/
	private Integer orders;
	/**子菜单集合*/
	private List<MenuBean> children;

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public List<MenuBean> getChildren() {
		return children;
	}

	public void setChildren(List<MenuBean> children) {
		this.children = children;
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

	@Override
	public String toString() {
		return "MenuBean{" +
				"privilegeId='" + privilegeId + '\'' +
				", privilegeName='" + privilegeName + '\'' +
				", parentId='" + parentId + '\'' +
				", parentName='" + parentName + '\'' +
				", href='" + href + '\'' +
				", title='" + title + '\'' +
				", icon='" + icon + '\'' +
				", isCurrent=" + isCurrent +
				", orders=" + orders +
				", children=" + children +
				'}';
	}
}
