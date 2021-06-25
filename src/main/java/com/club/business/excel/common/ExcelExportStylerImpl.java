package com.club.business.excel.common;

import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 设置样式实现类
 *
 * @author Tom
 * @date 2019-12-12
 */
public class ExcelExportStylerImpl extends ExcelExportStylerDefaultImpl {

    public ExcelExportStylerImpl(Workbook workbook) {
        super(workbook);
    }


    public CellStyle getTitleStyle(short color) {
        CellStyle titleStyle = this.workbook.createCellStyle();
        titleStyle.setAlignment((short)2);
        titleStyle.setVerticalAlignment((short)1);
        titleStyle.setWrapText(true);

        //设置字体
        Font font = this.workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBold(true);
        titleStyle.setFont(font);

        //增加框线
        titleStyle.setBorderLeft((short)1);
        titleStyle.setBorderRight((short)1);
        titleStyle.setBorderBottom((short)1);
        titleStyle.setBorderTop((short)1);


        return titleStyle;
    }


    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);
        style.setDataFormat(STRING_FORMAT);

        //增加框线
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderBottom((short)1);
        style.setBorderTop((short)1);

        if(isWarp) {
            style.setWrapText(true);
        }

        return style;
    }

    public CellStyle getHeaderStyle(short color) {
        CellStyle titleStyle = this.workbook.createCellStyle();
        titleStyle.setAlignment((short)2);
        titleStyle.setVerticalAlignment((short)1);



        //设置字体
        Font font = this.workbook.createFont();
        font.setFontHeightInPoints((short)20);
        font.setBold(true);
        titleStyle.setFont(font);

        //增加框线
        titleStyle.setBorderLeft((short)1);
        titleStyle.setBorderRight((short)1);
        titleStyle.setBorderBottom((short)1);
        titleStyle.setBorderTop((short)1);

        return titleStyle;
    }

    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);
        style.setDataFormat(STRING_FORMAT);

        //增加框线
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderBottom((short)1);
        style.setBorderTop((short)1);

        if(isWarp) {
            style.setWrapText(true);
        }
        return style;
    }



}
