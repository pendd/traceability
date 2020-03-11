package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.LineStoreroomHistoryVo;
import org.apache.ibatis.annotations.Param;



public interface ILineStoreroomHistoryService {

    /**
     * 查询线边库出入库历史纪录
     */
    Page<LineStoreroomHistoryVo> queryAllLineStoreroomHistory(Page<LineStoreroomHistoryVo> page, @Param("lineStoreroomId") Long lineStoreroomId,@Param("inOutType") Integer inOutType);

    /**
     *  查询线边库出库历史
     * @param page
     * @param lineStoreroomId
     * @return
     */
    Page<LineStoreroomHistoryVo> queryLineStoreroomOutHistory(Page<LineStoreroomHistoryVo> page, @Param("lineStoreroomId") Long lineStoreroomId);

    // 查询线边库入库历史
    Page<LineStoreroomHistoryVo> queryLineStoreroomInHistory(Page<LineStoreroomHistoryVo> page,Long lineStoreroomId);
}
