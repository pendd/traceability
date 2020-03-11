package com.hvisions.mes.domain;


import lombok.Data;

import java.util.Date;

@Data
public class GoodsMaterial {
    private Integer gmId;

    private Date createTime;

    private Integer userId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer goodsId;

    private Integer materialId;

    private String goodsName;

    private String materialName;

    private Integer quantity;

    ////////////////////////////  冗余字段  ////////////////////////////
    /**
     *  生产工单号
     */
    private String workNumber;

    private String goodsCode;

    private String specs;

}