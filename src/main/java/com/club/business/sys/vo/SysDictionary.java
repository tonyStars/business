package com.club.business.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author 
 * @date 2019-12-04
 */
public class SysDictionary implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 类型名称
     */
    @TableField("TYPE_NAME")
    private String typeName;
    
    /**
     * 子项目名称
     */
    @TableField("ITEM_NAME")
    private String itemName;
    
    /**
     * 值
     */
    @TableField("VALUE")
    private Integer value;
    
    /**
     * 备注
     */
    @TableField("NOTE")
    private String note;
    
    /**
     * 排序
     */
    @TableField("ORDER_ID")
    private Integer orderId;
    
    /**
     * 状态：0-正常、-1-作废
     */
    @TableField("STATUS")
    private Integer status;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysDictionary{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", value=" + value +
                ", note='" + note + '\'' +
                ", orderId=" + orderId +
                ", status=" + status +
                '}';
    }
}
