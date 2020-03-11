package com.hvisions.mes.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author dpeng
 * @date 2019-11-20 14:21
 */

@Data
public class SaleCodeRefDTO {

    @NotNull private Integer parentId;

    @NotNull private Integer childId;

    @NotBlank private String qrCode;
}
