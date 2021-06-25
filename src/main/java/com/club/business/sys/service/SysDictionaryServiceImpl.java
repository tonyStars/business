package com.club.business.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.sys.vo.SysDictionary;
import com.club.business.sys.dao.SysDictionaryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author 
 * @date 2019-12-04
 */
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper,SysDictionary> {

    /**
     * 数据字典查询
     * @param page 当前页
     * @param pageSize 页面大小
     * @param typeName 类型名称
     * @param itemName 子项目名称
     * @param status 状态
     * @return
     */
    public Map<String, Object> search(int page, int pageSize, String typeName, String itemName, String status) throws Exception {
        QueryWrapper<SysDictionary> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(typeName)){
            qw.eq("TYPE_NAME",typeName);
        }
        if(StringUtils.isNotBlank(itemName)){
            qw.eq("ITEM_NAME",itemName);
        }
        if(StringUtils.isNotBlank(status)){
            qw.eq("STATUS",status);
        }
        Page<SysDictionary> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysDictionary> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 新增数据字典
     * @param list 对象集合数据
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void add(List<SysDictionary> list) throws Exception {
        this.saveBatch(list);
    }

    /**
     * 修改数据字典
     * @param model
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void edit(SysDictionary model) throws Exception {
        this.updateById(model);
    }

    /**
     * 启用数据字典
     * @param ids
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void ok(String ids) throws Exception {
        List<SysDictionary> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysDictionary model = new SysDictionary();
            model.setId(Integer.valueOf(idsTemp[i]));
            model = this.getById(idsTemp[i]);
            if (model == null) {
                throw new BusinessException("未找到要操作的数据！");
            }
            if (model.getStatus().toString().equals("0")) {
                throw new BusinessException("请勿选择已启用的数据！");
            } else {
                model.setStatus(0);
            }
            list.add(model);
        }
        this.updateBatchById(list);
    }

    /**
     * 停用数据字典
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    public void del(String ids) throws Exception {
        List<SysDictionary> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysDictionary model = new SysDictionary();
            model.setId(Integer.valueOf(idsTemp[i]));
            model = this.getById(idsTemp[i]);
            if (model == null) {
                throw new BusinessException("未找到要操作的数据！");
            }
            if (model.getStatus().toString().equals("-1")) {
                throw new BusinessException("请勿选择已停用的数据！");
            } else {
                model.setStatus(-1);
            }
            list.add(model);
        }
        this.updateBatchById(list);
    }

    /**
     * 根据TypeName查询数据字典集合
     * @param typeName
     * @param itemName
     * @return
     */
    public List<SysDictionary> queryByName(String typeName, String itemName) {
        QueryWrapper<SysDictionary> qw = new QueryWrapper<>();
        qw.eq("STATUS",0);
        if(StringUtils.isNotBlank(typeName)){
            qw.eq("TYPE_NAME",typeName);
        }
        if(StringUtils.isNotBlank(itemName)){
            qw.eq("ITEM_NAME",itemName);
        }
        return this.list(qw);
    }

    /**
     * 根据TypeName查询数据字典集合
     * @param typeName
     * @return
     */
    public List<SysDictionary> queryByName(String typeName) {
        QueryWrapper<SysDictionary> qw = new QueryWrapper<>();
        qw.eq("STATUS",0);
        if(StringUtils.isNotBlank(typeName)){
            qw.eq("TYPE_NAME",typeName);
        }
        return this.list(qw);
    }

    /**
     * 查询所有类型名称
     * @return
     */
    public List<SysDictionary> queryAllType() throws Exception {
        return baseMapper.queryAllType();
    }
}
