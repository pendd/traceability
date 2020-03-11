package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;
/**
 * @author dpeng
 * @description 原料码表
 * @date 2019-07-21 12:21
 */
@Data
public class MaterialCode {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 原料码
     */
    private String materialCode;

    /**
     * 原料ID
     */
    private Integer materialId;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 原批次号
     */
    private String supplierNumber;

    /**
     * 单位
     */
    private String unit;

    /**
     * 打码人
     */
    private Integer userId;

    /**
     * 打码时间
     */
    private Date createTime;


    /**
     *  包装码
     */
    private String packCode;

    /**
     *  批次ID
     */
    private Integer batchId;

    /**
     *  班组ID
     */
    private Integer teamId;

    private String materialSignCode;

    private String specs;

    private Integer storeroomId;
}

