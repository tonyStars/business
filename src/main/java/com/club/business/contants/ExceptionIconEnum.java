package com.club.business.contants;

/**
 * 异常提示图标枚举类
 *
 * @author Tom
 * @date 2019-11-19
 */
public enum ExceptionIconEnum {

    PROMPT("提示",0),
    ERROR("错误",2),
    CONFIRM("询问",3),
    LOCKED("锁定",4),
    ;

    String alertInfo;
    int iconType;

    ExceptionIconEnum(String alertInfo, int alertType){
        this.alertInfo = alertInfo;
        this.iconType = alertType;
    }

    public int getIconType() {
        return iconType;
    }
}
