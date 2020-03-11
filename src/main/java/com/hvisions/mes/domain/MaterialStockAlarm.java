package com.hvisions.mes.domain;

import lombok.Data;

/**
 * @description  物料上下限报警
 * @author dpeng
 * @date 2019-07-16 15:50
 */

@Data
public class MaterialStockAlarm {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 原料ID
     */
    private Integer materialId;

    /**
     * 0 超过上限   1低于下限
     */
    private Integer alarmType;

    /**
     *  库房
     */
    private Integer storeroomId;

    /**
     *  库存量
     */
    private Integer actualcount;

    /**
     *  报警数量
     */
    private Integer alarmAmount;



    /**
     *  原料名称
     */
    private String materialName;

    /**
     *  原料标识码
     */
    private String materialSignCode;
}

