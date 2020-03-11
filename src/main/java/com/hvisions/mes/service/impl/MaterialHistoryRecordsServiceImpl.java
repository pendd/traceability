package com.hvisions.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MaterialInHistory;
import com.hvisions.mes.controller.vo.MaterialOutHistory;
import com.hvisions.mes.mapper.MaterialHistoryRecordsMapper;
import com.hvisions.mes.service.IMaterialHistoryRecordsService;
@Service
public class MaterialHistoryRecordsServiceImpl implements IMaterialHistoryRecordsService {
    @Autowired
    private MaterialHistoryRecordsMapper materialHistoryRecordsMapper;
    //获取原料入库历史记录
    @Override
    public Page<MaterialInHistory> queryAllMerialInHistoryRecords(Page<MaterialInHistory> page,String materialCode) {
        // TODO Auto-generated method stub
        page.setRecords(materialHistoryRecordsMapper.selectMaterialInHistoryRecord(page,materialCode));
        return page;
    }
    //获取原料出库历史记录
    @Override
    public Page<MaterialOutHistory> queryAllMerialOutHistoryRecords(Page<MaterialOutHistory> page,String materialCode) {
        // TODO Auto-generated method stub
        page.setRecords(materialHistoryRecordsMapper.selectMaterialOutHistoryRecord(page,materialCode));
        return page;
    }


}
