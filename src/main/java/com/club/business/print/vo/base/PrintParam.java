package com.club.business.print.vo.base;

/** 
 * 打印参数
 * @author Tom
 * @date 2018年12月10日
 * 
 */
public class PrintParam {
	
	/**
	 * 打印单据类型,必填
	 */
	private int printType;
	
	/**
	 * 打印单据Id字符串，必填
	 */
	private String billIds;
	
	/**
	 * 货主Id
	 */
	private Long ownerId;
	
	/**
	 * 公司Id
	 */
	private Integer companyId;
	
	/**
	 * 打印图像的根url
	 */
	private String baseUrl;

	public int getPrintType() {
		return printType;
	}

	public void setPrintType(int printType) {
		this.printType = printType;
	}

	public String getBillIds() {
		return billIds;
	}

	public void setBillIds(String billIds) {
		this.billIds = billIds;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
}
