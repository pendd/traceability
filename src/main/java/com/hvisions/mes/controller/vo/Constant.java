package com.hvisions.mes.controller.vo;

/**
 * Author swang
 * Date 2019/3/21 14:20
 * Version 1.0
 * Description
 **/
public class Constant {

    /**追溯系统  所有关于单位(unit)的常量 0个数1百分比*/
    public final static String SYSTEM_UNIT_QUANTITY = "0";
    /**追溯系统  所有关于单位(unit)的常量 0个数1百分比*/
    public final static String SYSTEM_UNIT_PERCENTAGE = "1";

    /**追溯系统  是否免检(0否1是) 0不免检就是必须质检*/
    public final static Integer SYSTEM_IS_CHECK_NO = 0;
    /**追溯系统  是否免检(0否1是) 1可以免检*/
    public final static Integer SYSTEM_IS_CHECK_YES = 1;

    /**原料入库 是否是原始入库 0不是原始入库1是原始入库*/
    public final static Integer MATERIAL_IS_HISTORY_NO = 0;
    /**原料入库 是否是原始入库 0不是原始入库1是原始入库*/
    public final static Integer MATERIAL_IS_HISTORY_YES = 1;
    /**原料入库 是否实际入库 0未入库1入库*/
    public final static Integer MATERIAL_IS_REAL_IN_YES = 1;

    /**库房 0原料库1成品库2线边库3半成品库*/
    public final static Integer STOREROOM_MATERIAL = 0;

    /**追溯历史记录 0原料库1线边库2投料3生产工艺4包装码垛5成品入库6经销*/
    public final static Integer TRACE_HISTORY_MATERIAL_ROOM = 0;
    public final static Integer TRACE_HISTORY_LINE_ROOM = 1;
    public final static Integer TRACE_HISTORY_START = 2;
    public final static Integer TRACE_HISTORY_WORK_ORDER = 3;
    public final static Integer TRACE_HISTORY_PACK = 4;
    public final static Integer TRACE_HISTORY_GOODS_IN_ROOM = 5;

    /**追溯历史记录 正向追溯和反向追溯   最大宽度*/
    public final static int TRACE_HISTORY_WIDTH = 1366;
    /**追溯历史记录 正向追溯和反向追溯   间距高度*/
    public final static int TRACE_HISTORY_GRID_HEIGHT = 50;
    /**追溯历史记录 正向追溯和反向追溯   点名前随机字母*/
    public final static String[] TRACE_HISTORY_ARRAY = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    /**追溯历史记录 正向追溯和反向追溯   点颜色 */
    public final static String TRACE_HISTORY_POINT_GRAY = "#2DBFC1";
    public final static String TRACE_HISTORY_POINT_BLUE = "#6495ED";
    public final static String TRACE_HISTORY_POINT_LIME = "#EEB903";
    public final static String TRACE_HISTORY_POINT_RED = "#F93841";

    public final static String TRACE_HISTORY_LINE_RED = "#FF0000";
    public final static String TRACE_HISTORY_LINE_BLUE = "#0000FF";




}
