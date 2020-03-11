package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

/**
 * 退料实体
 *
 * @author dpeng
 * @create 2019-06-17 8:48
 */
@Data
public class Withdrawal {

    /**
     *  主键
     */
    private Integer withdrawalId;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  退料人
     */
    private Integer userId;

    /**
     *  生产工单号
     */
    private String workNumber;

    /**
     *  原料ID
     */
    private Integer materialId;

    /**
     *  数量
     */
    private Integer amount;

    /**
     *  库房ID
     */
    private Integer storeroomId;

    /**
     *  是否到达  0未到达  1到达
     */
    private Integer state;

    /**
     *  已退回的数量
     */
    private Integer returnAmount;

    /**
     *  是否发送退料超期通知  0 未发生  1 已发送
     */
    private Integer sendOrNot;

    //////////////////////  冗余字段  //////////////////////////

    private String materialName;

    private String materialSignCode;

    private String specs;


    /**
     *  可能是包装码 也可能是原料码
     */
    private String code;
}
