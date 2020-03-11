package com.hvisions.mes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MatrixRepair {
    /**
     *
     */
    private Integer repairId;

    /**
     *
     */
    private Integer matrixId;

    /**
     *
     */
    private String repairContent;

    /**
     *
     */
    private Integer repairInterval;

    /**
     *
     */
    private Integer unit;

    /**
     * 是否延期  0未延期  1延期
     */
    private Integer isDelay;

    private String matrixName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
}

