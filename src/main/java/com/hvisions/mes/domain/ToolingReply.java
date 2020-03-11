package com.hvisions.mes.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ToolingReply {
    /**
     *
     */
    private Integer replyId;

    /**
     * 工装ID
     */
    private Integer toolingId;

    /**
     * 工装名
     */
    private String toolingName;

    /**
     * 维保时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    /**
     * 维保状态 0未维保  1已维保
     */
    private Integer repairState;

    /**
     * 维保明细
     */
    private String repairDetail;

    private Integer taskUser;

    private Integer principle;

    /**
     * 维保人
     */
    private String taskUserName;

    /**
     *  负责人
     */
    private String principleName;

    private Date replyTime;
}

