package com.hvisions.mes.domain;


import lombok.Data;

import java.util.Date;

@Data
public class OrderHistory {
    private Integer historyId;

    private Date createTime;

    /**
     *  0不合格  1合格
     */
    private Integer qualified;

    private Long userId;

    /** 生产工单号 */
    private String produceNumber;

    private Long orderId;

    /** 成品码 即扫的二维码 */
    private String productCode;

    private Long teamId;

    /** 产品编码 */
    private String goodsCode;

    private Integer isRework;

    private String userName;

    private String goodsName;

    private String processContent;

    /** 记录类型 0生产工序记录1维修工序记录 */
    private Integer historyType;


    ////////////////////// 冗余字段 ////////////////
    private String orderName;
    private Long lineId;

    private Integer orderNum;

    /**
     *  返工次数
     */
    private Integer reworkTimes;

    private Integer orderType;

}