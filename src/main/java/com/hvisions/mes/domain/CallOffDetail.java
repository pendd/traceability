package com.hvisions.mes.domain;


import com.hvisions.mes.controller.vo.CallOffVo;
import lombok.Data;

import java.util.List;

@Data
public class CallOffDetail {

    /**主键*/
    private Integer detailId;
    /**主表ID*/
    private Integer callOffId;

    private Integer status;

    /**
     *  新增 原料新编号
     */
    private String materialCode;



    private Integer amount;

    private String materialName;

    private Integer materialId;

    private Integer id;

    private Integer isFirst;

    private List<CallOffVo> materialCodeList;


}