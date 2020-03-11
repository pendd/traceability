package com.hvisions.mes.dto;

import lombok.Data;

/**
 * @author dpeng
 * @description SPI 检测
 * @date 2019-09-24 17:01
 */
@Data
public class SpiResult {

    /**
     *  程式名称
     */
    private String modelName;

    /**
     *  测试总数
     */
    private Integer sumAmount;

    /**
     *  通过数量
     */
    private Integer passAmount;
}
