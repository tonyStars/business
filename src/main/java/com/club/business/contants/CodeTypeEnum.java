package com.club.business.contants;

/**
 * Redis编码生成枚举类
 */
public enum CodeTypeEnum {

	USER("USER_CODE_KEY", "用户", 10000L, 5),
	GOODSSKUID("GOODS_SKU_ID", "货品SkuId", 1000000000L, 10);

	private String key ;

	private String name ;

	private Long startNum ;

	private int length ;

	private CodeTypeEnum(String key, String name, Long startNum, int length){
        this.key = key ;
        this.name = name ;
        this.startNum = startNum ;
        this.length = length ;
    }
	
	public static CodeTypeEnum getValueByKey(String key) {
	      for (CodeTypeEnum o : CodeTypeEnum.values()) {
	        if (o.getKey().equals(key)) {
	          return o ;
	        }
	      }
	      return null;
	    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStartNum() {
		return startNum;
	}

	public void setStartNum(Long startNum) {
		this.startNum = startNum;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
