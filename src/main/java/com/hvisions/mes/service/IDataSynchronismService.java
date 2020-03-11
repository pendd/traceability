package com.hvisions.mes.service;


/**
 * @author dpeng
 * @description 定时任务调度  报警
 * @date 2019-07-16 16:28
 */
public interface IDataSynchronismService {

    /**
     *  处理原料报警表
     */
    void handleMaterialStockAlarm();

}
