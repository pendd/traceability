package com.hvisions.mes.controller.vo;

import lombok.Data;

/**
 * 产品库存
 *
 * @author dpeng
 * @create 2019-04-01 0:01
 */
@Data
public class GoodsSortVo implements Comparable<GoodsSortVo>{

    /**
     *  成品名称
     */
    private String goodsName;

    /**
     *  总数量
     */
    private Integer allCount;

    @Override
    public int compareTo(GoodsSortVo o) {
        return this.allCount - o.getAllCount();
    }
}
