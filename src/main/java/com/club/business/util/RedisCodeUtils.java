package com.club.business.util;

import com.club.business.contants.CodeTypeEnum;
import com.club.business.util.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;

/**
 * redis编码生成工具类
 *
 * @author Tom
 * @date 2019-12-12
 */
@Component
public class RedisCodeUtils {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取年的后两位加上一年多少天+当前小时数作为前缀
     *
     * @param date
     * @return
     */
    public String getOrderIdPrefix(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        /**补两位,因为一年最多三位数*/
        String monthFormat = String.format("%1$02d", month + 1);
        /**补两位，因为日最多两位数*/
        String dayFormat = String.format("%1$02d", day);
        /**补两位，因为小时最多两位数*/
        String hourFormat = String.format("%1$02d", hour);
        return year + monthFormat + dayFormat + hourFormat;
    }

    /**
     * 使用redis获取递增Code
     * @param key
     * @return
     * @throws Exception
     */
    public String getCode(String key) throws Exception {
        CodeTypeEnum bcenum = CodeTypeEnum.getValueByKey(key);
        if(null != bcenum){
            Long startNum = bcenum.getStartNum();
            int length = bcenum.getLength();
            Long currNum = redisUtils.incr(bcenum.getKey(),1);
            return String.format("%0"+length+"d", (startNum + currNum));
        }else{
            throw new BusinessException("未配置" + key + "对应的CodeTypeEnum，请联系系统管理员") ;
        }
    }
}
