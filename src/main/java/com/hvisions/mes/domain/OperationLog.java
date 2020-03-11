package com.hvisions.mes.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

public class OperationLog {
    private static final long serialVersionUID = 1L;

    /** 日志操作类型：添加 */
    public static final int OPERATION_TYPE_ADD=0;
    public static final int OPERATION_TYPE_MODIFY=1;
    public static final int OPERATION_TYPE_REMOVE=2;
    public static final int OPERATION_TYPE_QUERY=3;
    public static final int OPERATION_TYPE_PASSWORD=4;

    /**
     * 流水号
     */
    private String serialId;
    /**
     *备注
     */
    private String memo;
    /**
     * 操作菜单ID
     */
    private String menuId;
    /**
     * 操作内容
     */
    private String operContent;
    /**
     * 操作时间
     */
    private Date operTime;
    /**
     * 操作类型
     * 0:添加、1:修改、2:删除
     */

    private Integer operType;
    /**
     * 操作人ID
     */
    private Integer operUserId;
    /**
     * 菜单名（中文）
     */
    private String menuNameZh;

    private String menuNameEn;
    /**
     * 用户名
     */
    private String empName;
    private String operContentEn;


    public String getMenuNameEn() {
        return menuNameEn;
    }
    public void setMenuNameEn(String menuNameEn) {
        this.menuNameEn = menuNameEn;
    }
    public String getOperContentEn() {
        return operContentEn;
    }
    public void setOperContentEn(String operContentEn) {
        this.operContentEn = operContentEn;
    }
    public String getSerialId() {
        return serialId;
    }
    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getMenuId() {
        return menuId;
    }
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    public String getOperContent() {
        return operContent;
    }
    public void setOperContent(String operContent) {
        this.operContent = operContent;
    }
    public Date getOperTime() {
        return operTime;
    }
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
    public Integer getOperType() {
        return operType;
    }
    public void setOperType(Integer operType) {
        this.operType = operType;
    }
    public Integer getOperUserId() {
        return operUserId;
    }
    public void setOperUserId(Integer operUserId) {
        this.operUserId = operUserId;
    }
    public String getMenuNameZh() {
        return menuNameZh;
    }
    public void setMenuNameZh(String menuNameZh) {
        this.menuNameZh = menuNameZh;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public static int getOperationTypeAdd() {
        return OPERATION_TYPE_ADD;
    }
    public static int getOperationTypeModify() {
        return OPERATION_TYPE_MODIFY;
    }
    public static int getOperationTypeRemove() {
        return OPERATION_TYPE_REMOVE;
    }
    public static int getOperationTypeQuery() {
        return OPERATION_TYPE_QUERY;
    }




}
