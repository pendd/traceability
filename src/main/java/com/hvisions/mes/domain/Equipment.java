package com.hvisions.mes.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Equipment {
    private Integer equipmentId;
    private String equipmentName;
    private Integer lineId;
    private String lineName;
    private Date createTime;
    private Integer available;
    // 负责人
    private Integer principal;
    // 负责人账户
    private String principalName;
    // 供应商
    private Integer supplierId;
    // 供应商名
    private String supplierName;
    // 创建人
    private Integer userId;
    // 更新时间
    private Date updateTime;
    // 更新人
    private Integer updateUserId;
    private String account;
    /**
     * 设备编号
     */
    private String eqptSn;

    /**
     * 设备类型
     */
    private String eqptType;

    /**
     * 设备型号
     */

    private String eqptModel;

    /**
     * 使用部门
     */

    private String deptName;

    /**
     * 生产商
     */
    private String manufacturer;

    /**
     * 出厂日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    /**
     * 保修期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date serviceLimit;

    /**
     * 重量
     */
    private String eqptWeight;

    /**
     * 计量单位
     */
    private String quantityUnit;
    /**
     * 是否停机 0停机 1运行
     */
    private Integer isStop;

    /**
     *  理论产量/小时
     */
    private Integer theoreticalYield;

}
