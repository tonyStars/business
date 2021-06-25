package com.club.business.goodluck.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.goodluck.vo.BusinessSuperLotto;
import com.club.business.goodluck.dao.BusinessSuperLottoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.goodluck.vo.LuckNoModel;
import com.club.business.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 大乐透往期数据表 服务实现类
 * </p>
 *
 * @author 
 * @date 2020-08-26
 */
@Service
public class BusinessSuperLottoServiceImpl extends ServiceImpl<BusinessSuperLottoMapper,BusinessSuperLotto> {

    /**
     * 分页查询
     * @param lotto 对象参数
     * @return
     * @throws Exception
     */
    public Map<String, Object> search(BusinessSuperLotto lotto) throws Exception {
        QueryWrapper<BusinessSuperLotto> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(lotto.getIssueNumber())){
            qw.like("issue_number",lotto.getIssueNumber());
        }
        if(lotto.getStartDate() != null && lotto.getEndDate() != null){
            qw.between("opening_time",lotto.getStartDate(),lotto.getEndDate());
        }
        qw.orderByDesc("opening_time");
        Page<BusinessSuperLotto> pageInfo = new Page<>();
        pageInfo.setCurrent(lotto.getPage());
        pageInfo.setSize(lotto.getLimit());
        Page<BusinessSuperLotto> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 新增号码
     * @param model 大乐透对象
     */
    @Transactional(rollbackFor=Exception.class)
    public void add(BusinessSuperLotto model) throws Exception {
        QueryWrapper<BusinessSuperLotto> qw = new QueryWrapper<BusinessSuperLotto>()
                .eq("issue_number",model.getIssueNumber());
        int count = this.count(qw);
        if(count > 0){
            throw new BusinessException("该期号已存在!");
        }
        model.setCreateTime(LocalDateTime.now());
        this.save(model);
    }

    /**
     * 编辑号码
     * @param model 大乐透对象
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void edit(BusinessSuperLotto model) throws Exception {
        this.updateById(model);
    }

    /**
     * 抽取幸运号码
     * @return
     */
    public BusinessSuperLotto luck() {
        BusinessSuperLotto res = new BusinessSuperLotto();
        List<BusinessSuperLotto> list = this.list();
        if(list != null && list.size() > 0){
            List<LuckNoModel> luckNo = list.stream().map(p -> new LuckNoModel(p.getRedNumber1(),
                    p.getRedNumber2(),
                    p.getRedNumber3(),
                    p.getRedNumber4(),
                    p.getRedNumber5(),
                    p.getBlueNumber6(),
                    p.getBlueNumber7())).collect(Collectors.toList());
            List<Integer> rds = getRandoms(getRandomList(luckNo));
            res = new BusinessSuperLotto(rds.get(0),rds.get(1),rds.get(2),rds.get(3),rds.get(4),rds.get(5),rds.get(6));
        }
        return res;
    }

    /**
     * 将封装了幸运数字的对象集合,取出每一组相同位置的号码封装成集合,并一起组成一组新的集合
     * @param luckNo 幸运数字的对象集合
     * @return
     */
    public List<List<Integer>> getRandomList(List<LuckNoModel> luckNo){
        List<List<Integer>> pps = new ArrayList<>();
        pps.add(luckNo.stream().map(LuckNoModel::getNo1).collect(Collectors.toList()));
        pps.add(luckNo.stream().map(LuckNoModel::getNo2).collect(Collectors.toList()));
        pps.add(luckNo.stream().map(LuckNoModel::getNo3).collect(Collectors.toList()));
        pps.add(luckNo.stream().map(LuckNoModel::getNo4).collect(Collectors.toList()));
        pps.add(luckNo.stream().map(LuckNoModel::getNo5).collect(Collectors.toList()));
        pps.add(luckNo.stream().map(LuckNoModel::getNo6).collect(Collectors.toList()));
        pps.add(luckNo.stream().map(LuckNoModel::getNo7).collect(Collectors.toList()));
        return pps;
    }

    /**
     * 设置取出出现频率最高的前几位数
     */
    private static final int randomNo = 3;
    /**
     * 通过特定逻辑获取最后的随机数字集合
     * 逻辑：将集合中每一组集合获取出来,再将每一组集合中所有相同元素出现的次数封装为Map并倒序排序,
     * 并将map中的key值封装到List集合中,取出前三个,并随机取出其中一个,依次将各个集合中随机取出一个封装成一个新的号码集合
     * @param data
     * @return
     */
    public List<Integer> getRandoms(List<List<Integer>> data){
        int topNo;
        if(data.get(0).size() < randomNo){
            topNo = data.get(0).size();
        }else{
            topNo = randomNo;
        }
        return luckListNo(data,topNo,new Random());
    }

    /**
     * 根据传入数据获取幸运数字集合(不重复)
     * @param data 集合参数
     * @param topNo 取出前几位出现频率最高的数字
     * @return
     */
    public List<Integer> luckListNo(List<List<Integer>> data,int topNo,Random random){
        List<Integer> list = new ArrayList<>();
        for(List<Integer> ls : data){
            List<Integer> ll = new ArrayList<>(sortByValueDesc(ls.stream().collect(Collectors.groupingBy(p -> p,Collectors.counting()))).keySet()).subList(0, topNo);
            list.add(ll.get(random.nextInt(ll.size())));
        }
        long count = list.stream().distinct().count();
        if(count < list.size()){
            list = luckListNo(data,topNo,random);
        }
        return list;
    }

    /**
     * 将map根据value值倒序排序
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
