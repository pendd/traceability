package com.hvisions.mes.dto;

import lombok.Data;

/**
 * @author dpeng
 * @description ICT 测试
 * @date 2019-09-24 15:46
 */
@Data
public class IctResult {

    /**
     *  测试数量
     */
    private Integer rowNum;

    /**
     *  测试板数
     */
    private Integer sumAmount;

    /**
     *  合格数
     */
    private Integer qualifiedAmount;
}
