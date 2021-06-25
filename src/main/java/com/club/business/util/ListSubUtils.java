package com.club.business.util;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 将List按照指定行数分页
 *
 * @author Tom
 * @param <T> 实体对象
 * @date 2019-12-12
 */
public class ListSubUtils<T> {
	
	private static ListSubUtils utils = null;
	
	public static ListSubUtils getInstance(){
		if(null == utils){
			utils = new ListSubUtils();
		}
		return utils;
	}

    /**  
     * 对list集合进行分页处理(示例：ListSubUtils.getInstance().listSplit(list, page, pageSize))
     *   
     * @return  
     */  
    public List<T> listSplit(List<T> list,int curpage,int pagesize) {
		int page = 1; //当前页面
		int rows = 1;//显示多少行
		int total;//总记录条数
		List<T> newList = new ArrayList<>();
		String pageTrans = String.valueOf(curpage);
		String sizeTrans = String.valueOf(pagesize);
		if(StringUtils.isBlank(pageTrans)){
			page = 1;
		}else{
			if(curpage > 1){
				page = curpage;
			}
		}
		if(StringUtils.isBlank(sizeTrans)){
			rows = 1;//不传此参数默认返回一条数据
		}else{
			if(pagesize > 1){
				rows = pagesize;
			}
		}
		total = list.size();
		//判断如果查询的行数，比实际行数要多，那么报错
		int searchSize = 0;
		if(page == 1){
			searchSize = rows;
		}else{
			searchSize = rows*(page-1);
		}
		if(searchSize <= total){
			newList = list.subList(rows*(page-1), ((rows*page)>total?total:(rows*page)));
		}else if(searchSize > total && page == 1){
			newList = list;
		}
		return newList;
    }  
}
