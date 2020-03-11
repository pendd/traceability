package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

@Data
public class Tooling {
    /**
     *
     */
    private Integer toolingId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Integer updateUserId;

    /**
     * 工装名称
     */
    private String toolingName;

    /**
     * 用途
     */
    private String toolingUse;

    /**
     * 规格
     */
    private String toolingSpec;

    /**
     *  是否删除 0：正常 1：删除
     */
    private Integer delFlag;

    private Integer taskUser;

    private Integer principle;

    private String taskUserName;

    private String principleName;
}

