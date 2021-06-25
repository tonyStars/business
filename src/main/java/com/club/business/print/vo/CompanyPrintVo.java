package com.club.business.print.vo;

import com.club.business.print.vo.base.BasePrintVo;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description  打印主表对象
 *
 * @author tony
 * @date 2019/7/3
 */
public class CompanyPrintVo extends BasePrintVo<CompanyItemPrintVo> {

	/**
	 * 父级公司名称
	 */
	private String parentName;

	/**
	 * 公司编码
	 */
	private String companyCode;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 联系人
	 */
	private String contactPerson;

	/**
	 * 联系电话
	 */
	private String contactPhone;

	/**
	 * 省份
	 */
	private String stateName;

	/**
	 * 城市
	 */
	private String cityName;

	/**
	 * 城镇
	 */
	private String countyName;

	/**
	 * 所属大区(华中、华南、华北、华东)
	 */
	private String cityRegion;

	/**
	 * 地址
	 */
	private String addr;

	/**
	 * 明细表
	 */
	private List<CompanyItemPrintVo> detailList = new ArrayList<>();

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String getCompanyCode() {
		return companyCode;
	}

	@Override
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public String getCompanyName() {
		return companyName;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCityRegion() {
		return cityRegion;
	}

	public void setCityRegion(String cityRegion) {
		this.cityRegion = cityRegion;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public List<CompanyItemPrintVo> getDetailList() {
		return detailList;
	}

	@Override
	public void setDetailList(List<CompanyItemPrintVo> detailList) {
		this.detailList = detailList;
	}
}
