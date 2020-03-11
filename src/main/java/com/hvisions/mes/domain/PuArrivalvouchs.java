package com.hvisions.mes.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
/**
 * @author dpeng
 * @description 采购(委外)到货单子表
 * @date 2019-08-05 20:04
 */
@Data
public class PuArrivalvouchs {
    /**
     *
     */
    private Integer autoid;

    /**
     *
     */
    private String caccId;

    /**
     * 单据主表ID
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String ccode;

    /**
     * 单据子表ID
     */
    private Integer iarrsid;

    /**
     * 行号
     */
    private Integer irowno;

    /**
     * 仓库编码
     */
    private String cwhcode;

    /**
     * 物料编码
     */
    private String cinvcode;

    /**
     * 到货数量
     */
    private BigDecimal iquantity;

    /**
     * 批号
     */
    private String cbatch;

    /**
     * 保质期
     */
    private Date dvdate;

    /**
     * 生产日期
     */
    private Date dmakedate;

    /**
     * 采购订单子表ID
     */
    private Integer podid;

    /**
     * 采购订单号
     */
    private String cpocode;

    /**
     * 采购订单行号
     */
    private Integer ipodrowno;

    /**
     * 销售订单字表ID
     */
    private Integer sodid;

    /**
     * 销售订单号
     */
    private String csocode;

    /**
     * 销售订单行号
     */
    private Integer isodrowno;

    /**
     * 记录创建日期
     */
    private Date dcreatedate;

    /**
     *  是否入库
     */
    private Integer isIn;


    /**
     *  物料名称
     */
    private String ciinvname;

    /**
     *  已扫码数量
     */
    private Integer amount;

    /**
     *  物料单位
     */
    private String ccomunitname;
}

