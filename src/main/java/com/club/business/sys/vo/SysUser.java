package com.club.business.sys.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId(value = "USER_ID", type = IdType.AUTO)
    private Integer userId;
    
    /**
     * 员工账号
     */
    @Excel(name = "员工账号" ,orderNum = "1")
    @TableField("USER_CODE")
    private String userCode;
    
    /**
     * 员工姓名
     */
    @Excel(name = "员工姓名" ,orderNum = "2")
    @TableField("USER_NAME")
    private String userName;
    
    /**
     * 员工职务（数据字典）
     */
    @Excel(needMerge = true,name = "员工职务",replace = { "董事长_1", "总经理_2", "总部财务总监_3", "总部销售总监_4", "总部技术总监_5", "总部运营总监_6", "总部行政总监_7"} ,orderNum = "3")
    @TableField("USER_DUTY")
    private Integer userDuty;
    
    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;
    
    /**
     * 身份证号
     */
    @Excel(name = "身份证号" ,orderNum = "4")
    @TableField("ID_CARD")
    private String idCard;
    
    /**
     * 手机号码
     */
    @Excel(name = "手机号码" ,orderNum = "5")
    @TableField("PHONE")
    private String phone;
    
    /**
     * 头像
     */
    @TableField("IMG_URL")
    private String imgUrl;
    
    /**
     * 性别：男、女
     */
    @Excel(needMerge = true,name = "性别：男、女",replace = { "男_0", "女_1"} ,orderNum = "6")
    @TableField("SEX")
    private Integer sex;
    
    /**
     * 工作类型（数据字典）
     */
    @Excel(needMerge = true,name = "工作类型",replace = { "正式_1", "试用_2", "临时_3"} ,orderNum = "7")
    @TableField("TYPE")
    private Integer type;
    
    /**
     * 备注
     */
    @Excel(name = "备注" ,orderNum = "8")
    @TableField("NOTE")
    private String note;
    
    /**
     * 状态：0-正常、-1-停用
     */
    @Excel(needMerge = true,name = "状态：0-正常、-1-停用",replace = { "正常_0", "停用_-1"} ,orderNum = "9")
    @TableField("STATUS")
    private Integer status;
    
    /**
     * 所属公司id
     */
    @TableField("COMPANY_ID")
    private Integer companyId;
    
    /**
     * 创建人id
     */
    @TableField("ENTRY_USER_ID")
    private Integer entryUserId;
    
    /**
     * 创建人
     */
    @Excel(name = "创建人" ,orderNum = "10")
    @TableField("ENTRY_USER_NAME")
    private String entryUserName;
    
    /**
     * 创建时间
     */
    @Excel(name="创建时间",orderNum = "11",width = 12D,format = "yyyy-MM-dd HH:mm:ss")
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
     * 公司名称
     */
    @TableField(exist = false)
    private String companyName;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Integer getUserDuty() {
        return userDuty;
    }

    public void setUserDuty(Integer userDuty) {
        this.userDuty = userDuty;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
    
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
    
    @Override
    public String toString() {
        return "SysUser{" +
        "userId=" + userId +
        ", userCode=" + userCode +
        ", userName=" + userName +
        ", userDuty=" + userDuty +
        ", password=" + password +
        ", idCard=" + idCard +
        ", phone=" + phone +
        ", imgUrl=" + imgUrl +
        ", sex=" + sex +
        ", type=" + type +
        ", note=" + note +
        ", status=" + status +
        ", companyId=" + companyId +
        ", entryUserId=" + entryUserId +
        ", entryUserName=" + entryUserName +
        ", entryUserTime=" + entryUserTime +
        ", updateUserId=" + updateUserId +
        ", updateUserName=" + updateUserName +
        ", updateUserTime=" + updateUserTime +
        "}";
    }
}
