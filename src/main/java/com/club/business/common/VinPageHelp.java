package com.club.business.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.vo.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/6/2.
 */
public class VinPageHelp {
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(VinPageHelp.class);

    /**
     * 为适应前台layUi而产生的封装数据类
     * @param page
     * @param <T>
     * @return
     */
    public static <T> Map<String,Object> getPage(Page<T> page){
        HashMap<String, Object> map = new HashMap<>();
        if(null==page){
            map.put("code",1);
            map.put("msg","查询数据异常");
            return map;
        }
        List<T> records = page.getRecords();
        long total = page.getTotal();
        map.put("data",records);
        map.put("count",total);
        map.put("code",0);
        map.put("msg",records.size() == 0 ? "无数据" : "查询成功");
        return map;
    }

    /**
     * 传入参数类  处理成查询所需要的封装
     * @param pageInfo 保留的传参，可以注入需要的参数，比如desc asc等  size和current 以pageParam为准
     * @param pageParam
     * @param <T>
     * @return
     */
    public static<T> Page<T> pageParamHandle(Page<T> pageInfo, PageParam pageParam){
        if(null==pageParam){
            pageInfo.setCurrent(1);
            pageInfo.setSize(10);
            return pageInfo;
        }
        Integer current = pageParam.getPage();
        Integer limit = pageParam.getLimit();
        //如果参数非法 则默认limit 0，10
        if (null == current || current==0){
            if (logger.isInfoEnabled()){
                logger.info("VinPageHelp pageParamHandle() current="+current);
            }
            current = 1;
        }
        if(null == limit || limit == 0){
            if (logger.isInfoEnabled()){
                logger.info("VinPageHelp pageParamHandle() limit="+limit);
            }
            limit = 10;
        }
        pageInfo.setCurrent(current);
        pageInfo.setSize(limit);
        return pageInfo;
    }



    /**
     * 传入参数类  处理成查询所需要的封装
     * @param pageInfo 保留的传参，可以注入需要的参数，比如desc asc等  size和current 以pageParam为准
     * @param
     * @param <T>
     * @return
     */
    public static<T> Page<T> pageParamHandle(Page<T> pageInfo,Integer page,Integer limit){

        //如果参数非法 则默认limit 0，10
        if (null == limit || limit==0){
            if (logger.isInfoEnabled()){
                logger.info("VinPageHelp pageParamHandle() limit="+limit);
            }
            limit = 1;
        }
        if(null == page || page == 0){
            if (logger.isInfoEnabled()){
                logger.info("VinPageHelp pageParamHandle() page="+page);
            }
            limit = 10;
        }
        pageInfo.setCurrent(page);
        pageInfo.setSize(limit);
        return pageInfo;
    }

    public static String formatDate(Date date){
        if(null == date){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

}
