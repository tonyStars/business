package com.club.business.print.service;

import com.club.business.print.constants.BusLineBreakingStrategy;
import com.club.business.print.vo.base.BaseDetailPrintVo;
import com.club.business.print.vo.base.BasePrintVo;
import com.club.business.print.vo.base.PrintParam;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.BarcodeUtils;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.List;

/** 
 * 单据打印抽象类，在这里定义打印的基本步骤
 *
 * @author Tom
 * @date 2019-12-16
 */
public abstract class AbstractBillPrintService<T extends BasePrintVo<M>, M extends BaseDetailPrintVo> {
	
	private Logger logger = LoggerFactory.getLogger(AbstractBillPrintService.class);

	/**
	 * 打印
	 * @param param 打印参数
	 * @param copy 
	 * @throws Exception 
	 */
	public void doPrint(PrintParam param, PdfCopy copy) throws Exception {
		/**1.获取打印数据*/
		List<T> dataList = getData(param);
		if (dataList == null || dataList.size() ==0) {
			return;
		}
		/**获取数据后进行处理*/
		afterGetData(dataList);
		/**获取打印模板*/
		Template template = getTemplate();
		/**2.分页处理*/
		for (T basePrint : dataList) {
			/**生成PDF之前,子类实现可以重写传入的basePrint打印对象*/
			beforePdf(basePrint);
			/**2.1 设置打印的根url*/
			basePrint.setBaseUrl(param.getBaseUrl());
			/**2.2 条码处理*/
			genBarCode(basePrint);
			/**2.3 页眉和页脚*/
			headerAndFooter(basePrint);
			/**2.4 设置序号*/
			setSerialNum(basePrint);
			/**2.5 打印分页*/
			paging(basePrint, copy, template);
			/**2.6 生成PDF之后调用此方法重写*/
			afterPdf(basePrint);
		}
	}
	
	/**
	 * 获取打印数据
	 * @param param 打印参数
	 * @return 打印数据集合
	 */
	protected abstract List<T> getData(PrintParam param);
	
	/**
	 * 获取打印模板名称
	 * @return 模板名称 xxx.ftl
	 */
	protected abstract String getTemplateName();
	
	/**
	 * 获取每页的条数
	 * @return 每页的条数
	 */
	protected abstract int getPageSize();
	
	/**
	 * 获取总页数
	 * @param size 明细条数
	 * @return 总页数
	 */
	protected abstract int getTotalCount(int size);
	
	/**
	 * @param i 当前索引
	 * @param pageSize 每页条数
	 * @param totalCount 总页数
	 * @param entries 明细
	 * @return
	 */
	protected abstract List<M> detailPaging(int i, int pageSize, int totalCount,
			List<M> entries);
	
	/**
	 * 获取数据之后的处理，子类可以重写来处理一些逻辑
	 * @param dataList 打印数据集合
	 */
	protected void afterGetData(List<T> dataList) {}

	/**
	 * 前置：生成pdf之前，子类可以重写
	 * @param basePrint 打印对象
	 */
	protected void beforePdf(T basePrint) {}

	/**
	 * 后置： 生成pdf之后，子类可以重写
	 * @param basePrint 打印对象
	 */
	protected void afterPdf(T basePrint) {}

	/**
	 * 获取需要生成条码的字段值,子类可以重写
	 * @param basePrint 打印对象
	 * @return 需要生成条码的字段值,默认 exNo
	 */
	protected String getBarcodeValue(T basePrint) {
		return basePrint.getExNo();
	}
	
	/**
	 * 生成条码，若getBarcodeFieldValue为空则不生成条码
	 * @param basePrint 单据打印对象
	 */
	protected void genBarCode(T basePrint) {
		String field = getBarcodeValue(basePrint);
		if (StringUtils.isEmpty(field)) {
			logger.info("条码为空，不生成条码!");
		} else {
			try {
				String barCode = BarcodeUtils.generateStr(field);
				basePrint.setBarCode("data:image/E;base64," + barCode);
			} catch (Exception e) {
				logger.error("生成条形码错误！");
			}
		}
	}
	
	/**
	 * 页眉和页脚
	 * @param basePrint 打印对象
	 */
	protected void headerAndFooter(T basePrint) {
		Integer companyId = basePrint.getCompanyId();
//		String printFooter = paramService.getParam(companyId, ParamConstant.PRINT_FOOTER);
		String printFooter = "Tom公司"; //根据公司设置获取
		basePrint.setPrintFooter(printFooter);
	}
	
	/**
	 * 设置序号
	 * @param basePrint 打印对象
	 */
	protected void setSerialNum(T basePrint) {}

	/**
	 * 获取打印模板(全部)
	 * @throws Exception 
	 * 
	 */
	private Template getTemplate() throws Exception {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
		cfg.setDirectoryForTemplateLoading(new File(this.getClass().getClassLoader().getResource("printTemp").getPath()));
		cfg.setDefaultEncoding("UTF-8");
		Template template = cfg.getTemplate(getTemplateName());
		return template;
	}

	/**
	 * 确定分页
	 * @param basePrint 单据打印对象
	 * @param copy 多页pdf对象
	 * @param template 打印模板工具类
	 * @throws Exception 
	 */
	protected void paging(T basePrint, PdfCopy copy, Template template) throws Exception {
		int pageSize = getPageSize();
		basePrint.setPageSize(pageSize);
		List<M> detailList = basePrint.getDetailList();
		int size = detailList.size();
		int totalCount = getTotalCount(size);
		basePrint.setTotalCount(totalCount);
		int curPageNum = 1;
		for (int i = 0; i < totalCount; i++) {
			List<M> subList = detailPaging(i,pageSize,totalCount,detailList);
			basePrint.setDetailList(subList);
			basePrint.setCurPageNum(curPageNum++);
			genpdf(basePrint,copy, template);
		}
	}

	/**
	 * 生成一页pdf
	 * @param obj 处理后的打印对象
	 * @param copy 多页pdf对象
	 * @param template 打印模板工具类o
	 * @throws Exception 
	 */
	protected void genpdf(T obj, PdfCopy copy, Template template) throws Exception {
		/**1. 模板生成html*/
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		template.process(obj, writer);
		String htmlStr =  stringWriter.toString();
	    writer.close();
	    /**2. html转pdf*/
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(htmlStr);
		ITextFontResolver fontResolver = renderer.getFontResolver();
		/**中文支持*/
		fontResolver.addFont("/printTemp/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		SharedContext sharedContext = renderer.getSharedContext();
		sharedContext.setLineBreakingStrategy(new BusLineBreakingStrategy());
		renderer.layout();
		renderer.createPDF(baos);
		PdfReader reader = new PdfReader(baos.toByteArray());
		copy.addDocument(reader);
		reader.close();
	}
	
	/**
	 * 保存打印记录
	 * @param user 当前用户
	 * @param param 打印参数
	 */
	protected void saveRecords(SysUser user, PrintParam param) {}
}

