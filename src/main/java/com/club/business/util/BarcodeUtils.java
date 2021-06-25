package com.club.business.util;

import com.alibaba.druid.util.Base64;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Description  生成条形码工具类
 * @author Tom
 * @date 2019-12-16
 * 
 */
public class BarcodeUtils {
	
	 /**
     * 生成文件
     *
     * @param msg
     * @param path
     * @return
     */
    public static File generateFile(String msg, String path) {
        File file = new File(path);
        try {
        	code128Generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
 
    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        code128Generate(msg, ous);
        return ous.toByteArray();
    }
    
    /**
     * 生成字符串
     *
     * @param msg
     * @return
     */
    public static String generateStr(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        code128Generate(msg, ous);
        return Base64.byteArrayToBase64(ous.toByteArray());
    }
 
    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public static void code128Generate(String msg, OutputStream ous) {
        if (msg == null || msg.equals("") || ous == null) {
            return;
        }
        Code128Bean bean = new Code128Bean();
        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(1.5f / dpi);
        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setBarHeight(8);
        bean.doQuietZone(false);
        String format = "image/png";
        try {
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生成条形码
            bean.generateBarcode(canvas, msg);
            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
