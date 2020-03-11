package com.hvisions.mes.domain;


import lombok.Data;

import java.util.Date;
/**
*
* 实体类
* @author mtyu
* @since 2019-03-05
*/

@Data
public class SalesCompany {

    /**
     *公司ID
     */
    private Integer comId;

    /**
     *创建时间
     */
    private Date createTime;

    /**
     *创建人
     */
    private Integer userId;

    /**
     *修改时间
     */
    private Date updateTime;

    /**
     *修改人
     */
    private Integer updateUserId;

    /**
     *公司名称
     */
    private String comName;

    /**
     *公司地址
     */
    private String address;

    /**
     *负责人
     */
    private String principal;

    /**
     *负责人电话
     */
    private String telephone;

    /**
     *备用电话
     */
    private String telephoneBackup;

    /**
     *是否可用
     */
    private Integer available;

    /**
     *备注
     */
    private String remark;

    /**
     *  地级市
     */
    private String city;
}
