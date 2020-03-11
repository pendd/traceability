package com.hvisions.mes.domain;

import java.math.BigDecimal;
import lombok.Data;
/**
 * @author dpeng
 * @description  装配车间员工考核实体类
 * @date 2019-07-31 14:03
 */
@Data
public class ReportAssembling {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer empId;

    /**
     * 工作态度
     */
    private BigDecimal attitude;

    /**
     * 工作技能
     */
    private BigDecimal skill;

    /**
     * 工作绩效
     */
    private BigDecimal achievements;

    /**
     * 协同合作
     */
    private BigDecimal cooperation;

    /**
     * 责任心
     */
    private BigDecimal responsibility;

    /**
     * 品德言行
     */
    private BigDecimal character;

    /**
     * 质量控制
     */
    private BigDecimal quality;

    /**
     * 安全生产
     */
    private BigDecimal safeProduction;

    /**
     * 管理能力
     */
    private BigDecimal manage;

    /**
     * 领导能力
     */
    private BigDecimal lead;

    /**
     * 最终结果
     */
    private BigDecimal result;

    /**
     * 得分汇总
     */
    private BigDecimal score;

    /**
     * 年
     */
    private Integer iyear;

    /**
     * 月
     */
    private Integer imonth;

    private String empName;
}

