package com.hvisions.mes.domain;

import lombok.Data;

@Data
public class AdjustPlan {

    /**
     * 计划ID
     */
    private Integer planId;

    /**
     * 排查点
     */
    private String planPart;

    /**
     * 排查内容
     */
    private String planContent;

    /**
     * 执行周期-固定间隔
     */
    private Integer cycleDuration;

    /**
     * 执行周期-固定间隔单位(3:小时 5:天 7:月 9:年)
     */
    private Integer cycleDurationUnit;

    // 是否延期
    private Integer isDelay;
}
