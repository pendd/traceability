package com.hvisions.mes.domain;



import lombok.Data;

import java.util.Date;

@Data
public class GoodsInStoreroom {
    private Integer inId;

    private Date createTime;

    private Long userId;

    private String goodsCode;

    private Long teamId;

    private Long storeroomId;

    private Integer qualified;


    //////////////// 冗余字段 /////////////////////
    // 包装码
    private String secondCode;

    private String produceNumber;

    private String userName;

    private String storeroomName;
}