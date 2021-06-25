package com.club.business.goodluck.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 公用参数对象
 *
 * @author Tom
 * @date 2020-08-26
 */
public class BaseModel implements Serializable {

    /**
     * 当前页
     */
    @TableField(exist = false)
    private int page;

    /**
     * 页面条数
     */
    @TableField(exist = false)
    private int limit;

    /**
     * 查询开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private LocalDate startDate;

    /**
     * 查询结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private LocalDate endDate;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
