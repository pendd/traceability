package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;
/**
 * @author dpeng
 * @description 采购(委外)到货单主表
 * @date 2019-08-05 20:04
 */
@Data
public class PuArrivalvouch {
    /**
     *
     */
    private Integer autoid;

    /**
     *
     */
    private String caccId;

    /**
     * 单据主表ID
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String ccode;

    /**
     * 单据日期
     */
    private Date ddate;

    /**
     * 供应商编码
     */
    private String cvencode;

    /**
     * 部门编码
     */
    private String cdepcode;

    /**
     * 业务员编码
     */
    private String cpersoncode;

    /**
     * 业务类型(普通采购 委外加工等)
     */
    private String cbustype;

    /**
     * 子表行数
     */
    private Short isubrows;

    /**
     * 备注
     */
    private String cmemo;

    /**
     * 制单人
     */
    private String cmaker;

    /**
     * 审核人
     */
    private String cverifier;

    /**
     * 记录创建日期
     */
    private Date dcreatedate;

    /**
     *  供应商名称
     */
    private String cvenname;

}

