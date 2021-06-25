package com.club.business.excel.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 导出工具类
 * 
 * @author Tom
 * @date 2019-12-12
 */
public class ExcelExportlHelper {
	
	private static final String HSSF = ".xls";

	private static final String XSSF = ".xlsx";
	
	/**
	 * 大数据导出excel
	 * @throws IOException 
	 */
	public static void exportExcel(ExportView exportView, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Workbook workbook = getExportData(exportView);
		if (exportView.getFileName() != null) {
			fileName = exportView.getFileName();
		}
		if (workbook instanceof HSSFWorkbook) {
			fileName += HSSF;
        } else {
        	fileName += XSSF;
        }
		if (isIE(request)) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF8");
        } else {
        	fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream(); // 输出到文件
        workbook.write(out);
        out.flush();
        out.close();
	}

	/**
	 * 多sheet导出
	 * @param
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void exportExcelMoreSheet(List<ExportView> paramsList, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		ExportView fistExportView = paramsList.get(0);
		Workbook workbook = getWorkbook(fistExportView.getExportParams().getType(), 0);
		for (ExportView exportView : paramsList) {
			ExcelExportService service = new ExcelExportService();
			service.createSheet(workbook, exportView.getExportParams(), exportView.getEntityCls(), exportView.getDataList());
		}
		if (fistExportView.getFileName() != null) {
			fileName = fistExportView.getFileName();
		}
		if (workbook instanceof HSSFWorkbook) {
			fileName += HSSF;
        } else {
        	fileName += XSSF;
        }
		if (isIE(request)) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF8");
        } else {
        	fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream(); // 输出到文件
        workbook.write(out);
        out.flush();
        out.close();
	}
	
	private static Workbook getExportData(ExportView exportView) {
		Workbook workbook = null;
		switch (exportView.getExportMode()) {
		case BIG:
			workbook = ExcelExportUtil.exportBigExcel(exportView.getExportParams(), exportView.getEntityCls(),
					Collections.EMPTY_LIST);
			IExcelExportServer server = (IExcelExportServer) exportView.getiExcelExportServer();
			int page = 1;
	        List<Object> list = server.selectListForExcelExport(exportView.getMap(), page++);
	        while (list != null && list.size() > 0) {
	            workbook = ExcelExportUtil.exportBigExcel(exportView.getExportParams(),
	            		exportView.getClass(), list);
	            list = server.selectListForExcelExport(exportView.getMap(),
						page++);
	        }
	        ExcelExportUtil.closeExportBigExcel();
	        break;
		case MAP:
			List<ExcelExportEntity> entityList = exportView.getEntityList();
			if (entityList.size() == 0) {
				exportView.getMap().forEach((key,value) -> {
					ExcelExportEntity excelentity = new ExcelExportEntity(key,value);
					entityList .add(excelentity);
				});
			}
			workbook = ExcelExportUtil.exportExcel(exportView.getExportParams(), entityList, exportView.getDataList());
			break;
		case SINGLE:
			workbook = ExcelExportUtil.exportExcel(exportView.getExportParams(), exportView.getEntityCls(),
					exportView.getDataList());
			break;
		case TEMPLATE:
			workbook = ExcelExportUtil.exportExcel(exportView.getTemplateExportParams(), exportView.getMap());
			break;

		case SINGLEADD:
			workbook = ExportUtil.exportExcel(exportView.getExportParams(), exportView.getEntityCls(),
					exportView.getDataList(),exportView.getAddMap());
			break;

		case SINGLEID:
			workbook = ExportUtil.exportExcelId(exportView.getExportParams(), exportView.getEntityCls(),
					exportView.getDataList(),exportView.getId());
			break;

		default:
			workbook = ExcelExportUtil.exportExcel(exportView.getExportParams(), exportView.getClass(),
					exportView.getDataList());
			break;
		}
		return workbook;
	}
	
	
	/**
	 * 是否IE浏览器
	 * @param request
	 * @return
	 */
	private static boolean isIE(HttpServletRequest request) {
        return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0
                || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0
                || request.getHeader("USER-AGENT").toLowerCase().indexOf("edge") > 0) ? true
                    : false;
    }
	
	private static Workbook getWorkbook(ExcelType type, int size) {
		if (ExcelType.HSSF.equals(type)) {
			return new HSSFWorkbook();
		} else if (size < 100000) {
			return new XSSFWorkbook();
		} else {
			return new SXSSFWorkbook();
		}
	}
}
