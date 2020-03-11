package com.hvisions.mes.domain;

import java.sql.Timestamp;
/**
*
* 工序实体类
* @author mtyu
* @since 2019-03-07
*/
public class Technics {


    /**
     *工序ID
     */
   private Integer  orderId;

   /**
    *创建时间
    */
   private Timestamp createTime ;

   /**
    *创建人
    */
   private Integer userId;

   /**
    *更新时间
    */
   private Timestamp updateTime;

   /**
    *更新时间
    */
   private Integer updateUserId;

   /**
    *顺序
    */
   private Integer orderNum;

   /**
    *工序内容
    */
   private String processContent;

   /**
    *上级ID
    */
   private Integer parentId;

   /**
    *上级工序内容
    */
   private String  parentPC;

   /**
    *设备ID
    */
   private Integer equpmentId;

   /**
    *设备名称
    */
   private String equpmentName;


   /**
    *是否质检(0否1是)
    */
   private Integer isCheck;

   /**
    *是否可用
    */
   private Integer available;


   /**
    *备注
    */
   private String  remark;



public String getEqupmentName() {
    return equpmentName;
}
public void setEqupmentName(String equpmentName) {
    this.equpmentName = equpmentName;
}
public Integer getOrderId() {
    return orderId;
}
public void setOrderId(Integer orderId) {
    this.orderId = orderId;
}
public Timestamp getCreateTime() {
    return createTime;
}
public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
}
public Integer getUserId() {
    return userId;
}
public void setUserId(Integer userId) {
    this.userId = userId;
}
public Timestamp getUpdateTime() {
    return updateTime;
}
public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
}
public Integer getUpdateUserId() {
    return updateUserId;
}
public void setUpdateUserId(Integer updateUserId) {
    this.updateUserId = updateUserId;
}
public Integer getOrderNum() {
    return orderNum;
}
public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
}
public String getProcessContent() {
    return processContent;
}
public void setProcessContent(String processContent) {
    this.processContent = processContent;
}
public Integer getParentId() {
    return parentId;
}
public void setParentId(Integer parentId) {
    this.parentId = parentId;
}
public String getParentPC() {
    return parentPC;
}
public void setParentPC(String parentPC) {
    this.parentPC = parentPC;
}
public Integer getEqupmentId() {
    return equpmentId;
}
public void setEqupmentId(Integer equpmentId) {
    this.equpmentId = equpmentId;
}
public Integer getIsCheck() {
    return isCheck;
}
public void setIsCheck(Integer isCheck) {
    this.isCheck = isCheck;
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
