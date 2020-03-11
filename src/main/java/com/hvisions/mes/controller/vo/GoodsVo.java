package com.hvisions.mes.controller.vo;


import lombok.Data;

/**
 * 成品合格率
 *
 * @author dpeng
 * @create 2019-03-27 21:36
 */
@Data
public class GoodsVo implements Comparable<GoodsVo>{

    /**
     *  成品名称
     */
    private String goodsName;

    /**
     *  合格率数量
     */
    private Integer divideCount;

    /**
     *  总数量
     */
    private Integer allCount;

    /**
     *  合格率  小数形式
     */
    private Double qualifiedPercent;

    /**
     *  合格率 取整
     */
    private Integer qualifiedPercentRound;

    /**
     *  定制排序规则   降序
     * @param o
     * @return
     */
    @Override
    public int compareTo(GoodsVo o) {
        double value = this.qualifiedPercent - o.getQualifiedPercent();
        int result = 0;
        if (value >0){
            result = 1;
        }else if (value < 0) {
            result = -1;
        }else {
            result = 0;
        }
        return result;
    }
}
