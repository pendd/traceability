package com.hvisions.mes.controller.vo;

import lombok.Data;

/**
 * 产线计划效率
 *
 * @author dpeng
 * @create 2019-03-29 9:43
 */
@Data
public class LineVo implements Comparable<LineVo>{

    /**
     *  产线编号
     */
    private Integer lineId;

    /**
     *  产线名称
     */
    private String lineName;

    /**
     *  实际产量
     */
    private Integer actualAmount;

    /**
     *  计划产量
     */
    private Integer planAmount;

    /**
     *  效率 = round(（计划产量/实际产量）*100)
     */
    private Integer efficiency;

    @Override
    public int compareTo(LineVo o) {
        return this.efficiency - o.getEfficiency();
    }
}
