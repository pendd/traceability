package com.hvisions.mes.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class EquipmentReply {
    /**
     *
     */
    private Integer replyId;

    /**
     *
     */
    private Integer equipmentId;

    /**
     * 设备名
     */
    private String equipmentName;

    /**
     * 维保时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 维保状态 0未维保 1已维保
     */
    private Integer repairState;

    /**
     * 维保明细
     */
    private String repairDetail;

    /**
     * 维保人
     */
    private Integer taskUser;

    /**
     * 反馈时间
     */
    private Date replyTime;

    private String taskUserName;
}

