package com.hvisions.mes.mapper;

import com.hvisions.mes.controller.vo.OrderDetailVo;
import com.hvisions.mes.domain.OrderHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderHistoryMapper {

    List<OrderHistory>selectOrderHistory();

    List<OrderHistory> selectOrderHistoryByCode(String productCode);

    List<OrderHistory> selectOrderHistoryByGoodsCode(String productCode);

    void insertOrderHistory(OrderHistory orderHistory);

    // 查询已完成工序数量
    List<OrderDetailVo> selectOrderHistoryCount();

}