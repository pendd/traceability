package com.hvisions.mes.domain;

import lombok.Data;

/**
 * 成品包装关联表
 *
 * @author dpeng
 * @create 2019-05-14 13:42
 */
@Data
public class GoodsPackRef {

    /** 成品编码 */
    private String goodsCode;

    /** 包装编码 */
    private String firstCode;
}
