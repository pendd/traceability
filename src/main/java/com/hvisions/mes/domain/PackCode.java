package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;
/**
 * @author dpeng
 * @description 包装码表
 * @date 2019-07-21 12:21
 */
@Data
public class PackCode {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 包装类型ID
     */
    private Integer packTypeId;

    /**
     * 包装码
     */
    private String packCode;

    /**
     * 打码人
     */
    private Integer userId;

    /**
     * 打码时间
     */
    private Date createTime;

    /**
     *  父亲码
     */
    private String fatherPackCode;
}

