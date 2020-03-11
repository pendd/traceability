package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Equipment;
import com.hvisions.mes.domain.EquipmentRepair;
import com.hvisions.mes.mapper.EquipmentRepairMapper;
import com.hvisions.mes.service.IEquipmentRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpeng
 * @description
 * @date 2019-08-27 19:56
 */
@Service
public class EquipmentRepairServiceImpl implements IEquipmentRepairService {

    @Autowired
    private EquipmentRepairMapper repairMapper;

    /**
     *  分页查询设备信息
     * @param page    分页对象
     * @param repair  设备维保对象
     * @return        设备维保
     */
    @Override
    public Page<EquipmentRepair> queryAllByPage(Page<EquipmentRepair> page, EquipmentRepair repair) {
        page.setRecords(repairMapper.selectAllByPage(page,repair));
        return page;
    }

    /**
     *  添加设备维保信息
     * @param repair 设备维保对象
     */
    @Override
    public void appendEquipmentRepair(EquipmentRepair repair) {
        repairMapper.insertEquipmentRepair(repair);
    }

    /**
     *  修改设备维保信息
     * @param repair
     */
    @Override
    public void changeEquipmentRepair(EquipmentRepair repair) {
        repairMapper.updateEquipmentRepair(repair);
    }

    /**
     * 通过ID删除设备维保信息
     * @param ids 设备维保ID
     */
    @Override
    public void cutEquipmentRepairById(List<Integer> ids) {
        repairMapper.deleteEquipmentRepairById(ids);
    }

    /**
     *  查询所有的设备名称和ID
     * @return  设备名称和ID
     */
    @Override
    public List<Equipment> queryEquipmentName() {
        return repairMapper.selectEquipmentName();
    }
}
