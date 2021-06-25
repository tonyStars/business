package com.club.business.goodluck.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.goodluck.vo.BusinessBichromaticSphere;
import com.club.business.goodluck.dao.BusinessBichromaticSphereMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.goodluck.vo.LuckNoModel;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 双色球往期数据表 服务实现类
 * </p>
 *
 * @author 
 * @date 2020-08-26
 */
@Service
public class BusinessBichromaticSphereServiceImpl extends ServiceImpl<BusinessBichromaticSphereMapper,BusinessBichromaticSphere> {

    @Autowired
    private BusinessSuperLottoServiceImpl superLottoService;

    /**
     * 分页查询
     * @param sphere 对象参数
     * @return
     */
    public Map<String, Object> search(BusinessBichromaticSphere sphere) throws Exception {
        QueryWrapper<BusinessBichromaticSphere> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(sphere.getIssueNumber())){
            qw.like("issue_number",sphere.getIssueNumber());
        }
        if(sphere.getStartDate() != null && sphere.getEndDate() != null){
            qw.between("opening_time",sphere.getStartDate(),sphere.getEndDate());
        }
        qw.orderByDesc("opening_time");
        Page<BusinessBichromaticSphere> pageInfo = new Page<>();
        pageInfo.setCurrent(sphere.getPage());
        pageInfo.setSize(sphere.getLimit());
        Page<BusinessBichromaticSphere> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 新增号码
     * @param model 对象参数
     */
    @Transactional(rollbackFor=Exception.class)
    public void add(BusinessBichromaticSphere model) throws Exception {
        QueryWrapper<BusinessBichromaticSphere> qw = new QueryWrapper<BusinessBichromaticSphere>()
                .eq("issue_number",model.getIssueNumber());
        int count = this.count(qw);
        if(count > 0){
            throw new BusinessException("该期号已存在!");
        }
        model.setCreateTime(LocalDateTime.now());
        this.save(model);
    }

    /**
     * 编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    public void edit(BusinessBichromaticSphere model) throws Exception {
        this.updateById(model);
    }

    /**
     * 抽取幸运号码
     * @return
     */
    public BusinessBichromaticSphere luck() {
        BusinessBichromaticSphere res = new BusinessBichromaticSphere();
        List<BusinessBichromaticSphere> list = this.list();
        if(list != null && list.size() > 0){
            List<LuckNoModel> luckNo = list.stream().map(p -> new LuckNoModel(p.getRedNumber1(),
                    p.getRedNumber2(),
                    p.getRedNumber3(),
                    p.getRedNumber4(),
                    p.getRedNumber5(),
                    p.getRedNumber6(),
                    p.getBlueNumber7())).collect(Collectors.toList());
            List<Integer> rds = superLottoService.getRandoms(superLottoService.getRandomList(luckNo));
            res = new BusinessBichromaticSphere(rds.get(0),rds.get(1),rds.get(2),rds.get(3),rds.get(4),rds.get(5),rds.get(6));
        }
        return res;
    }
}
