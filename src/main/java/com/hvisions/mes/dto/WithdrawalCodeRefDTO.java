package com.hvisions.mes.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author dpeng
 * @date 2019-11-20 13:31
 */
@Data
public class WithdrawalCodeRefDTO {

    @NotNull private Integer withdrawalId;

    @NotBlank private String qrCode;
}
