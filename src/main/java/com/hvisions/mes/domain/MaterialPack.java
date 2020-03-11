package com.hvisions.mes.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 原料包装
 *
 * @author dpeng
 * @create 2019-06-05 10:27
 */
@Data
@ApiModel
public class MaterialPack {

    @ApiModelProperty(hidden = true)
    private Integer packId;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "用户ID",name = "userId",required = true,example = "1")
    private Integer userId;

    @ApiModelProperty(value = "第一层码",name = "firstCode",required = true,example = "1%1001")
    private String firstCode;

    @ApiModelProperty(value = "上级码",name = "secondCode",required = true,example = "1%100000001")
    private String secondCode;

    @ApiModelProperty(hidden = true)
    private Integer packTypeId;


    //////////////////  冗余字段  ///////////////
    @ApiModelProperty(hidden = true)
    private Integer supplierId;

    @ApiModelProperty(hidden = true)
    private Integer materialId;

    @ApiModelProperty(hidden = true)
    private String supplierNumber;

    @ApiModelProperty(hidden = true)
    private Integer batchId;

    @ApiModelProperty(hidden = true)
    private Integer storeroomId;

    @ApiModelProperty(hidden = true)
    private String unit;

    @ApiModelProperty(hidden = true)
    private Integer teamId;

    @ApiModelProperty(hidden = true)
    private String materialSignCode;

    @ApiModelProperty(hidden = true)
    private String specs;

}
