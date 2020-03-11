package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MoldUseHistory;



public interface IMoldUseHistoryService {
    //获取所有模具使用历史记录
    Page<MoldUseHistory> queryAllMoldUSeHistoryRecords(Page<MoldUseHistory> page,Integer lineId,Integer equipmentId);
}
