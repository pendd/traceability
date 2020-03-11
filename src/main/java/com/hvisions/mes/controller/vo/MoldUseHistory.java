package com.hvisions.mes.controller.vo;

import java.util.Date;

/*
 * 模具使用记录
 */
public class MoldUseHistory {
    //模具ID
    private Integer moldId;
    //模具名
    private String moldName;
    //产线名
    private String lineName;
    //设备名
    private String equipmentName;
    //操作人
    private String empName;
    //生产批次号
    private String produceNumber;
    //工单号
    private String workNumber;
    //备注
    private String remark;
    //创建时间
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getMoldId() {
        return moldId;
    }
    public void setMoldId(Integer moldId) {
        this.moldId = moldId;
    }
    public String getMoldName() {
        return moldName;
    }
    public void setMoldName(String moldName) {
        this.moldName = moldName;
    }
    public String getLineName() {
        return lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    public String getEquipmentName() {
        return equipmentName;
    }
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public String getProduceNumber() {
        return produceNumber;
    }
    public void setProduceNumber(String produceNumber) {
        this.produceNumber = produceNumber;
    }
    public String getWorkNumber() {
        return workNumber;
    }
    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

}
