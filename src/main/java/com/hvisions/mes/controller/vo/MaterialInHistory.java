package com.hvisions.mes.controller.vo;
/*
 * 原材料入库历史记录
 */
import java.util.Date;

public class MaterialInHistory {
    //入库ID
    private Integer inId;
    //原料新编号
    private String materialCode;
    //原料批次号
    private Integer batchId;
    //单位
    private String unit;
    //数量
    private Integer amount;
    //创建时间
    private Date createTime;
    //是否原始入库 0否1是
    private Integer isHistory;
    //是否原始入库
    private String history;
    //是否入库 0否1是
    private Integer isInStoreroom;
    //是否入库
    private String inStoreroom;
    //备注
    private String remark;
    //原料ID
    private Integer materialId;
    //原料名称
    private String materiaName;
    //供应商ID
    private Integer supplierId;
    //供应商名称
    private String supplierName;
    //原批次号（供应商）
    private String supplierNumber;
    //是否免检
    private String check;

    public String getCheck() {
        return check;
    }
    public void setCheck(String check) {
        this.check = check;
    }
    public String getSupplierNumber() {
        return supplierNumber;
    }
    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }
    public Integer getInId() {
        return inId;
    }
    public void setInId(Integer inId) {
        this.inId = inId;
    }
    public String getMaterialCode() {
        return materialCode;
    }
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
    public Integer getBatchId() {
        return batchId;
    }
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getIsHistory() {
        return isHistory;
    }
    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }
    public String getHistory() {
        return history;
    }
    public void setHistory(String history) {
        this.history = history;
    }
    public Integer getIsInStoreroom() {
        return isInStoreroom;
    }
    public void setIsInStoreroom(Integer isInStoreroom) {
        this.isInStoreroom = isInStoreroom;
    }
    public String getInStoreroom() {
        return inStoreroom;
    }
    public void setInStoreroom(String inStoreroom) {
        this.inStoreroom = inStoreroom;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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

}
