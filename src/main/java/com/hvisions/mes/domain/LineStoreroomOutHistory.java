package com.hvisions.mes.domain;


import java.util.Date;


public class LineStoreroomOutHistory {
    private Integer historyId;

    private String materialCode;

    private Long lineStoreroomId;

    private Long inId;

    private Long userId;

    private Date createTime;

    private String unit;

    private Integer amount;

    private Long goType;

    private Long storeroomId;

    private String storeroomName;

    private String materialName;

    private Integer isArrive;

    private String supplierName;

    private Integer batchId;





    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Long getLineStoreroomId() {
        return lineStoreroomId;
    }

    public void setLineStoreroomId(Long lineStoreroomId) {
        this.lineStoreroomId = lineStoreroomId;
    }

    public Long getInId() {
        return inId;
    }

    public void setInId(Long inId) {
        this.inId = inId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getGoType() {
        return goType;
    }

    public void setGoType(Long goType) {
        this.goType = goType;
    }

    public Long getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(Long storeroomId) {
        this.storeroomId = storeroomId;
    }

    public Integer getIsArrive() {
        return isArrive;
    }

    public void setIsArrive(Integer isArrive) {
        this.isArrive = isArrive;
    }

}