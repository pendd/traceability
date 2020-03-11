package com.hvisions.mes.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author boy
 * @create 2019-07-03 21:41
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Technology {
    /**
     * 主键
     */
    private Integer technologyId;

    /**
     * 工序ID
     */
    private Integer orderId;

    /**
     * 工艺名称
     */
    private String technologyName;

    /**
     * 是否可用  0否 1是
     */
    private Integer available;

    ///////////////////  冗余字段  ////////////////////
    /**
     *  工序名称
     */
    private String orderName;

    private String goodsName;

    private String goodsId;
}

