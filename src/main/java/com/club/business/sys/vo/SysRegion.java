package com.club.business.sys.vo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 地区表
 * </p>
 *
 * @author 
 * @date 2019-12-09
 */
public class SysRegion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId(value = "REGION_ID", type = IdType.AUTO)
    private Integer regionId;
    
    /**
     * 区域名称
     */
    @TableField("NAME")
    private String name;
    
    /**
     * 区域编码
     */
    @TableField("CODE")
    private String code;
    
    /**
     * 排序
     */
    @TableField("SORT")
    private Integer sort;
    
    /**
     * 父地区id
     */
    @TableField("PARENT_ID")
    private Integer parentId;

    /**
     * 父地区名称
     */
    @TableField("PARENT_NAME")
    private String parentName;
    
    /**
     * 助记码
     */
    @TableField("PINYIN")
    private String pinyin;
    
    /**
     * 所属大区（华中、华南、华北、华东）
     */
    @TableField("CITY_REGION")
    private Integer cityRegion;
    
    /**
     * 类型：0-国家、1-省、2-市、3-区、9-直辖市
     */
    @TableField("REGION_TYPE")
    private Integer regionType;
    
    /**
     * 经度
     */
    @TableField("LONGITUDE")
    private BigDecimal longitude;
    
    /**
     * 纬度
     */
    @TableField("LATITUDE")
    private BigDecimal latitude;
    
    /**
     * 是否可操作（0不可以操作 1可以操作  ）
     */
    @TableField("ISSERVICE")
    private Integer isservice;
    
    /**
     * 状态：0-正常、-1-作废
     */
    @TableField("STATUS")
    private Integer status;
    
    /**
     * 备注
     */
    @TableField("NOTE")
    private String note;
    
    /**
     * 创建人
     */
    @TableField("ADD_NAME")
    private String addName;
    
    /**
     * 创建人id
     */
    @TableField("ADD_USER_ID")
    private Integer addUserId;
    
    /**
     * 创建时间
     */
    @TableField("ADD_USER__TIME")
    private LocalDateTime addUserTime;
    
    /**
     * 最后更新人
     */
    @TableField("LAST_UPDATE_NAME")
    private String lastUpdateName;
    
    /**
     * 最后更新人id
     */
    @TableField("LAST_UPDATE_ID")
    private Integer lastUpdateId;
    
    /**
     * 最后更新时间
     */
    @TableField("LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;
    
    /**
     * 同步时间
     */
    @TableField("SYN_TIME")
    private LocalDateTime synTime;
    
    /**
     * 同步状态 0：成功 1：失败
     */
    @TableField("SYN_FLAG")
    private Integer synFlag;
    
    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    
    public Integer getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(Integer cityRegion) {
        this.cityRegion = cityRegion;
    }
    
    public Integer getRegionType() {
        return regionType;
    }

    public void setRegionType(Integer regionType) {
        this.regionType = regionType;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public Integer getIsservice() {
        return isservice;
    }

    public void setIsservice(Integer isservice) {
        this.isservice = isservice;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }
    
    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }
    
    public LocalDateTime getAddUserTime() {
        return addUserTime;
    }

    public void setAddUserTime(LocalDateTime addUserTime) {
        this.addUserTime = addUserTime;
    }
    
    public String getLastUpdateName() {
        return lastUpdateName;
    }

    public void setLastUpdateName(String lastUpdateName) {
        this.lastUpdateName = lastUpdateName;
    }
    
    public Integer getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(Integer lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }
    
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    public LocalDateTime getSynTime() {
        return synTime;
    }

    public void setSynTime(LocalDateTime synTime) {
        this.synTime = synTime;
    }
    
    public Integer getSynFlag() {
        return synFlag;
    }

    public void setSynFlag(Integer synFlag) {
        this.synFlag = synFlag;
    }
    
    @Override
    public String toString() {
        return "SysRegion{" +
        "regionId=" + regionId +
        ", name=" + name +
        ", code=" + code +
        ", sort=" + sort +
        ", parentId=" + parentId +
        ", pinyin=" + pinyin +
        ", cityRegion=" + cityRegion +
        ", regionType=" + regionType +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", isservice=" + isservice +
        ", status=" + status +
        ", note=" + note +
        ", addName=" + addName +
        ", addUserId=" + addUserId +
        ", addUserTime=" + addUserTime +
        ", lastUpdateName=" + lastUpdateName +
        ", lastUpdateId=" + lastUpdateId +
        ", lastUpdateTime=" + lastUpdateTime +
        ", synTime=" + synTime +
        ", synFlag=" + synFlag +
        "}";
    }
}
