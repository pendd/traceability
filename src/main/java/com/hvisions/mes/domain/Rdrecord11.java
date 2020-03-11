package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

@Data
public class Rdrecord11 {
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
     * 单据日期
     */
    private Date dDate;

    /**
     * 供应商编码(委外发料时填)
     */
    private String cVenCode;

    /**
     * 部门编码
     */
    private String cDepCode;

    /**
     * 业务员编码
     */
    private String cPersonCode;

    /**
     * 仓库编码
     */
    private String cWhCode;

    /**
     * 入库类别编码
     */
    private String cRdCode;

    /**
     * 入库类别名称
     */
    private String cRdName;

    /**
     * 业务类型(领料 委外发料等)
     */
    private String cBusType;

    /**
     * 来源单类型(生产订单 委外订单等)
     */
    private String cSource;

    /**
     * 子表行数
     */
    private Integer iSubRows;

    /**
     * 备注
     */
    private String cMemo;

    /**
     * 制单人
     */
    private String cMaker;

    /**
     * 审核人
     */
    private String cVerifier;

    /**
     * 审核日期
     */
    private String dVeriDate;

    /**
     * 记录创建日期
     */
    private String dCreateDate;

    /**
     * 时间戳
     */
    private String msts;

    /**
     * 单据来源   (MES 或  ERP)
     */
    private String ctype;

    private String cguid;

    private Integer bErpIsRead;
}

