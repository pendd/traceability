package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.LineVo;
import com.hvisions.mes.domain.ProduceOrder;
import com.hvisions.mes.mapper.ProduceOrderMapper;
import com.hvisions.mes.service.IProduceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpeng
 * @create 2019-03-21 16:20
 */
@Service
public class ProduceOrderServiceImpl implements IProduceOrderService {

    @Autowired
    private ProduceOrderMapper mapper;

    @Override
    public void addOrder(List<ProduceOrder> list) {
        mapper.insertBatchOrder(list);
    }

    /**
     *  修改生产工单状态
     * @param state
     * @return
     */
    @Override
    public int modifyOrderState(Integer produceId,Integer state) {
        // 定义一个变量  表示当前订单状态是否已被更改  0 表示未更改  1表示已更改
        int count = 0;
        // 判断当前是否是修改为正在运行状态  2为正在运行状态
        if (state == 2) {
            ProduceOrder produceOrder = getOrderById(produceId);
            List<ProduceOrder> orders = getOrder(produceOrder.getLineId().intValue());
            // 通过产线id查看当前是否有正在运行的工单
            if (orders != null) {
                // 没有
                mapper.updateOrderState(produceId,state);
                count = 1;
            } else {
                // 有
                count = 0;
            }
        }else {
            mapper.updateOrderState(produceId,state);
            count = 1;
        }
        return count;
    }

    /**
     *  根据产线查询生产工单信息
     * @param lineId
     * @return
     */
    @Override
    public List<ProduceOrder> getOrder(Integer lineId) {
        List<ProduceOrder> orders = mapper.selectOrder(lineId);
        return orders;
    }

    /**
     *  通过id获取生产工单信息
     * @param produceId
     * @return
     */
    @Override
    public ProduceOrder getOrderById(Integer produceId) {
        ProduceOrder produceOrder = mapper.selectOrderByProduceId(produceId);
        return produceOrder;
    }

    /**
     *  通过生产工单号查询工单信息
     * @param workNumber
     * @return
     */
    @Override
    public ProduceOrder getOrderByWorkNumber(String workNumber, String createTime) {
        ProduceOrder produceOrder = mapper.selectOrderByWorkNumber(workNumber,createTime);
        return produceOrder;
    }

    /**
     *  获取产线生产效率    实际产量/计划产量
     * @return
     */
    @Override
    public List<LineVo> getLineEfficiency() {
        List<LineVo> planList = mapper.selectLinePlanCount();
        List<LineVo> actualList = mapper.selectLineActualCount();
        // 计划和实际的集合有一个共同的lineId
        for (LineVo plans : planList) {
            for (LineVo actual : actualList) {
                // 相同产线编号合并数据
                if (plans.getLineId().equals(actual.getLineId())){
                    plans.setActualAmount(actual.getActualAmount());
                    break;
                }
            }
        }
        for (LineVo planVo : planList) {
            // 得到效率
            Long round = Math.round(planVo.getActualAmount() * 1.0 / planVo.getPlanAmount() * 100);
            planVo.setEfficiency(round.intValue());
        }
        return planList;
    }

    /**
     *  获取所有工单
     * @return
     */
    @Override
    public Page<ProduceOrder> queryAllProduceOrder(Page<ProduceOrder> page,String goodsName,String realStartTime,String realEndTime) {
        page.setRecords(mapper.selectAllProduceOrder(page,goodsName,realStartTime,realEndTime));
        return page;
    }

    @Override
    public List<ProduceOrder> queryProduceOrderByGoodsId(Integer goodsId,String workNumber) {
        return mapper.selectProduceOrderByGoodsId(goodsId,workNumber);
    }

}
