package com.club.business.goodluck.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 双色球往期数据表
 * </p>
 *
 * @author 
 * @date 2020-08-26
 */
public class BusinessBichromaticSphere extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 期号(例:2020-081)
     */
    private String issueNumber;
    
    /**
     * 开奖时间(例:2020-08-25)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openingTime;
    
    /**
     * 红号码1(01-33)
     */
    private Integer redNumber1;
    
    /**
     * 红号码2(01-33)
     */
    private Integer redNumber2;
    
    /**
     * 红号码3(01-33)
     */
    private Integer redNumber3;
    
    /**
     * 红号码4(01-33)
     */
    private Integer redNumber4;
    
    /**
     * 红号码5(01-33)
     */
    private Integer redNumber5;
    
    /**
     * 红号码6(01-33)
     */
    private Integer redNumber6;
    
    /**
     * 蓝号码7(01-16)
     */
    private Integer blueNumber7;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public BusinessBichromaticSphere() {
    }

    public BusinessBichromaticSphere(Integer redNumber1, Integer redNumber2, Integer redNumber3, Integer redNumber4, Integer redNumber5, Integer redNumber6, Integer blueNumber7) {
        this.redNumber1 = redNumber1;
        this.redNumber2 = redNumber2;
        this.redNumber3 = redNumber3;
        this.redNumber4 = redNumber4;
        this.redNumber5 = redNumber5;
        this.redNumber6 = redNumber6;
        this.blueNumber7 = blueNumber7;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }
    
    public LocalDate getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDate openingTime) {
        this.openingTime = openingTime;
    }
    
    public Integer getRedNumber1() {
        return redNumber1;
    }

    public void setRedNumber1(Integer redNumber1) {
        this.redNumber1 = redNumber1;
    }
    
    public Integer getRedNumber2() {
        return redNumber2;
    }

    public void setRedNumber2(Integer redNumber2) {
        this.redNumber2 = redNumber2;
    }
    
    public Integer getRedNumber3() {
        return redNumber3;
    }

    public void setRedNumber3(Integer redNumber3) {
        this.redNumber3 = redNumber3;
    }
    
    public Integer getRedNumber4() {
        return redNumber4;
    }

    public void setRedNumber4(Integer redNumber4) {
        this.redNumber4 = redNumber4;
    }
    
    public Integer getRedNumber5() {
        return redNumber5;
    }

    public void setRedNumber5(Integer redNumber5) {
        this.redNumber5 = redNumber5;
    }
    
    public Integer getRedNumber6() {
        return redNumber6;
    }

    public void setRedNumber6(Integer redNumber6) {
        this.redNumber6 = redNumber6;
    }
    
    public Integer getBlueNumber7() {
        return blueNumber7;
    }

    public void setBlueNumber7(Integer blueNumber7) {
        this.blueNumber7 = blueNumber7;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    @Override
    public String toString() {
        return "BusinessBichromaticSphere{" +
        "id=" + id +
        ", issueNumber=" + issueNumber +
        ", openingTime=" + openingTime +
        ", redNumber1=" + redNumber1 +
        ", redNumber2=" + redNumber2 +
        ", redNumber3=" + redNumber3 +
        ", redNumber4=" + redNumber4 +
        ", redNumber5=" + redNumber5 +
        ", redNumber6=" + redNumber6 +
        ", blueNumber7=" + blueNumber7 +
        ", createTime=" + createTime +
        "}";
    }
}
