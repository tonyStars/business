package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 公司银行信息
 * </p>
 *
 * @author 
 * @date 2019-12-09
 */
public class SysCompanyBank implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 自增量
     */
    @TableId(value = "BANK_ID", type = IdType.AUTO)
    private Integer bankId;
    
    /**
     * 公司id
     */
    @TableField("COMPANY_ID")
    private Integer companyId;
    
    /**
     * 银行名称： 招商银行、工商银行、农业银行等
     */
    @TableField("BANK_NAME")
    private String bankName;
    
    /**
     * 开户行
     */
    @TableField("BANK_INFO")
    private String bankInfo;
    
    /**
     * 账号
     */
    @TableField("ACCOUNT")
    private String account;
    
    /**
     * 账户名
     */
    @TableField("ACCOUNT_NAME")
    private String accountName;
    
    /**
     * 纳税人识别号
     */
    @TableField("TAXNO")
    private String taxno;
    
    /**
     * 地址
     */
    @TableField("ADDRESS")
    private String address;
    
    /**
     * 备注
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
    
    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }
    
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }
    
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    
    @Override
    public String toString() {
        return "SysCompanyBank{" +
        "bankId=" + bankId +
        ", companyId=" + companyId +
        ", bankName=" + bankName +
        ", bankInfo=" + bankInfo +
        ", account=" + account +
        ", accountName=" + accountName +
        ", taxno=" + taxno +
        ", address=" + address +
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
