package com.hvisions.mes.service.impl;

import com.hvisions.mes.controller.vo.GoodsCountVo;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.mapper.BoardMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaLoginMapper;
import com.hvisions.mes.mapper.ScheduleMapper;
import com.hvisions.mes.service.IBoardService;
import com.hvisions.mes.util.DatePlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dpeng
 * @create 2019-06-20 11:03
 */
@Service
public class BoardServiceImpl implements IBoardService {

    @Autowired
    private BoardMapper boardMapper;

    /**
     *  正在运行的工单号  计划时长  实际开始时间   剩余时间
     * @return
     */
    @Override
    public ProduceOrder queryRunProduceOrder() {
        ProduceOrder produceOrder = boardMapper.selectRunProduceOrder();
        if (produceOrder == null) {
            return null;
        }
        double time = (produceOrder.getPlanEndTime().getTime() - produceOrder.getPlanStartTime().getTime()) * 1.0 / 1000 / 3600;
        DecimalFormat df = new DecimalFormat("#.0");
        // 计划时长
        produceOrder.setPlanTime(df.format(time));
        // 已经开始时长
        double over = (System.currentTimeMillis() - produceOrder.getRealStartTime().getTime()) * 1.0 / 1000 / 3600;
        // 剩余时长
        produceOrder.setOverTime(df.format(time - over));
        return produceOrder;
    }

    /**
     *  获取下一个工单
     * @return
     */
    @Override
    public List<GoodsMaterial> queryGoodsMaterial() {
        List<GoodsMaterial> list = boardMapper.selectGoodsMaterial();
        list.removeIf(Objects::isNull);
        return list;
    }

    /**
     *  获取产线产量
     * @return
     */
    @Override
    public Map<String, Map<Integer, List<ProduceOrder>>> queryLineCount() {
        List<ProduceOrder> orders = boardMapper.selectOrderHistoryCount();
        orders.removeIf(Objects::isNull);
        // 先按产线分组  后按月份分组
        return orders.stream()
                .collect(Collectors.groupingBy(ProduceOrder::getLineName, Collectors.groupingBy((ProduceOrder x) -> {
                    Calendar instance = Calendar.getInstance();
                    instance.setTime(x.getCreateTime());
                    int month = instance.get(Calendar.MONTH);
                    return month + 1;
                })));
    }

    /**
     *  获取当前工单的设备运行状态
     * @param produceNumber 生产工单号
     * @return
     */
    @Override
    public List<Equipment> queryEquipment(String produceNumber) {
        List<Equipment> list = boardMapper.selectEquipment(produceNumber);
        list.removeIf(Objects::isNull);
        return list;
    }

    /**
     *  获取设备异常次数
     * @param produceNumber 生产工单号
     * @return
     */
    @Override
    public List<EquipmentAbnormal> queryEquipmentAbnormal(String produceNumber) {
        List<EquipmentAbnormal> list = boardMapper.selectEquipmentAbnormal(produceNumber);
        list.removeIf(Objects::isNull);
        return list;
    }

    /**
     *  获取当前工单的工序在线人数
     * @param produceNumber
     * @return
     */
    @Override
    public List<OrderDetail> queryOrderEmpNum(String produceNumber) {
        List<OrderDetail> list = boardMapper.selectOrderEmpNum(produceNumber);
        int size = boardMapper.selectSumOrderNum(produceNumber).size();
        for (OrderDetail orderDetail : list) {
            orderDetail.setEmpSumNum(size);
        }
        list.removeIf(Objects::isNull);
        return list;
    }

    /**
     *  获取当前工单在线总人数
     * @param produceNumber
     * @return
     */
    @Override
    public int querySumOrderNum(String produceNumber) {
        return boardMapper.selectSumOrderNum(produceNumber).size();
    }

    /**
     *  获取当前工单所需的物料以及数量
     * @param produceNumber
     * @return
     */
    @Override
    public List<GoodsMaterial> queryCurrentGoodsMaterial(String produceNumber) {
        List<GoodsMaterial> list = boardMapper.selectCurrentGoodMaterial(produceNumber);
        list.removeIf(Objects::isNull);
        return list;
    }

    /**
     *  获取产品的 已生产数量、未生产数量、不合格数量、维修数量
     * @param produceNumber  生产工单号
     * @return
     */
    @Override
    public GoodsCountVo queryGoodsCount(String produceNumber) {

        // 获取当前工单号 生产产品要经历得检测工序
        List<Integer> checkOrderId = boardMapper.selectCheckOrderId(produceNumber);

        // 不合格品数量
        int unQualified = 0;

        // 已生产数量
        int complete = boardMapper.selectQualifiedCount(produceNumber);

        for (Integer orderId : checkOrderId) {
            Integer amount = boardMapper.selectNotQualifiedCount(produceNumber, orderId);
            // 不合格数量
            unQualified += amount == null ? 0 : amount;
        }

        // 维修数量
        int repair = boardMapper.selectRepairCount(produceNumber);
        // 计划数量
        int planAmount = boardMapper.selectPlanCount(produceNumber);

        // 未生产数量
        int unComplete = planAmount - complete - unQualified - repair;

        return new GoodsCountVo(complete,unComplete,unQualified,repair,planAmount);
    }
}
