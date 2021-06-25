package com.club.business.excel.service;

import java.util.List;

/**
 * 导入校验和保存接口
 *
 * @author Tom
 * @date 2019-12-12
 */

public interface IImportDataService {
	
	void checkData(List<?> dataList);
	
	void saveData(List<?> dataList);

}
