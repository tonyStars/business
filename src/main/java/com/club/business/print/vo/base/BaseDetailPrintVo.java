package com.club.business.print.vo.base;

import java.io.Serializable;

/**
 * @Description  
 * @author Tom
 * @date 2019-12-16
 * 
 */
public class BaseDetailPrintVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * 序号
     */
    private int serialNum;
    
	public int getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}

}
