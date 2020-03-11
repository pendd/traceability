package com.hvisions.mes.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author dpeng
 * @create 2019-07-15 15:54
 */
@Data
public class ReportRetrospect {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 产品编号
     */
    private String goodsCode;

    /**
     * 产品名称
     */
    private String goodsName;

    /**
     * 产品规格
     */
    private String goodsSpec;

    /**
     * 计量单位
     */
    private String goodsUnit;

    /**
     * 产品数量
     */
    private BigDecimal goodsQuantity;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料规格
     */
    private String materialSpec;

    /**
     * 物料单位
     */
    private String materialUnit;

    /**
     * 物料数量
     */
    private BigDecimal materialQuantity;

    /**
     * 物料类型
     */
    private String materialType;

    /**
     * 设备名称
     */
    private String equipmentName;

    /**
     * 设备异常数量
     */
    private Integer equipmentErrorCount;

    /**
     * 工序
     */
    private String workingProcedure;

    /**
     * 操作员
     */
    private String operators;

    /**
     * 工时
     */
    private BigDecimal hours;

    /**
     * 入库名称
     */
    private String inStockName;

    /**
     * 日期
     */
    private Date recordDate;
}

