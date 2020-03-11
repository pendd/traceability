package com.hvisions.mes.controller.vo.newpdavo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dpeng
 * @description 库房配料任务扫二维码返回结果与页面请求实体
 * @date 2019-08-09 13:37
 */
@Data
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignMaterialScanQrCodeVo {

    @ApiModelProperty(value = "原料二维码",required = true, example = "1%1565228832647")
    private String materialCode;

    @ApiModelProperty(value = "原料ID",required = true,example = "1")
    private Integer materialId;

    @ApiModelProperty(hidden = true)
    private Integer type;

    @ApiModelProperty(hidden = true)
    private Integer amount;

    @ApiModelProperty(hidden = true)
    private Integer status;

    private Integer storeroomId;
}
