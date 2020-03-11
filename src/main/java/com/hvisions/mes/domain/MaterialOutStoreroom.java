package com.hvisions.mes.domain;

import java.sql.Timestamp;

/**
*
* 实体类
* @author mtyu
* @since 2019-03-05
*/
public class MaterialOutStoreroom {
    /*
     * 主键
     */
    private Integer outId;
    /*
     * 库管员ID
     */
    private Integer userId;
    /*
     * 班组ID
     */
    private Integer teamId;
    /*
     * 原料二维码
     */
    private String materialCode;
    /*
     * 生产工单号
     */
    private String produceNumber;
    /*
     * 开始时间
     */
    private Timestamp createTime;
    /*
     * 单位
     */
    private String unit;
    /*
     * 数量
     */
    private Integer amount;
    /*
     * 是否到达
     */

    private Integer isArrive;
    /*
     * 备注
     */
    private String remark;
    /*
     * 供应商
     */
    private String supplierName;
    /*
     * 备注
     */
    private String storeroomName;
    /*
     * 原料
     */
    private String materialName;

    private String lineName;

    public String getLineName() {
        return lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    public Integer getOutId() {
        return outId;
    }
    public void setOutId(Integer outId) {
        this.outId = outId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getTeamId() {
        return teamId;
    }
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
    public String getMaterialCode() {
        return materialCode;
    }
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
    public String getProduceNumber() {
        return produceNumber;
    }
    public void setProduceNumber(String produceNumber) {
        this.produceNumber = produceNumber;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime) {
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
    public Integer getIsArrive() {
        return isArrive;
    }
    public void setIsArrive(Integer isArrive) {
        this.isArrive = isArrive;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getStoreroomName() {
        return storeroomName;
    }
    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }
    public String getMaterialName() {
        return materialName;
    }
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }




}
