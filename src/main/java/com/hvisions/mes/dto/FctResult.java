package com.hvisions.mes.dto;

import lombok.Data;

/**
 * @author dpeng
 * @description FCT 测试
 * @date 2019-09-24 17:03
 */
@Data
public class FctResult {

    /**
     *  测试数量
     */
    private Integer rowNum;

    /**
     *  合格数
     */
    private Integer qualifiedSum;
}
