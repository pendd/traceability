package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;
//成品入库
@Data
public class ProductList {
    private Long inId;
    private Date createTime;
    private Long userId;
    private String goodsCode;
    private Long teamId;
    private Long storeroomId;
    private Long qualified;

    private String storeroomName;
    private String goodsName;

}
