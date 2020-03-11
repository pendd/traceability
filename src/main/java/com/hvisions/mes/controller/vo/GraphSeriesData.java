package com.hvisions.mes.controller.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Author swang
 * Date 2019/3/24 14:50
 * Version 1.0
 * Description 流程图的数据部分
 **/
@Getter
@Setter
public class GraphSeriesData {
    /**节点名称*/
    private String name;
    /**节点的x轴坐标*/
    private int x;
    /**节点的y轴坐标*/
    private int y;
    private ItemStyle itemStyle;
    /**
     *  创建时间
     */
    private Date createTime;
    /**
     *  创建人
     */
    private String username;


    public GraphSeriesData(String name,int x,int y,String color){
        this.name = name;
        this.x = x;
        this.y = y;
        this.itemStyle = new ItemStyle(color);
    }

    public GraphSeriesData(String name, int x, int y, String color, Date createTime, String username){
        this.name = name;
        this.x = x;
        this.y = y;
        this.itemStyle = new ItemStyle(color);
        this.createTime = createTime;
        this.username = username;
    }

    @Data
    private static class ItemStyle{
        private String color;
        ItemStyle(String color){
            this.color = color;
        }
    }

    @Override public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof GraphSeriesData)) {
            return false;
        }
        GraphSeriesData data = (GraphSeriesData)o;
        return data.getName().equals(this.name);
    }

}
