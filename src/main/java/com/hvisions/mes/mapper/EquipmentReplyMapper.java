package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.EquipmentReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dpeng
 * @description 设备反馈
 * @date 2019-08-29 14:20
 */
public interface EquipmentReplyMapper {

    void insertEquipmentReply(EquipmentReply reply);

    List<EquipmentReply> selectEquipmentReplyByPage(@Param("page") Pagination page, @Param("userId") Integer userId);

    void updateEquipmentReply(EquipmentReply equipmentReply);

    List<EquipmentReply> selectAllEquipmentReply(@Param("page") Pagination page, @Param("equipmentName") String equipmentName, @Param("taskUserName") String taskUserName);


}
