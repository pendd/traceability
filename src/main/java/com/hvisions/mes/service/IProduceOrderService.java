package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.LineVo;
import com.hvisions.mes.domain.ProduceOrder;

import java.util.List;

public interface IProduceOrderService {

    void addOrder(List<ProduceOrder> list);

    int modifyOrderState(Integer produceId,Integer state);

    List<ProduceOrder> getOrder(Integer lineId);

    ProduceOrder getOrderById(Integer produceId);

    ProduceOrder getOrderByWorkNumber(String workNumber, String createTime);

    // 产线效率
    List<LineVo> getLineEfficiency();

    /**
     *  获取所有工单
     * @return
     */
    Page<ProduceOrder> queryAllProduceOrder(Page<ProduceOrder> page,String goodsName,String realStartTime,String realEndTime);

    List<ProduceOrder> queryProduceOrderByGoodsId(Integer goodsId,String workNumber);
}
