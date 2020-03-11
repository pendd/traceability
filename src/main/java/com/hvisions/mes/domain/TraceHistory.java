package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

/**
 * Author swang
 * Date 2019/3/23 14:01
 * Version 1.0
 * Description 追溯系统所有记录
 **/
@Data
public class TraceHistory {
    /**
     * 主键
     */
    private Integer historyId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人员ID
     */
    private Long userId;
    /**
     * 追溯编码
     */
    private String traceCode;
    /**
     * 追溯名称
     */
    private String traceName;
    /**
     * 追溯类型
     */
    private Integer traceType;
    /**
     * 下一级追溯编码
     */
    private String nextTraceCode;
    /**
     * 下一级追溯名称
     */
    private String nextTraceName;
}
