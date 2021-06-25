package com.club.business.common.vo;

import java.io.Serializable;

/**
 * 分页参数获取
 *
 * @author Tom
 * @date 2019-12-09
 */
public class PageParam implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 查询页码
     */
    private Integer page;
    /**
     * 每页数量
     */
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "PageParam{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
