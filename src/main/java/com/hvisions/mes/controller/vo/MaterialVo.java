package com.hvisions.mes.controller.vo;

import lombok.Data;

/**
 * 原料库存vo类
 *
 * @author dpeng
 * @create 2019-03-28 15:43
 */
@Data
public class MaterialVo implements Comparable<MaterialVo>{

    /**
     *  原料名称
     */
    private String materialName;

    /**
     *  原料出库数量
     */
    private Integer outNum;

    /**
     *  原料入库数量
     */
    private Integer inNum;

    /**
     *  原料剩余数量
     */
    private Integer overNum;

    /**
     *  单位
     */
    private String unit;

    /**
     *  自定义排序规则  按剩余量多少进行排序    升序
     * @param o
     * @return
     */
    @Override
    public int compareTo(MaterialVo o) {
        return o.getOverNum() - this.overNum;
    }
}
