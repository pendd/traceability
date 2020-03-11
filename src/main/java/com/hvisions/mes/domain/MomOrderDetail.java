package com.hvisions.mes.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class MomOrderDetail {
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
     * 订单类别(1-标准 2-非标准)
     */
    private String moClass;

    /**
     * 物料Id
     */
    private Integer partId;

    /**
     * 物料编码
     */
    private String cInvCode;

    /**
     * 生产部门编码
     */
    private String cDepCode;

    /**
     * 生产数量
     */
    private BigDecimal iModQty;

    /**
     * 物料清单Id
     */
    private Integer bomId;

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
     * 审核日期
     */
    private Date dRelsDate;

    /**
     * 审核人
     */
    private String cRelsUser;

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
     *  已扫码数量
     */
    private Integer scanAmount;

    /**
     *  物料名称
     */
    private String materialName;

    private String unit;

    public String getUnit() {
        return unit;
    }

    public Integer getAutoId() {
        return autoId;
    }

    public String getcAccId() {
        return cAccId;
    }

    public Integer getMoId() {
        return moId;
    }

    @JsonProperty("cMoCode")
    public String getCMoCode() {
        return cMoCode;
    }

    public Integer getModId() {
        return modId;
    }

    public Integer getiModRowNo() {
        return iModRowNo;
    }

    public String getMoClass() {
        return moClass;
    }

    public Integer getPartId() {
        return partId;
    }

    public String getCInvCode() {
        return cInvCode;
    }

    public String getcDepCode() {
        return cDepCode;
    }

    public BigDecimal getiModQty() {
        return iModQty;
    }

    public Integer getBomId() {
        return bomId;
    }

    public Integer getSodId() {
        return sodId;
    }

    public String getcSoCode() {
        return cSoCode;
    }

    public Integer getiSodRowNo() {
        return iSodRowNo;
    }

    public Date getdRelsDate() {
        return dRelsDate;
    }

    public String getcRelsUser() {
        return cRelsUser;
    }

    public Date getdCreateDate() {
        return dCreateDate;
    }

    public Date getMsts() {
        return msts;
    }

    public String getcFree1() {
        return cFree1;
    }

    public Integer getScanAmount() {
        return scanAmount;
    }

    public String getMaterialName() {
        return materialName;
    }
}

