package com.hvisions.mes.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GoodsStoreroomRef {
    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 生产订单号
     */
    @ApiModelProperty(value = "生产订单号",example = "DD20190626")
    @JsonProperty(value = "cMoCode")
    private String cMoCode;

    /**
     * 原料编码
     */
    @ApiModelProperty(value = "原料编码",example = "1")
    private String materialSignCode;

    /**
     * 成品码
     */
    @ApiModelProperty(value = "成品码",example = "cp100")
    private String qrGoodsCode;

    private Integer amount;
}

