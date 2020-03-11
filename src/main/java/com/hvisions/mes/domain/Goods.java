package com.hvisions.mes.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Goods {

    private Integer goodsId;

    private String goodsName;

    private Date createTime;

    private Integer available;

    private String goodsType;

    private Integer shelfLife;

    private Integer userId;

    private Date updateTime;

    private Integer updateUserId;

    private String produceNumber;

    private String goodsCode;

    private String specs;

    /**
     *  一件产品生产所需的标准时长，单位 小时
     */
    private BigDecimal standardHours;
}
