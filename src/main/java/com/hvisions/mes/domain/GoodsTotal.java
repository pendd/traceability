package com.hvisions.mes.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author dpeng
 * @create 2019-07-05 10:10
 */
@Data
public class GoodsTotal {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 0 产品   1 贴片机
     */
    private Integer typeStatistics;

    /**
     * 产品编码
     */
    private String goodsCode;

    /**
     * 产量
     */
    private BigDecimal quantity;

    /**
     * 日期
     */
    private Date produceDate;

    /**
     *  生产工单号
     */
    private String produceNumber;
}


