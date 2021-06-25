package com.club.business.excel.service;


import com.club.business.excel.common.ExportView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Excel到处接口
 *
 * @author Tom
 * @date 2019-12-12
 */
public interface IExcelExportService {
	
	void excute(ExportView exportView, HttpServletRequest request, HttpServletResponse response);
}
