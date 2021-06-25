package com.club.business.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.contants.RedisConstant;
import com.club.business.sys.vo.SysRegion;
import com.club.business.sys.dao.SysRegionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.RedisUtils;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author 
 * @date 2019-12-09
 */
@Service
public class SysRegionServiceImpl extends ServiceImpl<SysRegionMapper,SysRegion> {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 行政区域查询
     * @param page 当前页
     * @param pageSize 页面大小
     * @param name 区域名称
     * @param regionType 行政区域类型(0-国家、1-省、2-市、3-区、9-直辖市)
     * @param status 状态
     * @return
     */
    public Map<String, Object> search(int page, int pageSize,String name, String regionType, String status) throws Exception {
        QueryWrapper<SysRegion> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            qw.like("NAME",name);
        }
        if(StringUtils.isNotBlank(regionType)){
            qw.eq("REGION_TYPE",regionType);
        }
        if(StringUtils.isNotBlank(status)){
            qw.eq("STATUS",status);
        }
        Page<SysRegion> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysRegion> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 搜索框搜索行政区域数据
     * @param page 当前页
     * @param pageSize 页面大小
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchSelect(int page, int pageSize,String keyword) throws Exception {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("STATUS",0);
        if(StringUtils.isNotBlank(keyword)){
            qw.like("NAME",keyword);
        }
        Page<SysRegion> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysRegion> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 新增行政区域
     * @param model 行政区域对象
     * @param user 当前用户
     */
    @Transactional(rollbackFor=Exception.class)
    public void add(SysRegion model, SysUser user) throws Exception {
        model.setAddUserId(user.getUserId());
        model.setAddName(user.getUserName());
        model.setAddUserTime(LocalDateTime.now());
        this.save(model);
        updateRedisRegion();
    }

    /**
     * 修改行政区域
     * @param model 行政区域对象
     * @param user 当前用户
     */
    @Transactional(rollbackFor=Exception.class)
    public void edit(SysRegion model, SysUser user) throws Exception {
        model.setLastUpdateId(user.getUserId());
        model.setLastUpdateName(user.getUserName());
        model.setLastUpdateTime(LocalDateTime.now());
        this.updateById(model);
        updateRedisRegion();
    }

    /**
     * 启用行政区域
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    public void ok(String ids) throws Exception {
        List<SysRegion> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysRegion model = new SysRegion();
            model.setRegionId(Integer.valueOf(idsTemp[i]));
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
        updateRedisRegion();
    }

    /**
     * 停用行政区域
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    public void del(String ids) throws Exception {
        List<SysRegion> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysRegion model = new SysRegion();
            model.setRegionId(Integer.valueOf(idsTemp[i]));
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
        updateRedisRegion();
    }

    /**
     * 获取省市区树(单选)
     * @return
     */
    public List<Map<String, Object>> singleRegionTree() throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<SysRegion> list = new ArrayList<>();
        List<Object> list1 = redisUtils.lGet(RedisConstant.REGION_SINGLE_CACHE, Long.valueOf("0"), Long.valueOf("-1"));
        if(CollectionUtils.isEmpty(list1)){
            QueryWrapper<SysRegion> qw = new QueryWrapper<>();
            qw.eq("STATUS",0);
            list = this.list(qw);
            redisUtils.lSet(RedisConstant.REGION_SINGLE_CACHE,list);
        }else{
            list = (List<SysRegion>)list1.get(0);
        }
        if(list != null && list.size() > 0){
            for (SysRegion r : list) {
                if (!r.getParentId().toString().equals("0")) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", r.getRegionId());
                    map.put("pId", r.getParentId());
                    map.put("name", r.getName());
                    resultList.add(map);
                }
            }
        }
        return resultList;
    }

    /**
     * 增删改之后调用此方法更新redis中的Region
     * @throws Exception
     */
    public void updateRedisRegion() throws Exception {
        new Thread(()->{
            List<Object> list = redisUtils.lGet(RedisConstant.REGION_SINGLE_CACHE, Long.valueOf("0"), Long.valueOf("-1"));
            if(list != null && list.size() > 0){
                for(Object ll : list){
                    redisUtils.lRemove(RedisConstant.REGION_SINGLE_CACHE, 0, ll);
                }
            }
            QueryWrapper<SysRegion> qw = new QueryWrapper<>();
            qw.eq("STATUS",0);
            redisUtils.lSet(RedisConstant.REGION_SINGLE_CACHE,this.list(qw));
        }).start();
    }
}
