package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

@Data
public class EquipmentAoi {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer equipmentId;

    /**
     *
     */
    private String testModel;

    /**
     *
     */
    private Date completionTime;

    /**
     *
     */
    private Date startRetrialTime;

    /**
     *
     */
    private Date endRetrialTime;

    /**
     *
     */
    private Integer pointsTotal;

    /**
     *
     */
    private Integer wrongPointsTotal;

    /**
     *
     */
    private String workstation;

    /**
     *
     */
    private String operator;

    /**
     *
     */
    private String pcb;

    /**
     *
     */
    private String workNumber;
}

