package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.MaterialStoreroomHistoryVo;
import com.hvisions.mes.controller.vo.MaterialVo;
import com.hvisions.mes.domain.MaterialStoreroomHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MaterialStoreroomHistoryMapper {

    /**
     * 查询原料出入库历史
     */
    List<MaterialStoreroomHistoryVo> selectMaterialStoreroomHistory(@Param("page")Pagination page,@Param("workNumber")String workNumber, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("inOutType") Integer inOutType);

    /**
     * 添加原料入库历史记录
     */
    void insertInHistory(MaterialStoreroomHistory history);

    /**
     * 根据'原料编码'获取数据插入原料历史库但是没有实际入库的内容
     */
    MaterialStoreroomHistory selectHistoryByMaterialCode(String materialCode);

    /**
     * 根据ID更新 原料是否合格
     */
    void updateRealInHistory(MaterialStoreroomHistory history);

    /**
     *  原料编号  出库数量  入库数量
     */
    List<MaterialVo> selectMaterialHistoryCount();
}
