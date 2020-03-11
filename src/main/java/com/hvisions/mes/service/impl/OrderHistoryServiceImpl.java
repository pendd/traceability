package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.OrderHistory;
import com.hvisions.mes.mapper.OrderHistoryMapper;
import com.hvisions.mes.service.IOrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryServiceImpl implements IOrderHistoryService {
    @Autowired
    OrderHistoryMapper orderHistoryMapper;

    @Override
    public List<OrderHistory> findAllOrderHistory() {
        return orderHistoryMapper.selectOrderHistory();
    }

    @Override
    public List<OrderHistory> findOrderHistoryByCode(String productCode) {
        return orderHistoryMapper.selectOrderHistoryByCode(productCode);
    }

    @Override
    public List<OrderHistory> findOrderHistoryByGoodsCode(String productCode) {
        return orderHistoryMapper.selectOrderHistoryByGoodsCode(productCode);
    }

    @Override
    public void addOrderHistory(OrderHistory orderHistory) {
        orderHistoryMapper.insertOrderHistory(orderHistory);
    }
}
