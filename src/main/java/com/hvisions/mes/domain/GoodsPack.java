package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

/**
 * 成品包装表
 *
 * @author dpeng
 * @create 2019-05-14 13:43
 */
@Data
public class GoodsPack {

    private Long packId;

    private Date createTime;

    private Long userId;

    /** 包装编码 */
    private String firstCode;

    /** 包装父编码 */
    private String secondCode;

    /** 包装类型ID */
    private Integer packTypeId;


    /////////////////// 冗余字段 ///////////////////
    /**
     *  成品码
     */
    private String goodsCode;

    /**
     *  产品码
     */
    private String code;

    /**
     *  班组
     */
    private Integer teamId;

    /**
     *  工序
     */
    private Integer orderId;

    /**
     *  生产工单号
     */
    private String produceNumber;

    /**
     *  产品ID
     */
    private Integer goodsId;
}
