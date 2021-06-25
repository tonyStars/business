package com.club.business.excel.service;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.club.business.excel.common.ExportView;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Tom
 * @date 2019-12-12
 */
@Service
public class MapExportService implements IExcelExportService{

	@Override
	public void excute(ExportView exportView, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> params = new HashMap<>();
		List<ExcelExportEntity> entityList = exportView.getEntityList();
		if (entityList.size() == 0) {
			exportView.getMap().forEach((key,value) -> {
				ExcelExportEntity excelentity = new ExcelExportEntity(key,value);
				entityList .add(excelentity);
			});
		}
		params.put(MapExcelConstants.MAP_LIST, exportView.getDataList()); // 数据集合
		params.put(MapExcelConstants.ENTITY_LIST, entityList); // 注解集合
		params.put(MapExcelConstants.PARAMS, exportView.getExportParams());// 参数
		params.put(MapExcelConstants.FILE_NAME, exportView.getFileName());// 文件名称
		PoiBaseView.render(params, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
	}
}
