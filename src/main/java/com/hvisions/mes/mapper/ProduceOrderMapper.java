package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.LineVo;
import com.hvisions.mes.domain.ProduceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author StupidBoy
 */
@Mapper
@Repository
public interface ProduceOrderMapper{

    int insertBatchOrder(List<ProduceOrder> list);

    void updateOrderState(@Param("produceId") Integer produceId, @Param("state") Integer state);

    List<ProduceOrder> selectOrder(@Param("lineId")Integer lineId);

    ProduceOrder selectOrderByProduceId(@Param("produceId")Integer produceId);

    ProduceOrder selectOrderByWorkNumber(@Param("workNumber") String workNumber, @Param("createTime") String createTime);

    // 产线计划产量
    List<LineVo> selectLinePlanCount();

    // 产线实际产量
    List<LineVo> selectLineActualCount();

    /**
     * 获取所有工单信息
     *
     * @return 工单集合
     */
    List<ProduceOrder> selectAllProduceOrder(@Param("page")Pagination page, @Param("goodsName")String goodsName, @Param("realStartTime")String realStartTime, @Param("realEndTime")String realEndTime);

    List<ProduceOrder> selectProduceOrderByGoodsId(@Param("goodsId")Integer goodsId, @Param("workNumber")String workNumber);

}