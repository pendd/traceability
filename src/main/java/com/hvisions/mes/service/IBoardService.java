package com.hvisions.mes.service;

import com.hvisions.mes.controller.vo.GoodsCountVo;
import com.hvisions.mes.domain.*;

import java.util.List;
import java.util.Map;

/**
 * 看板接口
 *
 * @author dpeng
 * @create 2019-06-20 11:02
 */
public interface IBoardService {

    /**
     *  正在运行的工单号  计划时长  实际开始时间   剩余时间
     * @return
     */
    ProduceOrder queryRunProduceOrder();

    /**
     *  获取下一个工单
     * @return
     */
    List<GoodsMaterial> queryGoodsMaterial();

    /**
     *  获取产线产量
     * @return
     */
    Map<String, Map<Integer, List<ProduceOrder>>> queryLineCount();

    /**
     *  获取当前工单的设备运行状态
     * @param produceNumber
     * @return
     */
    List<Equipment> queryEquipment(String produceNumber);

    /**
     *  获取设备异常次数
     * @param produceNumber 生产工单号
     * @return
     */
    List<EquipmentAbnormal> queryEquipmentAbnormal(String produceNumber);

    /**
     *  获取当前工单的工序在线人数
     * @param produceNumber
     * @return
     */
    List<OrderDetail> queryOrderEmpNum(String produceNumber);

    /**
     *  获取当前工单在线总人数
     * @param produceNumber
     * @return
     */
    int querySumOrderNum(String produceNumber);

    /**
     *  获取当前工单所需的物料以及数量
     * @param produceNumber
     * @return
     */
    List<GoodsMaterial> queryCurrentGoodsMaterial(String produceNumber);

    /**
     *  获取产品的 已生产数量、未生产数量、不合格数量、维修数量
     * @param produceNumber  生产工单号
     * @return
     */
    GoodsCountVo queryGoodsCount(String produceNumber);
}
