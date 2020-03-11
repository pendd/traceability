package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

@Data
public class Person {
    /**
     * 自动编号(标识列)
     */
    private Integer autoId;

    /**
     * ERP账套号
     */
    private String cAccId;

    /**
     * 人员编码
     */
    private String cPersonCode;

    /**
     * 人员姓名
     */
    private String cPersonName;

    /**
     * 部门编码
     */
    private String cDepCode;

    /**
     * 记录创建日期
     */
    private Date dCreateDate;
}

