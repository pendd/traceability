package com.hvisions.mes.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author dpeng
 * @date 2019-11-20 15:44
 */
@Data
public class OrderHistoryDTO {

    @NotBlank private String produceNumber;

    @NotBlank private String productCode;

    @NotNull private Long orderId;
}
