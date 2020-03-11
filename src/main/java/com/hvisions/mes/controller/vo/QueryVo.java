package com.hvisions.mes.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Author swang
 * Date 2019/3/19 17:55
 * Version 1.0
 * Description
 **/
@Data
public class QueryVo {

    private Long teamId;//班组ID
    private Long lineId;//产线ID
    private Long userId;//产线ID
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private String workNumber;//生产工单号码
    /**
     * 直接设置开始和结束时间  并且带有格式转换
     */
    public void setStartAndEnd(LocalDateTime start,LocalDateTime end){
        this.setStartTime(Date.from(start.atZone(ZoneId.systemDefault()).toInstant()));
        this.setEndTime(Date.from(end.atZone(ZoneId.systemDefault()).toInstant()));
    }
}
