package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

/**
 * 工序总称
 *
 * @author dpeng
 * @create 2019-05-14 13:26
 */
@Data
public class orderFullName {

    /** 主键 */
    private Long fullId;

    /** 创建时间 */
    private Date createTime;

    /** 创建人ID */
    private Long userId;

    /** 修改时间 */
    private Date updateTime;

    /** 修改人 */
    private Long updateUserId;

    /** 工序总称 */
    private String fullName;

    /** 是否可用 */
    private Integer available;
}
