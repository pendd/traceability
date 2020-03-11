package com.hvisions.mes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 原料出入库历史实体类
 *      替代 MaterialInStoreroom  和  MaterialOutStoreroom
 *
 * @author dpeng
 * @create 2019-03-14 13:26
 */
@Data
public class MaterialStoreroomHistory {

    private Integer historyId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Long userId;

    private Long teamId;

    private Long batchId;

    private String materialCode;

    private String produceNumber;

    private String unit;

    private Double amount;

    private Integer qualified;

    private Integer isHistory;

    private Integer inOutType;

    private Integer isRealIn;

    private Integer isArrive;

    ////////////冗余字段/////////////////
    private String materialName;//原料名称

    private String storeroomName;//库房名称

    private String supplierName; // 供应商名称

    private String lineName;  // 产线名称

    private String specs;  // 原料规格

    private Integer storeroomId;

    private String materialSignCode;

    private Integer materialId;

    /**
     *  已退回的数量
     */
    private Integer returnAmount;

    private String userName;
}
