package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Equipment;

public interface IEquipmentService {
    List<Equipment> queryAllEquipment();
    Page<Equipment> showEquipment(Page<Equipment> page);

    List<Equipment> queryEquipmentCombobox();

    Integer findEquipmentName(String equipmentName);

    void AddEquipment(Equipment equipment);


    void ModEquipment(Equipment equipment);


    void DelEquipment(Integer ids);

    List<Equipment> queryEqptByLine(Integer lineId);

    Integer findLineID(Integer equipmentId);

    List<Equipment> queryEquipmentByOrderId(Integer orderId);
}
