package com.hvisions.mes.dto;

import lombok.Data;

/**
 * @author dpeng
 * @description SPI检测返回数据
 * @date 2019-09-24 14:19
 */
@Data
public class TpjResult {

    /**
     *  吸料数量和
     */
    private Integer pickUpSum;

    /**
     *  贴装数量和
     */
    private Integer placeSum;

    /**
     *  吸料错误数量和
     */
    private Integer pickErrorSum;

    /**
     *  检测出错数量和
     */
    private Integer visionErrorSum;

    /**
     *  抛料数量和
     */
    private Integer dumpSum;
}
