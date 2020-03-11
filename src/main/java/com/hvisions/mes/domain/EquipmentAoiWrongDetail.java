package com.hvisions.mes.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class EquipmentAoiWrongDetail {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer aoiId;

    /**
     *
     */
    private String wrongPoint;

    /**
     *
     */
    private String typeName;

    /**
     *
     */
    private BigDecimal xCoordinate;

    /**
     *
     */
    private BigDecimal yCoordinate;

    /**
     *
     */
    private BigDecimal angle;

    private String workNumber;
}

