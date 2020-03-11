package com.hvisions.mes.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 初始化库存
 * @author dpeng
 * @date 2019-11-18 15:50
 */
@Data
@ApiModel
public class InitialDataDTO {

    @ApiModelProperty(value = "物料二维码",required = true)
    @NotBlank(message = "物料二维码不能为空")
    private String materialCode;

    @ApiModelProperty(value = "料号",required = true)
    @NotBlank(message = "料号不能为空")
    private String materialSignCode;

    @ApiModelProperty(value = "供应商编码",required = true)
    @NotBlank(message = "供应商编码不能为空")
    private String supplierCode;

    @ApiModelProperty(value = "原批次号",required = true)
    @NotBlank(message = "原批次号不能为空")
    private String supplierNumber;

    @ApiModelProperty(value = "数量",required = true)
    @NotNull(message = "数量不能为空")
    private Integer amount;

    @ApiModelProperty(value = "库房编码",required = true)
    @NotBlank(message = "库房编码不能为空")
    private String storeroomCode;


}
