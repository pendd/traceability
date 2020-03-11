package com.hvisions.mes.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Schedule {
    private Integer scheduleId;

    private Date createTime;

    private Integer userId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer teamId;

    private String teamName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Integer available;

}