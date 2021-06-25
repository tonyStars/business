package com.club.business.sys.vo;

/**
 * 角色下拉复选框对象
 *
 * @author Tom
 * @date 2019-12-06
 */
public class RoleBaseModel {

    private String name;

    private Integer value;

    private boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
