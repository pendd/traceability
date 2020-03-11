package com.hvisions.mes.dto;

import lombok.Data;

/**
 * @author dpeng
 * @description AOI检测
 * @date 2019-09-24 15:28
 */
@Data
public class AoiResult {

    /**
     *  测试数量
     */
    private Integer rowNum;

    /**
     *  测试总点数
     */
    private Integer pointsTotalSum;

    /**
     *  不良总点数
     */
    private Integer wrongPointsTotalSum;

}
