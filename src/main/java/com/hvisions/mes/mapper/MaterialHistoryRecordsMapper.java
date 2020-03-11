package com.hvisions.mes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.MaterialInHistory;
import com.hvisions.mes.controller.vo.MaterialOutHistory;


public interface MaterialHistoryRecordsMapper {
    //查询原料入库历史记录
    List<MaterialInHistory> selectMaterialInHistoryRecord(Pagination page,@Param("materialCode")String materialCode);

    //查询原料出库历史记录
    List<MaterialOutHistory> selectMaterialOutHistoryRecord(Pagination page,@Param("materialCode")String materialCode);
}
