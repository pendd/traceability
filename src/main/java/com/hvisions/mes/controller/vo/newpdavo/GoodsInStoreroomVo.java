package com.hvisions.mes.controller.vo.newpdavo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dpeng
 * @description 成品入库VO类
 * @date 2019-08-12 16:25
 */
@ApiModel
@Setter
@NoArgsConstructor
public class GoodsInStoreroomVo {

    private String cMoCode;

    @ApiModelProperty(value = "用户ID",required = true,example = "1")
    private Integer userId;

    @ApiModelProperty(value = "班组ID",required = true,example = "1")
    private Integer teamId;

    @ApiModelProperty(value = "库房ID",required = true,example = "1")
    private Integer storeroomId;

    @ApiModelProperty(value = "生产订单号",required = true,example = "DD20190626")
    @JsonProperty("cMoCode")
    public String getCMoCode() {
        return cMoCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getStoreroomId() {
        return storeroomId;
    }
}
