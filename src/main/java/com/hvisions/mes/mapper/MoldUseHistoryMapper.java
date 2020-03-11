package com.hvisions.mes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.MoldUseHistory;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface MoldUseHistoryMapper {
    //查询模具使用记录
    List<MoldUseHistory> selectMoldUseHistoryRecord(Pagination page,@Param("lineId")Integer lineId,@Param("equipmentId")Integer equipmentId);
}
