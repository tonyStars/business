package com.club.business.print.dao;

import com.club.business.print.vo.CompanyPrintVo;
import java.util.List;

/** 
 * @author Tom
 * @date 2019年7月12日
 * 
 */
public interface CompanyPrintMapper {
	
	/**
	 * 付款通知单打印查询
	 * @author tony
	 * @param ids
	 * @return
	 */
	List<CompanyPrintVo> listCompanyPrintData(String[] ids);

}
