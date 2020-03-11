package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;
/**
 * @author dpeng
 * @description 物料分类实体类
 * @date 2019-08-05 10:11
 */
@Data
public class Inventoryclass {
    /**
     * 自动编号(标识列
     * )
     */
    private Integer autoid;

    /**
     * ERP账套号
     */
    private String caccId;

    /**
     * 物料分类编码
     */
    private String cinvccode;

    /**
     * 物料分类名称
     */
    private String cinvcname;

    /**
     * 物料分类级次
     */
    private Short iinvcgrade;

    /**
     * 是否末级分类
     */
    private Boolean binvcend;

    /**
     * 记录创建日期
     */
    private Date dcreatedate;
}

