package com.hvisions.mes.domain;

import lombok.Data;

/**
 * 工序资料表
 *
 * @author dpeng
 * @create 2019-05-14 13:35
 */
@Data
public class OrderResourceFile {

    private Integer id;

    /** 工序ID */
    private Integer orderId;

    /** 资料名 */
    private String fileName;

    /** 资料地址 */
    private String filePath;


    ////////////////////// 冗余字段 ///////////////////
    /**
     *  工序名称
     */
    private String orderName;
}
