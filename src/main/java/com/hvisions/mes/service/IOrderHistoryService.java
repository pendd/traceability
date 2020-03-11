package com.hvisions.mes.service;

import com.hvisions.mes.domain.OrderHistory;

import java.util.List;

public interface IOrderHistoryService {
    List<OrderHistory> findAllOrderHistory();
    List<OrderHistory> findOrderHistoryByCode(String productCode);
    List<OrderHistory> findOrderHistoryByGoodsCode(String productCode);
    void addOrderHistory(OrderHistory orderHistory);
}
