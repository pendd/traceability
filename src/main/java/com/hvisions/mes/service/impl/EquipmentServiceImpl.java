package com.hvisions.mes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Equipment;
import com.hvisions.mes.mapper.EquipmentMapper;
import com.hvisions.mes.service.IEquipmentService;
@Service
public class EquipmentServiceImpl implements IEquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Override
    public List<Equipment> queryAllEquipment() {
        // TODO Auto-generated method stub
        return equipmentMapper.selectEquipment();
    }
    @Override
    public Page<Equipment> showEquipment(Page<Equipment> page) {
        page.setRecords(equipmentMapper.selectEquipment(page));
        return page;
    }

    @Override
    public List<Equipment> queryEquipmentCombobox() {
        return equipmentMapper.selectEquipmentCombobox();
    }

    @Override
    public Integer findEquipmentName(String equipmentName) {
        return equipmentMapper.selectEquipmentName(equipmentName);
    }

    @Override
    public void AddEquipment(Equipment equipment) {
        equipmentMapper.InsertEquipment(equipment);
    }

    @Override
    public void ModEquipment(Equipment equipment) {
        equipmentMapper.UpdateEquipment(equipment);
    }

    @Override
    public void DelEquipment(Integer ids) {
        equipmentMapper.DeleteEquipment(ids);
    }
    @Override
    public List<Equipment> queryEqptByLine(Integer lineId) {
        // TODO Auto-generated method stub
        return equipmentMapper.selectEqptByLineId(lineId);
    }

    @Override
    public Integer findLineID(Integer equipmentId) {
        return equipmentMapper.selectEqptById(equipmentId);
    }

    @Override
    public List<Equipment> queryEquipmentByOrderId(Integer orderId) {
        return equipmentMapper.selectEquipmentByOrderId(orderId);
    }
}
