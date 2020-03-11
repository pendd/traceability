package com.hvisions.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MoldUseHistory;
import com.hvisions.mes.mapper.MoldUseHistoryMapper;
import com.hvisions.mes.service.IMoldUseHistoryService;
@Service
public class MoldUseHistoryServiceImpl implements IMoldUseHistoryService {
    @Autowired
    private MoldUseHistoryMapper moldUseHistoryMapper;
    @Override
    public Page<MoldUseHistory> queryAllMoldUSeHistoryRecords(Page<MoldUseHistory> page,Integer lineId,Integer equipmentId) {
        // TODO Auto-generated method stub
        page.setRecords(moldUseHistoryMapper.selectMoldUseHistoryRecord(page,lineId,equipmentId));
        return page;
    }

}
