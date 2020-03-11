package com.hvisions.mes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ToolingRepair {
    /**
     *
     */
    private Integer repairId;

    /**
     *
     */
    private Integer toolingId;

    /**
     * 维保内容
     */
    private String repairContent;

    /**
     * 维保时间间隔
     */
    private Integer repairInterval;

    /**
     * 时间单位  1：小时 2：天 3：月 4：年
     */
    private Integer unit;

    /**
     * 是否延期 0：未延期  1：延期
     */
    private Integer isDelay;


    private String toolingName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    private String taskUserName;

    private String principleName;

    private String taskUserEmail;

    private String principleEmail;

    private Integer taskUser;

    private Integer principle;


}

