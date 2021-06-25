package com.club.business.print.service;

import com.club.business.print.constants.PrintTypeEnum;
import com.club.business.print.vo.base.PrintParam;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.SpringContextUtils;
import com.club.business.util.exception.BusinessException;
import com.itextpdf.text.pdf.PdfCopy;
import org.springframework.stereotype.Service;

/**
 * 打印服务类
 *
 * @author Tom
 * @date 2019-12-16
 */
@Service
public class PrintService {

	/**
	 * 单据统一打印入口(获取到真实的服务类调用抽象类的doPrint方法执行打印)
	 * @param param 打印参数
	 * @param copy pdf对象
	 * @throws Exception
	 */
	public void printBill(PrintParam param, PdfCopy copy) throws Exception {
		int printType = param.getPrintType();
		AbstractBillPrintService<?, ?> billPrintService = getPrintService(printType);
		billPrintService.doPrint(param, copy);
	}

	/**
	 * 获取具体的单据打印服务类(调用工具方法通过class获取实体对象)
	 * @param printType 打印类型
	 * @return 抽象的打印服务类
	 * @throws Exception
	 */
	private AbstractBillPrintService<?, ?> getPrintService(int printType) throws Exception {
		PrintTypeEnum printTypeEnum = PrintTypeEnum.getByIndex(printType);
		if (printTypeEnum == null) {
			throw new BusinessException("暂无此单据的打印信息");
		}
		return (AbstractBillPrintService<?, ?>) SpringContextUtils.getBean(printTypeEnum.getClazz());
	}

	/**
	 * 保存打印记录
	 * @param user 当前用户
	 * @param param 打印参数
	 * @throws Exception
	 */
	public void saveRecords(SysUser user, PrintParam param) throws Exception {
		int printType = param.getPrintType();
		AbstractBillPrintService<?, ?> billPrintService = getPrintService(printType);
		billPrintService.saveRecords(user, param);
	}
}
