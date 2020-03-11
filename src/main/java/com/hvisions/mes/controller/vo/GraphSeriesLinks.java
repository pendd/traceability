package com.hvisions.mes.controller.vo;

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
public class GraphSeriesLinks {
    /**连线的开始节点的名称*/
    private String source;
    /**连线的结束节点的名称*/
    private String target;

    public GraphSeriesLinks(String source,String target){
        this.source = source;
        this.target = target;
    }

    @Override public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof GraphSeriesLinks)) return false;
        GraphSeriesLinks link = (GraphSeriesLinks)o;
        if(link.getSource().equals(this.source) && link.getTarget().equals(this.target)) return true;
        return false;
    }

}
