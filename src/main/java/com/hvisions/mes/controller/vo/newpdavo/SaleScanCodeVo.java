package com.hvisions.mes.controller.vo.newpdavo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dpeng
 * @description 销售出库扫码vo
 * @date 2019-08-14 9:35
 */
@Data
@ApiModel
public class SaleScanCodeVo {

    @ApiModelProperty(hidden = true)
    private int isMatch;

    @ApiModelProperty(hidden = true)
    private int amount;

    @ApiModelProperty(hidden = true)
    private Integer storeroomId;

    @ApiModelProperty(value = "主表ID",required = true,example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "子表ID",required = true,example = "1")
    private Integer childId;

    @ApiModelProperty(value = "成品二维码",required = true,example = "1%1565229366906")
    private String qrCode;
}
