package com.hvisions.mes.domain;


import lombok.Data;

import java.util.Date;

@Data
public class MoldOut {
    private Integer outId;

    private Long userId;

    private Date createTime;

    private Long applyId;

    private Long moldId;

    private Long storeroomId;

    private Integer amount;

    private Integer isArrive;

    private String lineName;

    private String equipmentName;

    private String moldName;




}