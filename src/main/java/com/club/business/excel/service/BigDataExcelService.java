package com.club.business.excel.service;

import cn.afterturn.easypoi.entity.vo.BigExcelConstants;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.club.business.excel.common.ExportView;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 大数据导出
 *
 * @author Tom
 * @date 2019-12-12
 */
@Service
public class BigDataExcelService implements IExcelExportService{
	
	@Override
	public void excute(ExportView exportView, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> params = new HashMap<>();
		params.put(BigExcelConstants.FILE_NAME, exportView.getFileName());
		params.put(BigExcelConstants.CLASS, exportView.getEntityCls());
		params.put(BigExcelConstants.PARAMS, exportView.getExportParams());
		params.put(BigExcelConstants.DATA_PARAMS, exportView.getMap());
		params.put(BigExcelConstants.DATA_INTER, exportView.getiExcelExportServer());
		PoiBaseView.render(params, request, response, BigExcelConstants.EASYPOI_BIG_EXCEL_VIEW);
	}
}
