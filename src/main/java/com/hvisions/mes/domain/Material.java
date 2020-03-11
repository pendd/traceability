package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Material {

    private Integer materialId;
    private String materialName;
    private Integer supplierId;
    private String supplierName;
    private Integer isCheck;
    private Date createTime;
    private Integer available;
    //创建人
    private Integer userId;
    //更新时间
    private Date updateTime;
    //更新人
    private Integer updateUserId;
    private Integer alarmStock;
    private Integer shelfLife;
    private String shelfLiftUnit;
    /** 原料标识码 (红色、黑色) */
    private String materialSignCode;

    /** 原料规格 */
    private String specs;

    /** 报警库存上限 */
    private Integer alarmStockUp;

    /** 报警库存下限 */
    private Integer alarmStockDown;

    /** 物料类型 */
    private String materialType;

    ////////////////////////冗余字段///////////////////////////
    /** 库存量 */
    private Integer actualCount;

    /** 原批次号 */
    private String supplierNumber;

    private String materialCode;

    private Integer batchId;

    private String unit;

    /**
     *   数据库中已没有  待删除
     */
    private Integer parentId;
    private String parentName;

    private Integer amount;

    /**
     *  已退回的数量
     */
    private Integer returnAmount;

    private Integer storeroomId;

    private Integer callOffId;

    private Integer withdrawalId;

}
