package com.hvisions.mes.domain;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "备料实体类")
public class CallOff {

    @ApiModelProperty(hidden = true)
    private Integer callOffId;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "工单号",required = true,example = "GD20190626")
    private String workNumber;

    @ApiModelProperty(value = "用户ID",required = true,example = "1")
    private Long userId;

    /**
     *  新增 原料ID
     */
    @ApiModelProperty(hidden = true)
    private Integer materialId;

    /**
     *  新增数量
     */
    @ApiModelProperty(hidden = true)
    private Integer amount;

    /**
     *  新增 库房ID
     */
    @ApiModelProperty(value = "库房ID",required = true,example = "1")
    private Integer storeroomId;

    /**
     *  状态 0未出库 1出库  2到达
     */
    @ApiModelProperty(hidden = true)
    private Integer state;

    @ApiModelProperty(hidden = true)
    private Long lineId;

    @ApiModelProperty(value = "班组ID",required = true,example = "1")
    private Long teamId;

    @ApiModelProperty(hidden = true)
    private String materialName;

    @ApiModelProperty(hidden = true)
    private String materialSignCode;

    @ApiModelProperty(hidden = true)
    private String specs;

    @ApiModelProperty(hidden = true)
    private Integer allocateId;

}