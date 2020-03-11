package com.hvisions.mes.domain;
import	java.util.Date;

import lombok.Data;

/**
 * 原料包装关联
 *
 * @author dpeng
 * @create 2019-06-05 10:30
 */
@Data
public class MaterialPackRef {

    private Integer id;

    private String materialCode;

    private String firstCode;

    private Integer userId;

    private Date createTime;
}
