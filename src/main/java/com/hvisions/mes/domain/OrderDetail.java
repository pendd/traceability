package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDetail {
    private Integer orderId;
    private String equipmentName;
    private Integer equipmentId;
    private Date createTime;
    private Integer available;
    private Integer userId;
    private Date updateTime;
    private Integer updateUserId;
    private String remark;
    private Integer orderNum;
    private String processContent;
    private String parentName;
    private Integer parentId;
    private Integer isCheck;
    private String orderName;

    /** 标准操作时间 */
    private Integer operatingTime;

    /** 工序类型 0:普通工序，1:质检工序，2：返修工序，3：包装工序 */
    private Integer orderType;

    ///////////////////// 冗余字段 //////////////////////////
    /**
     *  标识状态
     */
    private Integer status;

    /**
     *  数量
     */
    private Integer amount;

    /**
     *  工单在线总人数
     */
    private Integer empSumNum;

    /**
     *  是否合格  0 不合格  1 合格
     */
    private Integer qualified;

    private Integer teamId;

}
