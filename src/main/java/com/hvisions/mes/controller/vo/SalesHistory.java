package com.hvisions.mes.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SalesHistory {
    //对外成品码
    private String code;
    //单位
    private String unitName;
    //创建时间
    private Date createTime;
    //销售名称
    private String salesName;
    //公司名称
    private String comName;
    //地址
    private String address;
    //负责人
    private String principal;
    //电话
    private String telephone;

    /**
     *  经销商编号
     */
    private Integer companyId;

    /**
     *  经销商所在地级市名称
     */
    private String city;

    /**
     *  最近销售量
     */
    private Integer recentCount;

    /**
     *  最近交易时间
     */
    private Date recentTime;

    /**
     *  销售总量
     */
    private Integer saleCount;

    /**
     *  商品名称
     */
    private String goodsName;
}
