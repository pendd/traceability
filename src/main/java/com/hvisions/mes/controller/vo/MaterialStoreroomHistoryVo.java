package com.hvisions.mes.controller.vo;

import com.hvisions.mes.domain.MaterialStoreroomHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 原料出入库历史vo类
 *      替代vo文件夹下的MaterialInHistory 和 MaterialOutHistory
 *
 * @author dpeng
 * @create 2019-03-15 16:37
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MaterialStoreroomHistoryVo extends MaterialStoreroomHistory {

    /**
     *  合格  不合格
     */
    private String qualifiedName;

    /**
     *  原始入库  非原始入库
     */
    private String isHistoryName;

    /**
     *  入库  出库
     */
    private String inOutTypeName;

    /**
     *  未入库  已入库
     */
    private String isRealInName;

    /**
     *  未到达  已到达
     */
    private String isArriveName;

    /**
     *  原料名
     */
    private String materialName;

    /**
     *  供应商名
     */
    private String supplierName;

    /**
     *  供应商负责人
     */
    private String principal;

    /**
     *  供应商地址
     */
    private String address;

    /**
     *  班组名
     */
    private String teamName;

    /**
     *  操作员
     */
    private String empName;

    /**
     *  供应商原批次信息
     */
    private String supplierNumber;
}
