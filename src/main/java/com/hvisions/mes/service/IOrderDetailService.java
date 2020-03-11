package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.OrderDetailVo;
import com.hvisions.mes.domain.OrderDetail;

public interface IOrderDetailService {
    Page<OrderDetail> showOrderDetail(Page<OrderDetail> page,String goodsName,String orderName);


    String addOrderDetail(OrderDetail orderDetail);


    String modOrderDetail(OrderDetail orderDetail);


    void DelOrderDetail(Integer ids);

    // 查询未完成工序量top5
    List<OrderDetailVo> queryUnfinishedOrder();

    /**
     *  获取工序ID 和工序名称
     * @return
     */
    List<OrderDetail> queryOrderIdOrderName(Integer goodsId,String orderName);

    List<OrderDetail> queryOrderDetailByGoodsId(Integer goodsId);
}
