package com.hvisions.mes.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class MomOrder {

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
     * 单据日期
     */
    private Date dDate;

    /**
     * 制单人
     */
    private String cMaker;

    /**
     * 审核人
     */
    private String cVerifier;

    /**
     * 子表行数
     */
    private Integer iSubRows;

    /**
     * 记录创建日期
     */
    private Date dCreateDate;

    /**
     * 时间戳
     */
    private Date msts;

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
    public String getcMoCode() {
        return cMoCode;
    }

    public Date getdDate() {
        return dDate;
    }

    public String getcMaker() {
        return cMaker;
    }

    public String getcVerifier() {
        return cVerifier;
    }

    public Integer getiSubRows() {
        return iSubRows;
    }

    public Date getdCreateDate() {
        return dCreateDate;
    }

    public Date getMsts() {
        return msts;
    }
}

