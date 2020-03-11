package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 看板mapper
 *
 * @author dpeng
 * @create 2019-06-20 10:57
 */
@Mapper
@Repository
public interface BoardMapper {

    /**
     *  正在运行的工单
     * @return
     */
    ProduceOrder selectRunProduceOrder();

    /**
     *  获取下一个订单
     * @return
     */
    List<GoodsMaterial> selectGoodsMaterial();

    /**
     *  获取包装工序的历史记录
     * @return
     */
    List<ProduceOrder> selectOrderHistoryCount();

    /**
     * 获取当前工单的设备运行状态
     *
     * @param produceNumber 生产工单号
     * @return
     */
    List<Equipment> selectEquipment(@Param("produceNumber")String produceNumber);

    /**
     * 获取设备异常次数
     *
     * @param produceNumber 生产工单号
     * @return
     */
    List<EquipmentAbnormal> selectEquipmentAbnormal(@Param("produceNumber")String produceNumber);

    /**
     * 获取当前工单的工序在线人数
     *
     * @param produceNumber
     * @return
     */
    List<OrderDetail> selectOrderEmpNum(@Param("produceNumber")String produceNumber);

    /**
     * 获取当前工单在线总人数
     *
     * @param produceNumber
     * @return
     */
    List<Emp> selectSumOrderNum(@Param("produceNumber")String produceNumber);

    /**
     * 获取当前工单所需的物料以及数量
     *
     * @param produceNumber
     * @return
     */
    List<GoodsMaterial> selectCurrentGoodMaterial(@Param("produceNumber")String produceNumber);

    /**
     * 不合格品数量
     *
     * @param produceNumber 生产工单号
     * @return
     */
    Integer selectNotQualifiedCount(@Param("produceNumber")String produceNumber, @Param("orderId")Integer orderId);

    /**
     * 已生产数量
     *
     * @param produceNumber 生产工单号
     * @return
     */
    int selectQualifiedCount(@Param("produceNumber")String produceNumber);

    /**
     * 维修数量
     *
     * @param produceNumber 生产工单号
     * @return
     */
    int selectRepairCount(@Param("produceNumber")String produceNumber);

    /**
     * 计划数量
     *
     * @param produceNumber 生产工单号
     * @return
     */
    int selectPlanCount(@Param("produceNumber")String produceNumber);

    /**
     * 获取产品的所有检测工序
     *
     * @param produceNumber 工单号
     * @return
     */
    List<Integer> selectCheckOrderId(@Param("produceNumber")String produceNumber);

}
