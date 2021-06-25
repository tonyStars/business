package com.club.business.excel.common;

import cn.afterturn.easypoi.excel.ExcelImportUtil;

import java.util.List;

/**
 * 导入工具类
 * 
 * @author Tom
 * @date 2019-12-12
 */
public class ExcelImportlHelper {
	
	/**
	 * 导入excel数据
	 * @param importView
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static List<Object> importExcel(ImportView importView) throws Exception {
		List<Object> dataList = null;
		if (importView.getMultipartFile() != null) {
			dataList = ExcelImportUtil.importExcel(importView.getMultipartFile().getInputStream(),importView.getEntityCls(),
					importView.getImportPrams());
		} else if (importView.getFile() != null) {
			dataList = ExcelImportUtil.importExcel(importView.getFile(), importView.getEntityCls(),
					importView.getImportPrams());
		}
		return dataList;
	}
   
}
		