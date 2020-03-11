package com.hvisions.mes.domain;



import java.util.Date;

public class SalesRecord {
    private Integer salesRecordId;

    private String salesName;

    private Integer companyId;

    private Date createTime;

    private Integer userId;

    public Integer getSalesRecordId() {
        return salesRecordId;
    }

    public void setSalesRecordId(Integer salesRecordId) {
        this.salesRecordId = salesRecordId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}