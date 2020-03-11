package com.hvisions.mes.controller.vo;
/*
 * 原材料出库历史记录
 */
import java.util.Date;

public class MaterialOutHistory {
    //出库ID
    private Integer outId;
    //班组ID
    private Integer teamId;
    //用户ID
    private Integer userId;
    //原料编号
    private String materialCode;
    //生产批次号
    private String produceNumber;
    //创建时间
    private Date createTime;
    //单位
    private String unit;
    //数量
    private Integer amount;
    //是否到达 0否1是
    private Integer isArrive;
    //是否到达
    private String arrive;
    //班组
    private String teamName;
    //操作员
    private String empName;
    //原料批次ID
    private Integer batchId;
    //原料ID
    private Integer materialId;
    //原料名称
    private String materiaName;
    public Integer getOutId() {
        return outId;
    }
    public void setOutId(Integer outId) {
        this.outId = outId;
    }
    public Integer getTeamId() {
        return teamId;
    }
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
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
    public Integer getIsArrive() {
        return isArrive;
    }
    public void setIsArrive(Integer isArrive) {
        this.isArrive = isArrive;
    }
    public String getArrive() {
        return arrive;
    }
    public void setArrive(String arrive) {
        this.arrive = arrive;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public Integer getBatchId() {
        return batchId;
    }
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }
    public Integer getMaterialId() {
        return materialId;
    }
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }
    public String getMateriaName() {
        return materiaName;
    }
    public void setMateriaName(String materiaName) {
        this.materiaName = materiaName;
    }

}
