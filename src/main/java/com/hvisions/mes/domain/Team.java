package com.hvisions.mes.domain;

import lombok.Data;
import java.util.Date;

@Data
public class Team {
    private Integer teamId;

    private Date createTime;

    private Integer userId;

    private Date updateTime;

    private Integer updateUserId;

    private String teamName;

    private Integer available;

    /** 顺序 */
    private Integer orderNum;

    /** 工作小时 */
    private Integer workHour;
}