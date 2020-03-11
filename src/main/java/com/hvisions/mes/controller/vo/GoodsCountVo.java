package com.hvisions.mes.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品已成产、未生产、不合格、维修数
 *
 * @author dpeng
 * @create 2019-06-24 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCountVo {

    /**
     *  已生产数量
     */
    private Integer completeCount;

    /**
     *  未生产数量
     */
    private Integer unCompleteCount;

    /**
     *  不合格数量
     */
    private Integer unQualifiedCount;

    /**
     *  维修数量
     */
    private Integer repairCount;

    /**
     *  计划数量
     */
    private Integer planAmount;

}
