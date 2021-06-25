package com.club.business.excel.service;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.club.business.excel.common.ExportView;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解导出
 *
 * @author Tom
 * @date 2019-12-12
 */
@Service
public class SingleExportService implements IExcelExportService{

	@Override
	public void excute(ExportView exportView, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> params = new HashMap<>();
		params.put(NormalExcelConstants.DATA_LIST, exportView.getDataList()); // 数据集合
		params.put(NormalExcelConstants.CLASS, exportView.getEntityCls());//导出实体
		params.put(NormalExcelConstants.PARAMS, exportView.getExportParams());//参数
		params.put(NormalExcelConstants.FILE_NAME, exportView.getFileName());//文件名称
		PoiBaseView.render(params, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
	}
}
