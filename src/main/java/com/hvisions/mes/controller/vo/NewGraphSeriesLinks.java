package com.hvisions.mes.controller.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Author swang
 * Date 2019/3/24 14:50
 * Version 1.0
 * Description 流程图的连接线部分
 **/
@Getter
@Setter
public class NewGraphSeriesLinks {
    /**连线的开始节点的名称*/
    private String source;
    /**连线的结束节点的名称*/
    private String target;
    private LineStyle lineStyle;

    public NewGraphSeriesLinks(String source, String target, String color, Integer width){
        this.source = source;
        this.target = target;
        this.lineStyle = new LineStyle(color,width);
    }

    @Override public boolean equals(Object o){
        if (o == this) {return true;}
        if (!(o instanceof NewGraphSeriesLinks)) {return false;}
        NewGraphSeriesLinks link = (NewGraphSeriesLinks)o;
        if(link.getSource().equals(this.source) && link.getTarget().equals(this.target)) {return true;}
        return false;
    }

    @Data
    private static class LineStyle{
        private String color;
        private Integer width;
        LineStyle(String color,Integer width) {
            this.color = color;
            this.width = width;
        }
    }

}
