package com.club.business.sys.dao;

import com.club.business.sys.vo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据id获取对象
     * @param id userId
     * @return
     */
    SysUser getModelById(@Param("id") String id);

    /**
     * Excel导出
     * @param ew
     * @return
     */
    List<SysUser> selectExportList(@Param("ew") Map<String, Object> ew);
}
