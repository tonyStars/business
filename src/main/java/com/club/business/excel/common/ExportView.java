package com.club.business.excel.common;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出信息配置类
 *
 * @author Tom
 * @date 2019-12-12
 */
public class ExportView {
	/**
	 * 导出参数
	 */
	private ExportParams exportParams = new ExportParams();
	/**
	 * 对应注解 class 实例对象的数据集合
	 */
    private List<?> dataList;
	/**
	 * 对应注解的 class
	 */
	private Class<?> entityCls;
	/**
	 * 导出类型
	 */
	private ExportModeEnum exportMode = ExportModeEnum.SINGLE;
	/**
	 * 导出文件名字
	 */
	private String fileName = null;
	/**
	 * 大数据导出服务类
	 */
	private IExcelExportServer iExcelExportServer;
	/**
	 * 查询参数   当为模板导出时为 字段名-属性名 ，当为大数据导出时为查询参数
	 */
	private Map<String,Object> map = new HashMap<>();
	/**
	 * 自定义导出实体
	 */
	private List<ExcelExportEntity> entityList = new ArrayList<>();
	/**
	 * 模板导出参数
	 */
	private TemplateExportParams templateExportParams;
	/**
	 * 注解ID
	 */
	private String id;

	private Map<Map<String, Boolean>, List<ExcelExportEntity>> addMap;

	public ExportView() {
    	
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<Map<String, Boolean>, List<ExcelExportEntity>> getAddMap() {
		return addMap;
	}

	public void setAddMap(Map<Map<String, Boolean>, List<ExcelExportEntity>> addMap) {
		this.addMap = addMap;
	}

	public ExportView(String fileName, String title, String sheetName) {
    	this.fileName = fileName;
    	if (exportParams == null) {
    		exportParams = new ExportParams();
    	}
    	exportParams.setTitle(title);
    	exportParams.setSheetName(sheetName);
    	exportParams.setType(ExcelType.XSSF);
    }
    
	public ExportParams getExportParams() {
        return exportParams;
    }

    public void setExportParams(ExportParams exportParams) {
        this.exportParams = exportParams;
    }

    public Class<?> getEntityCls() {
        return entityCls;
    }
    
    public void setEntityCls(Class<?> entityCls) {
        this.entityCls = entityCls;
    }
    
    public List<?> getDataList() {
        return dataList;
    }
    
    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
    
    public ExportModeEnum getExportMode() {
		return exportMode;
	}
	public void setExportMode(ExportModeEnum exportMode) {
		this.exportMode = exportMode;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public IExcelExportServer getiExcelExportServer() {
		return iExcelExportServer;
	}
	
	public void setiExcelExportServer(IExcelExportServer iExcelExportServer) {
		this.iExcelExportServer = iExcelExportServer;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public List<ExcelExportEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<ExcelExportEntity> entityList) {
		this.entityList = entityList;
	}
	
	public TemplateExportParams getTemplateExportParams() {
		return templateExportParams;
	}

	public void setTemplateExportParams(TemplateExportParams templateExportParams) {
		this.templateExportParams = templateExportParams;
	}
}
