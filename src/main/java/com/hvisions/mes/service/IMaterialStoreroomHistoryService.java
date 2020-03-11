package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MaterialStoreroomHistoryVo;
import com.hvisions.mes.domain.MaterialStoreroomHistory;


public interface IMaterialStoreroomHistoryService {

    /**
     *  获取原料出入库历史
     */
    Page<MaterialStoreroomHistoryVo> queryAllMaterialStoreroomHistory(Page<MaterialStoreroomHistoryVo>page,String workNumber,String startTime,String endTime,Integer inOutType);
    /**
     * 添加原料入库历史记录
     */
    void insertInHistory(MaterialStoreroomHistory history);

    /**
     * 根据'原料编码'获取数据插入原料历史库但是没有实际入库的内容
     */
    String updateHistoryByMaterialCode(String materialCode,Integer qualified);
}
