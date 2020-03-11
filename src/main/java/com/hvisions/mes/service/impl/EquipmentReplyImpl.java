package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.EquipmentReply;
import com.hvisions.mes.mapper.EquipmentReplyMapper;
import com.hvisions.mes.service.IEquipmentReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dpeng
 * @description
 * @date 2019-08-30 14:10
 */
@Service
public class EquipmentReplyImpl implements IEquipmentReply {

    @Autowired
    private EquipmentReplyMapper replyMapper;

    @Override
    public Page<EquipmentReply> queryEquipmentReplyByPage(Page<EquipmentReply> page,Integer userId) {
        page.setRecords(replyMapper.selectEquipmentReplyByPage(page,userId));
        return page;
    }

    @Override
    public void updateEquipmentReply(EquipmentReply equipmentReply) {
        replyMapper.updateEquipmentReply(equipmentReply);
    }

    @Override
    public Page<EquipmentReply> queryAllEquipmentReply(Page<EquipmentReply> page, String equipmentName, String taskUserName) {
        page.setRecords(replyMapper.selectAllEquipmentReply(page,equipmentName,taskUserName));
        return page;
    }
}
