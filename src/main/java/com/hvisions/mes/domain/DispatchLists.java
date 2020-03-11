package com.hvisions.mes.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class DispatchLists {
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
    private Integer iDlsId;

    /**
     * 行号
     */
    private Integer iRowNo;

    /**
     * 物料编码
     */
    private String cInvCode;

    /**
     * 仓库编码
     */
    private String cWhCode;

    /**
     * 发货数量
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
     * 销售订单子表Id
     */
    private Integer sodId;

    /**
     * 销售订单号
     */
    private String cSoCode;

    /**
     * 销售订单行号
     */
    private Integer iSodRowNo;

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

    /**
     * Mes是否行关闭
     */
    private Boolean bMesRowClosed;

    /**
     * Mes行关闭日期
     */
    private Date dMesRowCloseDate;


    /**
     *  产品名称
     */
    private String productName;

    /**
     *  库房名称
     */
    private String storeroomName;

    /**
     *  已扫码数量
     */
    private Integer scanAmount;

    /**
     *  是否已出库 0 未出库  1已出库
     */
    private Integer isOut;

    private String unit;
}

