package com.club.business.excel.common;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Excel工具类
 *
 * @author Tom
 * @date 2019-12-12
 */
public class ExportUtil {

    public static Workbook exportExcel(ExportParams entity, Class<?> pojoClass, Collection<?> dataSet, Map<Map<String,Boolean>,List<ExcelExportEntity>> addMap) {
        Workbook workbook = getWorkbook(entity.getType(),dataSet.size());
        ExcelExportService excelExportService = new ExcelExportService();
        List<ExcelExportEntity> excelParams = excelExportService.createSheet(workbook, entity, pojoClass, dataSet);
        for (Map<String,Boolean> addPosi:addMap.keySet()){
            List<ExcelExportEntity> excelExportEntityList = addMap.get(addPosi);
            for(String name :addPosi.keySet()){
                boolean isList = addPosi.get(name);
                if(isList){
                    for (ExcelExportEntity exportEntity : excelParams){
                        if(exportEntity.getName().equals(name)){
                            List<ExcelExportEntity> list = exportEntity.getList();
                            list.addAll(excelExportEntityList);
                            break;
                        }
                    }
                }else{
                    excelParams.addAll(excelExportEntityList);
                    break;
                }
            }
        }
        excelExportService.createSheetForMap(workbook, entity, excelParams, dataSet);
        return workbook;
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

    public static Workbook exportExcelId(ExportParams entity, Class<?> pojoClass, List<?> dataSet, String id) {
        Workbook workbook = getWorkbook(entity.getType(),dataSet.size());
        ExcelExportService excelExportService = new ExcelExportService();
        List<ExcelExportEntity> excelParams = excelExportService.createSheetId(workbook, entity, pojoClass, dataSet,id);
        excelExportService.createSheetForMap(workbook, entity, excelParams, dataSet);
        return workbook;
    }
}
