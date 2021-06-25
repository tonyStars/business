package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 公司表
 * </p>
 *
 * @author 
 * @date 2019-12-09
 */
public class SysCompany implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId(value = "COMPANY_ID", type = IdType.AUTO)
    private Integer companyId;
    
    /**
     * 父公司id
     */
    @TableField("PARENT_ID")
    private Integer parentId;

    /**
     * 父公司名称
     */
    @TableField("PARENT_NAME")
    private String parentName;
    
    /**
     * 公司编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;
    
    /**
     * 公司全称
     */
    @TableField("COMPANY_NAME")
    private String companyName;
    
    /**
     * 公司简称
     */
    @TableField("NAME_SHORT")
    private String nameShort;
    
    /**
     * 联系人
     */
    @TableField("CONTACT_PERSON")
    private String contactPerson;
    
    /**
     * 联系电话
     */
    @TableField("CONTACT_PHONE")
    private String contactPhone;
    
    /**
     * 省id
     */
    @TableField("STATE_CODE")
    private Integer stateCode;
    
    /**
     * 省
     */
    @TableField("STATE_NAME")
    private String stateName;
    
    /**
     * 市id
     */
    @TableField("CITY_CODE")
    private Integer cityCode;
    
    /**
     * 市
     */
    @TableField("CITY_NAME")
    private String cityName;
    
    /**
     * 区id
     */
    @TableField("COUNTY_CODE")
    private Integer countyCode;
    
    /**
     * 区
     */
    @TableField("COUNTY_NAME")
    private String countyName;
    
    /**
     * 所属大区（华中、华南、华北、华东）
     */
    @TableField("CITY_REGION")
    private Integer cityRegion;
    
    /**
     * 地址
     */
    @TableField("ADDR")
    private String addr;
    
    /**
     * 公司类型：99-不限，0-总部、1-分公司、2-办事处、3-站点
     */
    @TableField("TYPE_CODE")
    private Integer typeCode;
    
    /**
     * 公司级别（0-总公司1-分公司、2-办事处、站点）
     */
    @TableField("COMPANY_LEVEL")
    private Integer companyLevel;
    
    /**
     * 公司简介
     */
    @TableField("NOTE")
    private String note;
    
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

    /**
     * 省市区id集合
     */
    @TableField(exist = false)
    private String addressIds;

    /**
     * 省市区名称集合
     */
    @TableField(exist = false)
    private String addressNames;
    
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }
    
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    
    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }
    
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public Integer getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(Integer countyCode) {
        this.countyCode = countyCode;
    }
    
    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
    
    public Integer getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(Integer cityRegion) {
        this.cityRegion = cityRegion;
    }
    
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }
    
    public Integer getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(Integer companyLevel) {
        this.companyLevel = companyLevel;
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getAddressIds() {
        return addressIds;
    }

    public void setAddressIds(String addressIds) {
        this.addressIds = addressIds;
    }

    public String getAddressNames() {
        return addressNames;
    }

    public void setAddressNames(String addressNames) {
        this.addressNames = addressNames;
    }

    @Override
    public String toString() {
        return "SysCompany{" +
        "companyId=" + companyId +
        ", parentId=" + parentId +
        ", companyCode=" + companyCode +
        ", companyName=" + companyName +
        ", nameShort=" + nameShort +
        ", contactPerson=" + contactPerson +
        ", contactPhone=" + contactPhone +
        ", stateCode=" + stateCode +
        ", stateName=" + stateName +
        ", cityCode=" + cityCode +
        ", cityName=" + cityName +
        ", countyCode=" + countyCode +
        ", countyName=" + countyName +
        ", cityRegion=" + cityRegion +
        ", addr=" + addr +
        ", typeCode=" + typeCode +
        ", companyLevel=" + companyLevel +
        ", note=" + note +
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
