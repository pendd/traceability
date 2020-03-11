package com.hvisions.mes.domain;

import lombok.Data;

@Data
public class Matrix {
    /**
     *
     */
    private Integer matrixId;

    /**
     *
     */
    private String matrixName;

    /**
     *
     */
    private String matrixUse;

    /**
     *
     */
    private String matrixSpec;

    /**
     * 是否删除  0正常 1删除
     */
    private Integer delFlag;

    private Integer taskUser;

    private Integer principle;

    private String taskUserName;

    private String principleName;
}

