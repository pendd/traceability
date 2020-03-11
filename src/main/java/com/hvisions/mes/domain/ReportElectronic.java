package com.hvisions.mes.domain;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author dpeng
 * @description  电子车间员工考核实体类
 * @date 2019-07-31 14:03
 */
@Data
public class ReportElectronic {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer empId;

    /**
     * 5S考核
     */
    private BigDecimal s5;

    /**
     * 工作纪律
     */
    private BigDecimal discipline;

    /**
     * 产品品质
     */
    private BigDecimal quality;

    /**
     * 工作效率
     */
    private BigDecimal efficiency;

    /**
     * 其他
     */
    private BigDecimal other;

    /**
     * 管理能力
     */
    private BigDecimal manage;

    /**
     * 基准分
     */
    private BigDecimal baseScore;

    /**
     * 得分
     */
    private BigDecimal score;

    /**
     * 备注
     */
    private String remark;

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

