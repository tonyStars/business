package com.club.business.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.contants.RedisConstant;
import com.club.business.sys.vo.SysCompany;
import com.club.business.sys.dao.SysCompanyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.sys.vo.SysUser;
import com.club.business.util.RedisUtils;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司表 服务实现类
 * </p>
 *
 * @author 
 * @date 2019-12-09
 */
@Service
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper,SysCompany> {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 公司查询
     * @param page 当前页
     * @param pageSize 页面size
     * @param companyName 公司名称
     * @param companyCode 公司编码
     * @param typeCode 类型
     * @param status 状态
     * @return
     */
    public Map<String, Object> search(int page, int pageSize, String companyName, String companyCode, String typeCode, String companyLevel,String status) throws Exception {
        QueryWrapper<SysCompany> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(companyName)){
            qw.like("COMPANY_NAME",companyName);
        }
        if(StringUtils.isNotBlank(companyCode)){
            qw.eq("COMPANY_CODE",companyCode);
        }
        if(StringUtils.isNotBlank(typeCode)){
            qw.eq("TYPE_CODE",typeCode);
        }
        if(StringUtils.isNotBlank(companyLevel)){
            qw.eq("COMPANY_LEVEL",companyLevel);
        }
        if(StringUtils.isNotBlank(status)){
            qw.eq("STATUS",status);
        }
        Page<SysCompany> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysCompany> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 搜索框搜索公司数据
     * @param page 当前页
     * @param pageSize 页面size
     * @param keyword 搜索字段
     * @return
     */
    public Map<String, Object> searchSelect(int page, int pageSize, String keyword) throws Exception {
        QueryWrapper<SysCompany> qw = new QueryWrapper();
        qw.eq("STATUS",0);
        if(StringUtils.isNotBlank(keyword)){
            qw.like("COMPANY_NAME",keyword);
        }
        Page<SysCompany> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysCompany> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 新增公司
     * @param model 公司对象集合
     * @param user 当前用户
     */
    @Transactional(rollbackFor=Exception.class)
    public void add(SysCompany model, SysUser user) throws Exception {
        model = pageRegions(model);
        model.setEntryUserId(user.getUserId());
        model.setEntryUserName(user.getUserName());
        model.setEntryUserTime(LocalDateTime.now());
        this.save(model);
        updateRedisCompany();
    }

    /**
     * 修改公司
     * @param model 公司对象
     * @param user 当前用户
     */
    @Transactional(rollbackFor=Exception.class)
    public void edit(SysCompany model, SysUser user) throws Exception {
        model = pageRegions(model);
        model.setUpdateUserId(user.getUserId());
        model.setUpdateUserName(user.getUserName());
        model.setUpdateUserTime(LocalDateTime.now());
        this.updateById(model);
        updateRedisCompany();
    }

    /**
     * 启用公司
     * @param ids 公司表id集合
     */
    @Transactional(rollbackFor=Exception.class)
    public void ok(String ids) throws Exception {
        List<SysCompany> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysCompany model = new SysCompany();
            model.setCompanyId(Integer.valueOf(idsTemp[i]));
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
        updateRedisCompany();
    }

    /**
     * 停用公司
     * @param ids 公司表id集合
     */
    @Transactional(rollbackFor=Exception.class)
    public void del(String ids) throws Exception {
        List<SysCompany> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysCompany model = new SysCompany();
            model.setCompanyId(Integer.valueOf(idsTemp[i]));
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
        updateRedisCompany();
    }

    /**
     * 公司对象封装省市区参数
     * @param model 公司对象
     * @return
     * @throws Exception
     */
    public SysCompany pageRegions(SysCompany model) throws Exception {
        String [] aIds = model.getAddressIds().split(",");
        String [] aNames = model.getAddressNames().split(",");
        int length = aIds.length;
        if(length == 1){
            model.setStateCode(Integer.parseInt(aIds[0]));
            model.setStateName(aNames[0]);
        }
        if(length == 2){
            model.setStateCode(Integer.parseInt(aIds[0]));
            model.setStateName(aNames[0]);
            model.setCityCode(Integer.parseInt(aIds[1]));
            model.setCityName(aNames[1]);
        }
        if(length == 3){
            model.setStateCode(Integer.parseInt(aIds[0]));
            model.setStateName(aNames[0]);
            model.setCityCode(Integer.parseInt(aIds[1]));
            model.setCityName(aNames[1]);
            model.setCountyCode(Integer.parseInt(aIds[2]));
            model.setCountyName(aNames[2]);
        }
        return model;
    }

    /**
     * 公司对象将省市区参数封装在addressId和addressNames中用于回显
     * @param model 公司对象
     * @return
     * @throws Exception
     */
    public SysCompany pageBackRegions(SysCompany model) throws Exception {
        String aIds = "";
        String aNames = "";
        if(model.getStateCode() != null && StringUtils.isNotBlank(model.getStateName())){
            aIds = model.getStateCode().toString();
            aNames = model.getStateName();
        }
        if(model.getCityCode() != null && StringUtils.isNotBlank(model.getCityName())){
            aIds = aIds + "," + model.getCityCode();
            aNames = aNames + "," + model.getCityName();
        }
        if(model.getCountyCode() != null && StringUtils.isNotBlank(model.getCountyName())){
            aIds = aIds + "," + model.getCountyCode();
            aNames = aNames + "," + model.getCountyName();
        }
        model.setAddressIds(aIds);
        model.setAddressNames(aNames);
        return model;
    }

    /**
     * 增删改之后调用此方法更新redis中的Company
     * @throws Exception
     */
    public void updateRedisCompany() throws Exception {
        new Thread(()->{
            List<Object> list = redisUtils.lGet(RedisConstant.COMPANY_LIST_CACHE, Long.valueOf("0"), Long.valueOf("-1"));
            if(list != null && list.size() > 0){
                for(Object ll : list){
                    redisUtils.lRemove(RedisConstant.COMPANY_LIST_CACHE, 0, ll);
                }
            }
            QueryWrapper<SysCompany> qw = new QueryWrapper<>();
            qw.eq("STATUS",0);
            redisUtils.lSet(RedisConstant.COMPANY_LIST_CACHE,this.list(qw));
        }).start();
    }
}
