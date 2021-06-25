package com.club.business.print.controller;

import com.club.business.print.service.PrintService;
import com.club.business.print.vo.base.PrintParam;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.BaseConstant;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
/**
 * 单据打印
 *
 * @author Tom
 * @date 2019-12-16
 */
@Controller
@RequestMapping("/print")
public class PrintController {
	
	@Autowired
	private PrintService printService;
	
	/**
	 * 单据打印统一入口(所有打印均调用此接口)
	 * @param param 打印参数
	 * @param request rquest
	 * @param response response
	 * @throws Exception
	 */
	@RequestMapping("/printBill")
	public void printBill(PrintParam param, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String ids = param.getBillIds();
		int printType = param.getPrintType();
		if (ids == null || printType == 0) {
			 PrintWriter writer = response.getWriter();
			 writer.write("参数错误！");
			 writer.close();
			 return;
		}
		String baseUrl = request.getScheme()
				+"://" + request.getServerName()
				+ ":" + request.getServerPort()
				+ request.getContextPath();
		param.setBaseUrl(baseUrl);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Document document = new Document();
		PdfCopy copy = new PdfCopy(document,bos);
		document.open();
		printService.printBill(param, copy);
		document.close();
		ServletOutputStream out = response.getOutputStream();
		out.write(bos.toByteArray());
		out.close();
		SysUser user = (SysUser) request.getSession().getAttribute(BaseConstant.SESSION_USER);
		/**挪到这里做，防止打印出错后事务回滚的问题*/
		printService.saveRecords(user, param);
	}
}
