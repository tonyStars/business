package com.club.business.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.sys.vo.SysOperLog;
import com.club.business.sys.dao.SysOperLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.util.DateConvert;
import com.club.business.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author 
 * @date 2019-12-11
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper,SysOperLog> {

    /**
     * 日志查询
     * @param page 当前页
     * @param pageSize 页面size
     * @param menuName 菜单名称
     * @param operIp 操作IP
     * @param typeName 操作类型
     * @param status 状态
     * @return
     */
    public Map<String, Object> search(int page, int pageSize, String menuName, String operIp, String typeName, String status,String startTime,String endTime) throws Exception{
        QueryWrapper<SysOperLog> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(menuName)){
            qw.like("MENU_NAME",menuName);
        }
        if(StringUtils.isNotBlank(operIp)){
            qw.eq("OPER_IP",operIp);
        }
        if(StringUtils.isNotBlank(typeName)){
            qw.like("TYPE_NAME",typeName);
        }
        if(StringUtils.isNotBlank(status)){
            qw.eq("STATUS",status);
        }
        DateConvert convert = new DateConvert();
        if(StringUtils.isNotBlank(startTime)){
            qw.ge("OPER_TIME",convert.convert(startTime));
            if(StringUtils.isBlank(endTime)){
                qw.le("OPER_TIME", LocalDateTime.now());
            }
        }
        if(StringUtils.isNotBlank(endTime)){
            qw.le("OPER_TIME", DateUtils.weeHours(convert.convert(endTime),1));
            if(StringUtils.isBlank(startTime)){
                qw.ge("OPER_TIME",DateUtils.getMonthFirstDay());
            }
        }
        qw.orderByDesc("OPER_TIME");
        Page<SysOperLog> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysOperLog> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }
}
