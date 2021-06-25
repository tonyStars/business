package com.club.business.print.service.impl;

import com.club.business.print.dao.CompanyPrintMapper;
import com.club.business.print.service.A4BillPrintService;
import com.club.business.print.vo.CompanyItemPrintVo;
import com.club.business.print.vo.CompanyPrintVo;
import com.club.business.print.vo.base.PrintParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/** 
 * 公司信息打印
 *
 * @author Tom
 * @date 2019-12-16
 */
@Service
public class CompanyPrintService extends A4BillPrintService<CompanyPrintVo, CompanyItemPrintVo> {
	
	@Autowired
	private CompanyPrintMapper companyPrintMapper;

	/**
	 * 重写打印模板
	 * @return
	 */
	@Override
	protected String getTemplateName() {
		return "companyprint.ftl";
	}
	
	@Override
	protected String getBarcodeValue(CompanyPrintVo companyPrint) {
		return null;
	}

	/**
	 * 重写获取打印参数方法s
	 * @param param 打印参数
	 * @return
	 */
	@Override
	protected List<CompanyPrintVo> getData(PrintParam param) {
		String[] ids = param.getBillIds().split(",");
		return companyPrintMapper.listCompanyPrintData(ids);
	}

	/**
	 * 如果有类似于数据字典字段需要重新封装对象参数的可以重写此方法
	 * @param dataList 打印数据集合
	 */
	@Override
	protected void afterGetData(List<CompanyPrintVo> dataList) {}
}
