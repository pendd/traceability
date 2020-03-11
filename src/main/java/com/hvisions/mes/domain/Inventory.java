package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;
/**
 * @author dpeng
 * @description 物料档案实体类
 * @date 2019-08-05 10:11
 */
@Data
public class Inventory {
    /**
     *
     */
    private Integer autoid;

    /**
     *
     */
    private String caccId;

    /**
     * 物料编码
     */
    private String cinvcode;

    /**
     * 物料代码
     */
    private String ciinvaddcode;

    /**
     * 物料名称
     */
    private String ciinvname;

    /**
     * 规格型号
     */
    private String cinvstd;

    /**
     * 存货分类编码
     */
    private String cinvccode;

    /**
     * 存活分类名称
     */
    private String cinvcname;

    /**
     * 主计量单位编码
     */
    private String ccomunitcode;

    /**
     * 主计量单位名称
     */
    private String ccomunitname;

    /**
     * 物料停用日期
     */
    private Date dedate;

    /**
     * 是否批次管理
     */
    private Boolean binvbatch;

    /**
     * 是否保质期管理
     */
    private Boolean binvquality;

    /**
     * 是否采购属性
     */
    private Boolean bpurchase;

    /**
     * 是否自制属性
     */
    private Boolean bself;

    /**
     * 是否委外属性
     */
    private Boolean bproxyforeign;

    /**
     * 是否销售属性
     */
    private Boolean bsale;

    /**
     * 是否有自由项 1(颜色管理)
     */
    private Boolean bfree1;

    /**
     * 记录创建日期
     */
    private Date dcreatedate;

    /**
     * 记录修改次数
     */
    private Short imodifycounts;

    /**
     * 记录最后修改日期
     */
    private Date dlastmodifydate;

    /**
     * 时间戳
     */
    private Date msts;
}

