package com.hvisions.mes.controller.vo.newpdavo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dpeng
 * @description 销售出库vo类
 * @date 2019-08-27 10:43
 */
@Data
@ApiModel
public class SaleOutStoreroomVo {

    @ApiModelProperty(value = "主表ID",required = true,example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "库房ID",required = true,example = "1")
    private Integer storeroomId;

    @ApiModelProperty(value = "用户ID",required = true,example = "1")
    private Integer userId;

    private Integer teamId;
}
