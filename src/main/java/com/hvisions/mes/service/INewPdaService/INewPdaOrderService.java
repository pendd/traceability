package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.OrderHistoryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @create 2019-05-21 16:43
 */
public interface INewPdaOrderService {

    void cutOrderHistory(OrderHistoryDTO historyDTO);

    // 获取工单信息
    ProduceOrder selectProduceOrder(ProduceOrder produceOrder);

    // 操作说明书列表
    List<OrderResourceFile> selectOrderResourceFile(Integer orderId);

    /** 工序执行逻辑 */
    OrderDetail getOrderLogic(OrderHistory orderHistory);

    // 获取工序所经过的所有的产品名称以及成品码
    List<OrderHistory> selectOrderHistory(Integer orderId, String produceNumber);

    // 更新产品合格状态
    void updateQualified(OrderHistory orderHistory);

    /**
     *  新增装配工序关联记录
     * @param refCode
     * @return
     */
    void appendAssignRefCode(AssignRefCode refCode);

    /**
     * 获取产品得前段工序和后段工序
     * @param produceNumber  工单号
     * @param productCode    成品码
     * @return               前段工序 后段工序
     */
    Map<String, Object> queryOldAndNewOrderList(@Param("produceNumber")String produceNumber, @Param("productCode")String productCode);
}
