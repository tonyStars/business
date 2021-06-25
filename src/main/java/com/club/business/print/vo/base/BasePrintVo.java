package com.club.business.print.vo.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @author Tom
 * @date 2019-12-16
 */
public class BasePrintVo<M extends BaseDetailPrintVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 外部单号
	 */
	private String exNo;

	/**
	 * 创建人
	 */
	private String entryUserName;

	/**
	 * 创建时间
	 */
	private Date entryUserTime;

	/**
	 * 所属公司id
	 */
	private Integer companyId;

	/**
	 * 公司名
	 */
	private String companyName;

	/**
	 * 公司编码
	 */
	private String companyCode;

	/**
	 * 货主id
	 */
	private Long ownerId;

	/**
	 * 货主编码
	 */
	private String ownerCode;

	/**
	 * 货主名称
	 */
	private String ownerName;

	/**
	 * 条形码
	 */
	private String barCode;
	
	/**
	 * 图片打印的根url
	 */
	private String baseUrl;

	/**
	 * 总页数
	 */
	private int totalCount;

	/**
	 * 当前页码
	 */
	private int curPageNum;

	/**
	 * 每页条数
	 */
	private int pageSize;
	
	/**
	 * 每页初始序号
	 */
	private int initIndex;
	
	/**
	 * 扩展属性字段
	 */
	private List<String> fieldNameList = new ArrayList<>();
	
	/**
	 * 扩展属性名称
	 */
	private List<String> propertyNameList = new ArrayList<>();
	
	/**
	 * 打印页脚
	 */
	private String printFooter;
	
	public String getExNo() {
		return exNo;
	}
	
	public void setExNo(String exNo) {
		this.exNo = exNo;
	}
	
	public String getEntryUserName() {
		return entryUserName;
	}

	public void setEntryUserName(String entryUserName) {
		this.entryUserName = entryUserName;
	}

	public Date getEntryUserTime() {
		return entryUserTime;
	}

	public void setEntryUserTime(Date entryUserTime) {
		this.entryUserTime = entryUserTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurPageNum() {
		return curPageNum;
	}

	public void setCurPageNum(int curPageNum) {
		this.curPageNum = curPageNum;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<String> getFieldNameList() {
		return fieldNameList;
	}

	public void setFieldNameList(List<String> fieldNameList) {
		this.fieldNameList = fieldNameList;
	}

	public List<String> getPropertyNameList() {
		return propertyNameList;
	}

	public void setPropertyNameList(List<String> propertyNameList) {
		this.propertyNameList = propertyNameList;
	}

	public int getInitIndex() {
		return initIndex;
	}

	public void setInitIndex(int initIndex) {
		this.initIndex = initIndex;
	}
	
	public String getPrintFooter() {
		return printFooter;
	}

	public void setPrintFooter(String printFooter) {
		this.printFooter = printFooter;
	}
	
	public List<M> getDetailList() {
		return null;
	}
	 
	public void setDetailList(List<M> detailList) {
	}
	
}
