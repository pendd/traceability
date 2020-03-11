package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DispatchList {
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
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dDate;

    /**
     * 客户编码
     */
    private String cCusCode;

    /**
     * 部门编码
     */
    private String cDepCode;

    /**
     * 业务员编码
     */
    private String cPersonCode;

    /**
     * 发货地址
     */
    private String cShipAddress;

    /**
     * 销售订单号
     */
    private String cSoCode;

    /**
     * 销售类型
     */
    private String cSTCode;

    /**
     * 销售类型名称
     */
    private String cSTName;

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
    private Date dVeriDate;

    /**
     * 记录创建日期
     */
    private Date dCreateDate;

    /**
     * 时间戳
     */
    private Date msts;

    /**
     *
     */
    private Integer bMesIsRead;

    /**
     *
     */
    private Date dMesReadDate;


    /**
     *  客户
     */
    private String customName;

    /**
     *  业务员
     */
    private String personName;

    /**
     *  部门
     */
    private String depName;

    /**
     *  仓库编码
     */
    private String cWhCode;

    /**
     *  来源单号
     */
    private String cSource;
}

