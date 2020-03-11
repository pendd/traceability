package com.hvisions.mes.domain;

import lombok.Data;

@Data
public class SaleCodeRef {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 销售发货单主表ID
     */
    private Integer parentId;

    /**
     * 销售发货单子表ID
     */
    private Integer childId;

    /**
     * 成品码
     */
    private String qrCode;

    private Integer amount;

    private Integer storeroomId;

    private String materialSignCode;
}

