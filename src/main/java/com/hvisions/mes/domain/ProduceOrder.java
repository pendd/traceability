package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProduceOrder {
    private Integer produceId;

    private Date createTime;

    private Long userId;

    private Date updateTime;

    private Long updateUserId;
    /**
     *生产工单号
     */
    private String workNumber;
    /**
     * 生产批次号
     */
    private String produceNumber;

    private Long goodsId;

    private Long orderFullNameId;

    private Long lineId;

    private Long teamId;

    private Integer orderNum;

    private Date planStartTime;

    private Date planEndTime;

    private Date realStartTime;

    private Integer planAmount;

    private Date realEndTime;

    private Integer status;

    private String goodsName;
/////////////////冗余字段////////////////////
    /**
     * 产品对应的原料BOM
     */
    private List<Material> materialList;

    /**
     *  返回结果信息
     */
    private String ret;

    private String orderFullName;

    private String lineName;

    private String teamName;

    // 工单创建人姓名
    private String username;

    // 加工数量
    private Integer workNum;

    // 剩余数量
    private Integer overNum;

    // 平均加工工时
    private Double avgHour;

    // 产品码
    private String goodsCode;

    // 工序ID
    private Integer orderId;

    // 当前时间
    private Date currentTime;

    /**
     *  计划时长
     */
    private String planTime;

    /**
     *  剩余时长
     */
    private String overTime;

    private Integer modId;

    private String cmocode;

    private Integer hoursWithdrawal;

    private String code;

}