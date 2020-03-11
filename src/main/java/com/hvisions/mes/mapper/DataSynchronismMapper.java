package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.MaterialStockAlarm;

import java.util.List;

/**
 * @description  定时任务调度 报警
 * @author dpeng
 * @date 2019-07-16 15:50
 */
public interface DataSynchronismMapper {

    /**
     *  查询触发报警库存上限的物料
     * @param alarm
     * @return
     */
    List<Material> selectAlarmUpMaterial(MaterialStockAlarm alarm);

    /**
     *  查询触发报警库存下限的物料
     * @param alarm
     * @return
     */
    List<Material> selectAlarmDownMaterial(MaterialStockAlarm alarm);

    /**
     *  新增报警数据
     * @param alarm
     */
    void insertMaterialStockAlarm(MaterialStockAlarm alarm);

    /**
     * 通过id删除报警数据
     * @param alarm
     */
    void deleteMaterialStockAlarm(MaterialStockAlarm alarm);

    /**
     *  通过物料ID 和报警类型查询物料
     * @param alarm
     * @return
     */
    List<MaterialStockAlarm> selectMaterialStockAlarm(MaterialStockAlarm alarm);

}
