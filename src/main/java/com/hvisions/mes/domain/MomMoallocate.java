package com.hvisions.mes.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class MomMoallocate {
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
    private Integer moId;

    /**
     * 单据编号
     */
    private String cMoCode;

    /**
     *
     */
    private Integer modId;

    /**
     * 行号
     */
    private Integer iModRowNo;

    /**
     * 订单子件Id
     */
    private Integer allocteId;

    /**
     * 行号
     */
    private Integer sortSeq;

    /**
     * 子件物料Id
     */
    private Integer partId;

    /**
     * 子件物料编码
     */
    private String cInvCode;

    /**
     * 应领数量
     */
    private BigDecimal qty;

    /**
     * 累计已领数量
     */
    private BigDecimal issQty;

    /**
     * 领用仓库编码
     */
    private String cWhCode;

    /**
     * 供应类型(1-入库倒冲 3-领料)
     */
    private Integer wipType;

    /**
     * 记录创建日期
     */
    private Date dCreateDate;

    /**
     * 时间戳
     */
    private Date msts;

    /**
     * 自由项1(颜色)
     */
    private String cFree1;
}

