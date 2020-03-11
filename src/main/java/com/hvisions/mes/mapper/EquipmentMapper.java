package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment>{
    List<Equipment> selectEquipment();
    List<Equipment> selectEquipment(Pagination page);

    //查询设备名是否存在

    Integer selectEquipmentName(String equipmentName);
    //查询设备下拉
    List<Equipment> selectEquipmentCombobox();

    //增加设备信息
    Integer InsertEquipment(Equipment equipment);

    //修改设备信息
    void UpdateEquipment(Equipment equipment);

    //删除设备信息
    void DeleteEquipment(Integer equipmentId);
    //按照产线查设备
    List<Equipment>selectEqptByLineId(Integer lineId);
    //按照设备查产线
    Integer selectEqptById(Integer equipmentId);

    List<Equipment> selectEquipmentByOrderId(Integer orderId);
}
