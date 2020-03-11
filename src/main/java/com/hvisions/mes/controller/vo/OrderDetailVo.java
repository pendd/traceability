package com.hvisions.mes.controller.vo;

import lombok.Data;

/**
 * 工序vo类   记录产品工序完成数量和总数量
 *
 * @author dpeng
 * @create 2019-03-29 11:00
 */
@Data
public class OrderDetailVo implements Comparable<OrderDetailVo>{

    /**
     *  工序编号
     */
    private Integer orderId;

    /**
     *  已完成工序数量
     */
    private Integer finishedCount;

    /**
     *  工序总数量
     */
    private Integer allCount;

    /**
     *  未完成工序量
     */
    private Integer unfinishedCount;

    /**
     *  设备名
     */
    private String equipmentName;

    /**
     *  工序内容
     */
    private String processContent;

    /**
     *  自定义排序规则   降序
     * @param o
     * @return
     */
    @Override
    public int compareTo(OrderDetailVo  o) {
        return o.getUnfinishedCount() - this.unfinishedCount;
    }
}
