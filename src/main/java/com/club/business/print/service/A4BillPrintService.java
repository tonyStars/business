package com.club.business.print.service;

import com.club.business.print.constants.PrintConstant;
import com.club.business.print.vo.base.BaseDetailPrintVo;
import com.club.business.print.vo.base.BasePrintVo;
import java.util.List;

/** 
 * A4打印,所有A4打印都可以继承此类
 *
 * @author Tom
 * @date 2019-12-16
 */
public abstract class A4BillPrintService<T extends BasePrintVo<M>,M extends BaseDetailPrintVo> extends AbstractBillPrintService<T,M> {
	
	@Override
	protected int getPageSize() {
		return PrintConstant.A4_PRINT_ROW;
	}
	
	@Override
	protected int getTotalCount(int size) {
		int pageSize = getPageSize(); 
		if (size % pageSize == 0) {
			return size / pageSize;
		} 
		return size / pageSize + 1;
	}
	
	@Override
	protected List<M> detailPaging(int i, int pageSize, int totalCount,List<M> entries) {
		return i == totalCount - 1 ? entries.subList(i * pageSize, entries.size())
				: entries.subList(i * pageSize, (i + 1) * pageSize);
	}
}
