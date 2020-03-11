package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.MaterialStockAlarm;
import com.hvisions.mes.mapper.DataSynchronismMapper;
import com.hvisions.mes.service.IDataSynchronismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpeng
 * @description
 * @date 2019-07-16 16:33
 */
@Service
public class DataSynchronismServiceImpl implements IDataSynchronismService {

    @Autowired
    private DataSynchronismMapper dataSynchronismMapper;

    /**
     *  处理原料报警表
     */
    @Override
    public void handleMaterialStockAlarm(){
        // 先删除报警表中的数据
        deleteMaterialStockAlarm();
        // 再添加报警表中的数据
        addMaterialStockAlarm();
    }


    /**
     *  报警条件解除删除原料报警表中的数据
     */
    private void deleteMaterialStockAlarm(){
        MaterialStockAlarm materialStockAlarm = new MaterialStockAlarm();
        // 查询报警表中所有
        List<MaterialStockAlarm> alarmList = dataSynchronismMapper.selectMaterialStockAlarm(materialStockAlarm);

        for (MaterialStockAlarm alarm : alarmList) {
            // 判断该记录目前库存量是否正常
            if (alarm.getAlarmType() == 0) {
                // 超量记录 判断超量是否还存在
                List<Material> upList = dataSynchronismMapper.selectAlarmUpMaterial(alarm);

                if (upList.size() == 0) {
                    // 表明上限已经不超 删除该记录
                    dataSynchronismMapper.deleteMaterialStockAlarm(alarm);
                }
            } else if (alarm.getAlarmType() == 1) {
                // 小于下限记录  判断是否还是小于下限
                List<Material> downList = dataSynchronismMapper.selectAlarmDownMaterial(alarm);
                if (downList.size() == 0) {
                    // 表明已经不低于下限 删除该记录
                    dataSynchronismMapper.deleteMaterialStockAlarm(alarm);
                }
            }

        }
    }

    /**
     *  触发报警条件往原料报警表中添加数据
     */
    private void addMaterialStockAlarm() {
        MaterialStockAlarm materialStockAlarm = new MaterialStockAlarm();
        // 查询所有
        List<Material> upList = dataSynchronismMapper.selectAlarmUpMaterial(materialStockAlarm);
        List<Material> downList = dataSynchronismMapper.selectAlarmDownMaterial(materialStockAlarm);


        if (upList.size() > 0) {
            // 存在库存量超过上限的物料
            // 判断此条数据在报警表中是否存在  存在不添加 不存在添加
            for (Material material : upList) {
                materialStockAlarm = new MaterialStockAlarm();
                materialStockAlarm.setMaterialId(material.getMaterialId());
                materialStockAlarm.setAlarmType(0);
                materialStockAlarm.setStoreroomId(material.getStoreroomId());
                List<MaterialStockAlarm> alarm = dataSynchronismMapper.selectMaterialStockAlarm(materialStockAlarm);
                if (alarm.size() == 0) {
                    // 为null 表示报警表中 不存在这条数据 插入
                    materialStockAlarm.setActualcount(material.getActualCount());
                    materialStockAlarm.setAlarmAmount(material.getAlarmStockUp());

                    dataSynchronismMapper.insertMaterialStockAlarm(materialStockAlarm);
                }
            }
        }

        if (downList.size() > 0) {
            // 存在库存量低于下限的物料
            // 判断此条数据在报警表中是否存在  存在不添加 不存在添加
            for (Material material : downList) {
                materialStockAlarm = new MaterialStockAlarm();
                materialStockAlarm.setMaterialId(material.getMaterialId());
                materialStockAlarm.setAlarmType(1);
                materialStockAlarm.setStoreroomId(material.getStoreroomId());
                List<MaterialStockAlarm> alarm = dataSynchronismMapper.selectMaterialStockAlarm(materialStockAlarm);
                if (alarm.size() == 0) {
                    // 为0 表示报警表中 不存在这条数据 插入
                    materialStockAlarm.setActualcount(material.getActualCount());
                    materialStockAlarm.setAlarmAmount(material.getAlarmStockDown());

                    dataSynchronismMapper.insertMaterialStockAlarm(materialStockAlarm);
                }
            }
        }
    }


}
