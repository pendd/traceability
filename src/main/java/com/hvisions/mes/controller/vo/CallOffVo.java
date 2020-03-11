package com.hvisions.mes.controller.vo;

import lombok.Data;

/**
 * @author dpeng
 * @create 2019-07-03 13:24
 */
@Data
public class CallOffVo {
    /**
     *  原料二维码
     */
    private String materialCode;

    /**
     *  数量
     */
    private Integer count;
}
