package com.club.business.print.service;

import com.club.business.print.constants.PrintConstant;
import com.club.business.print.vo.base.BaseDetailPrintVo;
import com.club.business.print.vo.base.BasePrintVo;
import java.util.List;

/** 
 * 三联单打印抽象类(暂未使用)
 * @author Tom
 * @date 2019-12-16
 * 
 */
public abstract class TripletPrintService<T extends BasePrintVo<M>,M extends BaseDetailPrintVo> extends AbstractBillPrintService<T,M> {
	
	@Override
	protected int getPageSize() {
		return PrintConstant.PRINT_ROW;
	}
	
	@Override
	protected int getTotalCount(int size) {
		return size / getPageSize() + 1;
	}
	
	@Override
	protected void setSerialNum(T basePrint) {}

	/**
	 * 不能让明细下面的项目单独占一页，所以整页数拆分一行到最后一页
	 */
	@Override
	protected List<M> detailPaging(int i, int pageSize, int totalCount,List<M> detailList) {
		int size = detailList.size();
		if (size % pageSize == 0) {
			if (i == totalCount - 2) {
				return detailList.subList(i * pageSize, (i + 1) * pageSize - 1);
			} else if (i == totalCount - 1) {
				return detailList.subList(i * pageSize - 1, size);
			} else {
				return detailList.subList(i * pageSize, (i + 1) * pageSize);
			}
		} else {
			return i == totalCount - 1 ? detailList.subList(i * pageSize, size)
					: detailList.subList(i * pageSize, (i + 1) * pageSize);
		}
	}
}
