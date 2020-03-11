package com.hvisions.mes.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class StopPlanManual {

    /*
     * 流水号
     */
    private Integer serialId;

    /*
     * 停机原因
     */
    private String stopReason;

    /*
     * 停机开始时间
     */
    private Timestamp stopBeginTime;

    /*
     * 停机结束时间
     */
    private Timestamp stopEndTime;

}
