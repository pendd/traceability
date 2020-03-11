package com.hvisions.mes.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author dpeng
 * @date 2019-11-20 13:13
 */
@Data
public class AssignMaterialQrRefDTO {

    @NotNull private Integer callOffId;

    @NotBlank private String qrCode;
}
