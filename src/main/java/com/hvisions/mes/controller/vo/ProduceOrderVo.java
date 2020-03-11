package com.hvisions.mes.controller.vo;

import com.hvisions.mes.domain.ProduceOrder;
import com.hvisions.mes.util.excelUtil.ExcelField;
import lombok.Data;

import java.util.Date;

/**
 * 生产工单导入excel模板vo类
 *
 * @author dpeng
 * @create 2019-03-29 22:35
 */

@Data
public class ProduceOrderVo {

    @ExcelField(title = "生产工单号",colum = 1,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private String workNumber;

    @ExcelField(title = "生产批次号",colum = 2,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private String produceNumber;

    @ExcelField(title = "产品名称",colum = 3,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private String goodsName;

    @ExcelField(title = "工序总称",colum = 4,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private String orderFullName;

    @ExcelField(title = "产线名称",colum = 5,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private String lineName;

    @ExcelField(title = "班组名称",colum = 6,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private String teamName;

    @ExcelField(title = "次序",colum = 7,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private Integer orderNum;

    @ExcelField(title = "计划开始时间",colum = 8,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private Date planStartTime;

    @ExcelField(title = "计划结束时间",colum = 9,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private Date planEndTime;

    @ExcelField(title = "计划数量",colum = 10,claz = ProduceOrder.class,maxLength = Integer.MAX_VALUE)
    private Integer planAmount;

}
