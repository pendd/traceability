package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PDA 端工序逻辑mapper
 *
 * @author dpeng
 * @create 2019-05-21 16:40
 */

@Mapper
@Repository
public interface NewPdaOrderMapper {

    void deleteOrderHistory(OrderHistory orderHistory);

    // 获取工单信息
    ProduceOrder selectProduceOrder(Long lineId);

    // 通过工序获取加工数量
    int selectCount(@Param("orderId")Integer orderId, @Param("produceNumber") String produceNumber);

    // 操作说明书列表
    List<OrderResourceFile> selectOrderResourceFile(Integer orderId);

    // 获取一个产品的所有工序
    List<OrderDetail> selectAllOrder(String produceNumber);

    // 获取产品的过往工序
    List<OrderDetail> selectOldOrder(@Param("productCode")String productCode, @Param("workNumber")String workNumber);

    // 往工序记录表中插入数据
    void insertOrderHistory(OrderHistory orderHistory);

    /**
     *  往工序记录表中批量插入数据
     * @param goodsPacks
     */
    void insertBatchOrderHistory(List<GoodsPack> goodsPacks);

    // 获取检测工序
    List<OrderDetail> selectOrderDetail();

    // 获取检测工序的后一个工序
    List<OrderDetail> selectNextOrderDetail();

    // 通过工序ID 判断是哪种工序
    OrderDetail selectOrderDetailById(Integer orderId);

    // 通过维修工序ID和成品码判断产品是否是合格的(在上一个检测工序中)
    OrderHistory selectQualifiedByRepairId(@Param("orderId") Integer orderId, @Param("productCode")String productCode);

    // 获取产品 在过往的最后一个检测工序  的合格状态
    OrderHistory selectLastOrderQualified(@Param("productCode") String productCode, @Param("produceNumber")String produceNumber);

    // 获取工序所经过的所有的产品名称以及成品码
    List<OrderHistory> selectOrderHistory(@Param("orderId") Integer orderId,@Param("produceNumber") String produceNumber);

    // 更新产品合格状态
    void updateQualified(OrderHistory orderHistory);

    void insertAssignRefCode(AssignRefCode refCode);

    /**
     *  获取成品的上一个工序
     * @param produceNumber  工单号
     * @param productCode    成品码
     * @return 工序ID  工序顺序  工序类型
     */
    OrderDetail selectLastOrderId(@Param("produceNumber")String produceNumber, @Param("productCode")String productCode);

    /**
     *  获取成品的下一个工序
     * @param produceNumber 工单号
     * @param orderNum      工序顺序
     * @return              工序ID
     */
    OrderDetail selectAfterOrderId(@Param("produceNumber")String produceNumber, @Param("orderNum")Integer orderNum);

    /**
     *  获取产品的维修工序
     * @param produceNumber 生产工单号
     * @return              工序对象
     */
    OrderDetail selectRepairOrder(String produceNumber);

    /**
     *  获取产品得第一道工序
     * @param produceNumber
     * @return
     */
    OrderDetail selectFirstOrderDetail(String produceNumber);

}
