package com.club.business.sys.dao;

import com.club.business.sys.vo.SysDictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author 
 * @date 2019-12-04
 */
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    /**
     * 查询所有类型名称
     * @return
     */
    List<SysDictionary> queryAllType();
}
