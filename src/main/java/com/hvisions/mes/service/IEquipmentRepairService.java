package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Equipment;
import com.hvisions.mes.domain.EquipmentRepair;

import java.util.List;

/**
 * @author dpeng
 * @description 设备维保
 * @date 2019-08-27 19:53
 */
public interface IEquipmentRepairService {

    /**
     *  分页查询设备信息
     * @param page    分页对象
     * @param repair  设备维保对象
     * @return        设备维保
     */
    Page<EquipmentRepair> queryAllByPage(Page<EquipmentRepair> page, EquipmentRepair repair);

    /**
     *  添加设备维保信息
     * @param repair 设备维保对象
     */
    void appendEquipmentRepair(EquipmentRepair repair);

    /**
     *  修改设备维保信息
     * @param repair
     */
    void changeEquipmentRepair(EquipmentRepair repair);

    /**
     * 通过ID删除设备维保信息
     * @param ids 设备维保ID
     */
    void cutEquipmentRepairById(List<Integer> ids);

    /**
     *  查询所有的设备名称和ID
     * @return  设备名称和ID
     */
    List<Equipment> queryEquipmentName();
}
