package com.hvisions.mes.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class Rdrecords11 {
    /**
     * 自动编号(标识列)
     */
    private Integer autoId;

    /**
     * ERP账套号
     */
    private String cAccId;

    /**
     * 单据主表Id
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String cCode;

    /**
     * 单据子表Id
     */
    private Integer iRdsId;

    /**
     * 行号
     */
    private Integer iRowNo;

    /**
     * 物料编码
     */
    private String cInvCode;

    /**
     * 入库数量
     */
    private BigDecimal iQuantity;

    /**
     * 批号
     */
    private String cBatch;

    /**
     * 保质期
     */
    private Date dVDate;

    /**
     * 父项产品编码
     */
    private String cParentInvCode;

    /**
     * 委外订单子表Id
     */
    private Integer omdId;

    /**
     * 委外订单号
     */
    private String cOmCode;

    /**
     * 委外订单行号
     */
    private Integer iOmdRowNo;

    /**
     * 生产订单子件表id
     */
    private Integer allocateId;

    /**
     * 生产订单子表Id
     */
    private Integer modId;

    /**
     * 生产订单号
     */
    private String cMoCode;

    /**
     * 生产订单行号
     */
    private Integer iModRowNo;

    /**
     * 记录创建日期
     */
    private String dCreateDate;

    /**
     * 时间戳
     */
    private Date msts;

    /**
     * 自由项1(颜色)
     */
    private String cFree1;

    private String ctype;

    private String cguid;
}

