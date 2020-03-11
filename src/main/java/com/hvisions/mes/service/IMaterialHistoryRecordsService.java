package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MaterialInHistory;
import com.hvisions.mes.controller.vo.MaterialOutHistory;



public interface IMaterialHistoryRecordsService {
    //获取原料入库历史记录
    Page<MaterialInHistory> queryAllMerialInHistoryRecords(Page<MaterialInHistory> page,String materialCode);

    //获取原料出库历史记录
    Page<MaterialOutHistory> queryAllMerialOutHistoryRecords(Page<MaterialOutHistory> page,String materialCode);
}
