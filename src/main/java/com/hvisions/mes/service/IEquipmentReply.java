package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.EquipmentReply;

/**
 * @author dpeng
 * @description 设备反馈接口
 * @date 2019-08-30 14:09
 */
public interface IEquipmentReply {

    Page<EquipmentReply> queryEquipmentReplyByPage(Page<EquipmentReply> page, Integer userId);

    void updateEquipmentReply(EquipmentReply equipmentReply);

    Page<EquipmentReply> queryAllEquipmentReply(Page<EquipmentReply> page, String equipmentName, String taskUserName);
}
