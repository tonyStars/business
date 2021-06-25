package com.club.business.excel.common;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.club.business.excel.service.IImportDataService;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

/**
 * 导入参数配置类
 *
 * @author Tom
 * @date 2019-12-12
 */
public class ImportView {
	/**
	 * 导入参数
	 */
	private ImportParams importPrams;
	/**
	 * 导入实体Class
	 */
	private Class<?> entityCls;
	/**
	 * 导入服务接口
	 */
	private IImportDataService iImportDataService;
	/**
	 * 导入文件
	 */
	private File file;
	
	private MultipartFile multipartFile;
	
	public ImportView() {
		importPrams = new ImportParams();
	}
	
	public IImportDataService getiImportDataService() {
		return iImportDataService;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public void setiImportService(IImportDataService iImportDataService) {
		this.iImportDataService = iImportDataService;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ImportParams getImportPrams() {
		return importPrams;
	}

	public void setImportPrams(ImportParams importPrams) {
		this.importPrams = importPrams;
	}

	public Class<?> getEntityCls() {
		return entityCls;
	}

	public void setEntityCls(Class<?> entityCls) {
		this.entityCls = entityCls;
	}
}
