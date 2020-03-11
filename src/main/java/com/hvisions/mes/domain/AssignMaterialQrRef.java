package com.hvisions.mes.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author dpeng
 * @description 配料表中已扫的原料二维码
 * @date 2019-08-09 13:37
 */
@Data
@ApiModel
public class AssignMaterialQrRef {

    @ApiModelProperty(value = "主键",hidden = true)
    private Integer id;

    @ApiModelProperty(value = "对应领料表ID",required = true,example = "13")
    private Integer callOffId;

    @ApiModelProperty(value = "原料二维码",required = true,example = "2%1565229373884")
    private String qrCode;

    @ApiModelProperty(value = "出库数量",required = true,example = "100")
    private Integer outAmount;

}

