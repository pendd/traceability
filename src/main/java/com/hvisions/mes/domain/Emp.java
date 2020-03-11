
package com.hvisions.mes.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Emp {
    /**
     * 员工ID
     */
    @JsonProperty(value = "userId")
    private Integer empId;

    /**
     * 员工姓名
     */
    private String empName;
    /**
     * 班组ID
     */
    private Integer teamId;
    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 电话
     */
    private String tel;

    /**
     * Email
     */
    private String email;

    /**
     * 备注
     */
    private String memo;

    /**
     * 删除标志
     */
    private Integer delFlag;
    /**
     * 是否需要密码
     */
    private Integer needPw;

    /**
     *  产线ID
     */
    private Integer lineId;

    /**
     *  工序ID
     */
    private Integer orderId;

    /**
     *  是否在线 0 不在线 1 在线
     */
    private Integer isOnline;

    /* 下面为冗余字段*/
    private Integer roleId;//角色ID
    private String roleName;//角色名称
    private Integer count;
    private String csex;
    private String operate;
    private String teamName;//班组名称
    private String lineName;
    private String orderName;

    private Date startTime;

    private Date endTime;

    private List<OrderDetail> orders;

    /**
     *  是erp数据还是mes数据  0 mes  1 erp
     */
    private Integer mesOrErpEmp;

}
