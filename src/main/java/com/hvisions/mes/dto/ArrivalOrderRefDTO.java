package com.hvisions.mes.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author dpeng
 * @date 2019-11-19 15:37
 */
@Data
public class ArrivalOrderRefDTO {

    @NotNull(message = "主表ID不能为空")
    private Integer parentId;

    @NotNull(message = "子表ID不能为空")
    private Integer childId;

    @NotBlank(message = "物料二维码不能为空")
    private String qrCode;
}
