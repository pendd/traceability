package com.hvisions.mes.domain;

import java.sql.Time;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class StopPlanAuto {
    /*
     * 流水号
     */
    private Integer serialId;

    /*
     * 停机原因
     */
    private String stopReason;

    /*
     * 有效区间-开始日期(时间部分要设置为00:00:00)
     */
    private Timestamp activeBeginDate;

    /*
     * 有效区间-结束日期(时间部分要设置为00:00:00)
     */
    private Timestamp activeEndDate;

    /*
     * 有效星期(多个时用|分隔，周一是1，周日是7)
     */
    private String activeWeeks;

    /*
     * 停机开始时间
     */
    private Time stopBeginTime;

    /*
     * 停机结束时间(为NULL时，代表当天结束时刻)
     */
    private Time stopEndTime;

}
