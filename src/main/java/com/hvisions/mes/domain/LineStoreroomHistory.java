package com.hvisions.mes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 线边库出入库历史实体类
 *      替代 LineStoreroomInHistory 和  LineStoreroomOutHistory
 *
 * @author dpeng
 * @create 2019-03-14 13:11
 */
@Data
public class LineStoreroomHistory {

    private Integer historyId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Long userId;

    private Long lineStoreroomId;

    private Long materialHistoryId;

    private String materialCode;

    private String unit;

    private Integer amount;

    private Integer inOutType;

    private Long goType;

    private Long storeroomId;

    private Integer isArrive;

    /** 去向产线ID */
    private Integer goLineId;
}
