package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SalesRecordDetail {
    private Integer detailId;

    private Integer salesRecordId;

    private String goodsCode;

    private String unitName;

    private Date createTime;

    private String comName;

    private Integer userId;


}