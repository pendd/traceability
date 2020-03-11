package com.hvisions.mes.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *
 * 原料批次实体类
 * @author mtyu
 * @since 2019-03-05
 */
@ApiModel(value = "原料批次对象")
@Data
public class MaterialBatch {


    /**
     *批次ID
     */
    @ApiModelProperty(hidden = true)
    private Integer batchId;
    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime = new Date();
    /**
     * 原批次号
     */
    @ApiModelProperty(hidden = true)
    private String supplierNumber;

    /**
     * 库房ID
     */
    @ApiModelProperty(value = "库房ID",required = true,example = "1")
    private Integer storeroomId;

    /**
     * 库房
     */
    @ApiModelProperty(hidden = true)
    private String storeroomName;

    /**
     * 供应商ID
     */
    @ApiModelProperty(hidden = true)
    private Integer supplierId;

    /**
     * 供应商
     */
    @ApiModelProperty(hidden = true)
    private String supplierName;

    /**
     * 原料ID
     */
    @ApiModelProperty(hidden = true)
    private Integer materialId;


    /**
     * 原料
     */
    @ApiModelProperty(hidden = true)
    private String materialName;

    /**
     * 班组ID
     */
    @ApiModelProperty(value = "班组ID",required = true,example = "1")
    private Integer teamId;

    /**
     * 班组
     */
    @ApiModelProperty(hidden = true)
    private String teamName;

    /**
     * 库管员ID
     */
    @ApiModelProperty(value = "库管员ID",required = true,example = "1")
    private Integer userId;

    /**
     * 库管员
     */
    @ApiModelProperty(hidden = true)
    private String userName;

    /**
     * 数量
     */
    @ApiModelProperty(hidden = true)
    private Integer amount;

    /**
     * 单位
     */
    @ApiModelProperty(hidden = true)
    private String unit;

    /**
     * 是否免检(0否1是)      已经废弃
     */
    @ApiModelProperty(hidden = true)
    private Integer isCheck;

    /**
     *  主表ID
     */
    @ApiModelProperty(value = "主表ID",required = true,example = "1")
    private Integer parentId;

    /**
     *  子表ID
     */
    @ApiModelProperty(hidden = true)
    private Integer childId;

    /**
     *  原料二维码
     */
    @ApiModelProperty(hidden = true)
    private String materialCode;

    /**
     *  原料编码
     */
    @ApiModelProperty(hidden = true)
    private String materialSignCode;

    /**
     *  库房编码
     */
    @ApiModelProperty(hidden = true)
    private String storeroomCode;

    /**
     *  规格 即一袋多少个
     */
    @ApiModelProperty(hidden = true)
    private String specs;

}
