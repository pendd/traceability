package com.hvisions.mes.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class EquipmentRepair {
    /**
     *
     */
    private Integer repairId;

    /**
     *
     */
    private Integer equipmentId;

    /**
     * 维保内容
     */
    private String repairContent;

    /**
     * 维保时间间隔
     */
    private Integer repairInterval;

    /**
     * 时间单位  2：天 3：月 4：年
     */
    private Integer unit;

    /**
     * 维保开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    private String equipmentName;

    private String taskUserEmail;

    private String taskUserName;

    private Integer taskUser;
}

