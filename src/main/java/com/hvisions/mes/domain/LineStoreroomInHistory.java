package com.hvisions.mes.domain;



import java.util.Date;

public class LineStoreroomInHistory {
    private Integer historyId;

    private Integer storeroomId;
    private Long lineStoreroomId;

    private Long inId;

    private Long userId;

    private Date createTime;

    private String unit;

    private Integer amount;

    private Long outHistoryId;

    private String materialName;

    private String materialCode;



    public Integer getStoreroomId() {
        return storeroomId;
    }

    public void setStoreroomId(Integer storeroomId) {
        this.storeroomId = storeroomId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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

    public Long getOutHistoryId() {
        return outHistoryId;
    }

    public void setOutHistoryId(Long outHistoryId) {
        this.outHistoryId = outHistoryId;
    }

}