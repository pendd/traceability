package com.hvisions.mes.domain;

import java.util.Date;

public class Supplier {
    private Integer supplierId;

    private String supplierName;

    private String address;

    private String principal;

    private String telephone;

    private String telephoneBackup;

    private Date createTime;

    private Integer available;

    private String remark;

    private Integer userId;
    //更新时间
    private Date updateTime;
    //更新人
    private Integer updateUserId;
    private String supplierType;
    private Integer supplyYears;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public Integer getSupplyYears() {
        return supplyYears;
    }

    public void setSupplyYears(Integer supplyYears) {
        this.supplyYears = supplyYears;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephoneBackup() {
        return telephoneBackup;
    }

    public void setTelephoneBackup(String telephoneBackup) {
        this.telephoneBackup = telephoneBackup;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
