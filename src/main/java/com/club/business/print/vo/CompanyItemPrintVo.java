package com.club.business.print.vo;

import com.club.business.print.vo.base.BaseDetailPrintVo;

/**
 * 打印明细表对象
 *
 * @author tony
 * @date 2019-12-16
 */
public class CompanyItemPrintVo extends BaseDetailPrintVo {

    /**
     * 银行名称： 招商银行、工商银行、农业银行等
     */
    private String bankName;

    /**
     * 开户行
     */
    private String bankInfo;

    /**
     * 账号
     */
    private String account;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 纳税人识别号
     */
    private String taxno;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String note;

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
}
