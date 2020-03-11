package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Equipment;
import com.hvisions.mes.domain.EquipmentRepair;

import java.util.List;

/**
 * @author dpeng
 * @description 设备维保
 * @date 2019-08-27 19:34
 */
public interface EquipmentRepairMapper {

    /**
     *  分页查询设备信息
     * @param page    分页对象
     * @param repair  设备维保对象
     * @return        设备维保
     */
    List<EquipmentRepair> selectAllByPage(Pagination page, EquipmentRepair repair);

    /**
     *  添加设备维保信息
     * @param repair 设备维保对象
     */
    void insertEquipmentRepair(EquipmentRepair repair);

    /**
     *  修改设备维保信息
     * @param repair
     */
    void updateEquipmentRepair(EquipmentRepair repair);

    /**
     * 通过ID删除设备维保信息
     * @param ids 设备维保ID
     */
    void deleteEquipmentRepairById(List<Integer> ids);

    /**
     *  查询所有的设备名称和ID
     * @return  设备名称和ID
     */
    List<Equipment> selectEquipmentName();
}
