package com.hvisions.mes.domain;

import lombok.Data;

/**
 * 库存
 *
 * @author dpeng
 * @create 2019-05-14 13:36
 */
@Data
public class Stock {

    private Integer id;

    /** 成品编码或原料标识码 */
    private String code;

    /** 库房编码 */
    private String storeroomCode;

    /** 实际数量 */
    private Integer actualcount;

}
