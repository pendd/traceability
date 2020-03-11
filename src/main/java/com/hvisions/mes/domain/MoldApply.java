package com.hvisions.mes.domain;



import lombok.Data;

import java.util.Date;

@Data
public class MoldApply {
    private Integer applyId;

    private Integer teamId;

    private Integer lineId;

    private Integer equipmentId;

    private Integer moldId;

    private Integer amount;

    private Integer userId;

    private Date createTime;

    private Integer isOut;

    private String teamName;

    private String lineName;

    private String equipmentName;

    private String moldName;

}