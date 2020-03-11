package com.hvisions.mes.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dpeng
 * @description 到货单主子表记录二维码
 * @date 2019-08-07 10:26
 */
@Data
@ApiModel(value = "到货单关系对象" ,description = "到货单关系实体")
public class ArrivalOrderRef {
    /**
     * 到货单主表ID
     */
    @ApiModelProperty(value = "主表ID",name = "parentId",required = true,example = "1")
    private Integer parentId;

    /**
     * 到货单子表ID
     */
    @ApiModelProperty(value = "子表ID",name = "childId",required = true,example = "1")
    private Integer childId;

    /**
     * 原料二维码
     */
    @ApiModelProperty(value = "二维码",name = "qrCode",required = true,example = "1%1565060520366")
    private String qrCode;

    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     *  数量
     */
    private Integer amount;

    private Integer storeroomId;
}

