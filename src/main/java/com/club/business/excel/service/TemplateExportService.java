package com.club.business.excel.service;

import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.club.business.excel.common.ExportView;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板导出
 *
 * @author Tom
 * @date 2019-12-12
 */
@Service
public class TemplateExportService implements IExcelExportService{
	
	@Override
	public void excute(ExportView exportView, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> params = new HashMap<>();
		params.put(TemplateExcelConstants.FILE_NAME, exportView.getFileName()); //文件名
		params.put(TemplateExcelConstants.PARAMS, exportView.getTemplateExportParams());//参数
		params.put(TemplateExcelConstants.MAP_DATA, exportView.getMap());//数据
	    PoiBaseView.render(params, request, response, TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
	}
}
