package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.LineStoreroomHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  线边库出入库历史纪录
 * @author dpeng
 */
@Mapper
@Repository
public interface LineStoreroomHistoryMapper {

    /**
     * 查询线边库出入库历史纪录   逻辑有问题
     */
    List<LineStoreroomHistoryVo> selectLineStoreroomHistory(Pagination page, @Param("lineStoreroomId") Long lineStoreroomId,@Param("inOutType") Integer inOutType);

    /**
     *  查询线边库出库历史
     * @param page
     * @param lineStoreroomId
     * @return
     */
    List<LineStoreroomHistoryVo> selectLineStoreroomOutHistory(Pagination page, @Param("lineStoreroomId") Long lineStoreroomId);

    // 查询线边库入库历史
    List<LineStoreroomHistoryVo> selectLineStoreroomInHistory(Pagination page,Long lineStoreroomId);
}