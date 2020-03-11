package com.hvisions.mes.domain;

import lombok.Data;

/**
 * 员工产线工序班组
 *
 * @author dpeng
 * @create 2019-06-27 10:07
 */
@Data
public class EmpLineOrderTeam {

    /**
     *  主键
     */
    private Integer id;

    /**
     *  员工ID
     */
    private Integer empId;

    /**
     *  产线ID
     */
    private Integer lineId;

    /**
     *  工序ID
     */
    private Integer orderId;

    /**
     *  班组ID
     */
    private Integer teamId;

    ////////////////////// 冗余字段 ///////////////////
    /**
     * 员工名称
     */
    private String empName;

    /**
     *  产线名称
     */
    private String lineName;

    /**
     *  工序名称
     */
    private String orderName;

    /**
     *  班组名称
     */
    private String teamName;

    private Integer goodsId;

    private String goodsName;
}
