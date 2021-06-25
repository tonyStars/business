package com.club.business.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局日期处理类
 * Convert<T,S>
 *         泛型T:代表客户端提交的参数 String
 *         泛型S:通过convert转换的类型
 * @author Tom
 * @date 2019-12-12
 */
public class DateConvert implements Converter<String, Date> {
	
		private static Logger logger = LoggerFactory.getLogger(DateConvert.class);
		
        @Override
        public Date convert(String stringDate){
            SimpleDateFormat simpleDateFormat = null;
            
            if (stringDate.matches("^\\d{2,4}-\\d{1,2}-\\d{1,2}$")) {
            	simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            } else {
            	simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            try {
            	if (stringDate == null || "".equals(stringDate)) {
            		return null;
            	}
                return simpleDateFormat.parse(stringDate);
            } catch (ParseException e) {
            	logger.info(e.getMessage(), e);
            }
            return null;
        }
}

