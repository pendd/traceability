package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

@Data
public class SaleOutStoreroom {
    /**
     *
     */
    private Integer outId;

    /**
     *
     */
    private Date createTime;

    /**
     * 成品码
     */
    private String goodsCode;

    /**
     * 班组ID
     */
    private Integer teamId;

    /**
     * 库房ID
     */
    private Integer storeroomId;

    /**
     * 操作人ID
     */
    private Integer userId;
}

