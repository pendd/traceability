package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

/**
 * 设备停机异常记录实体
 *
 * @author dpeng
 * @create 2019-06-21 16:29
 */
@Data
public class EquipmentAbnormal {

    /**
     *  主键
     */
    private Long id;

    /**
     *  设备ID
     */
    private Long equipmentId;

    /**
     *  异常状态 （0 未处理 1 已处理）
     */
    private Integer isHandle;

    /**
     *  发生原因
     */
    private String reason;

    /**
     *  发生时间
     */
    private Date createTime;

    /**
     *  处理时间
     */
    private Date completeTime;

    /**
     *  处理方法
     */
    private String method;


    ///////////////////////// 冗余字段 ////////////////////
    /**
     *  设备名称
     */
    private String equipmentName;

    /**
     *  数量
     */
    private Integer amount;
}
