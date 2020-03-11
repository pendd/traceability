package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.OrderHistoryDTO;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaGoodsMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaOrderMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author dpeng
 * @create 2019-05-21 16:43
 */

@Service
public class NewPdaOrderServiceImpl implements INewPdaOrderService {

    @Autowired
    private NewPdaOrderMapper orderMapper;

    @Autowired
    private NewPdaGoodsMapper goodsMapper;

    @Transactional(rollbackFor = Exception.class,transactionManager = "primaryTransaction")
    @Override
    public void cutOrderHistory(OrderHistoryDTO historyDTO) {

        OrderHistory history = new OrderHistory();
        BeanUtils.copyProperties(historyDTO,history);

        // 通过成品码获取包装码
        GoodsPackRef goodsPackRef = goodsMapper.selectGoodsPackRefByGoodsCode(historyDTO.getProductCode());

        if (goodsPackRef == null) {
            // 没有包装码 直接删除工序历史表记录即可
            orderMapper.deleteOrderHistory(history);
        }else {
            // 有包装码 删除成品包装关联表中所有跟这个包装码关联的记录  并删除工序中所有的成品码记录
            // 通过包装码获取成品码
            List<GoodsPackRef> goodsPackRefs = goodsMapper.selectGoodsPackRefByFirstCode(goodsPackRef.getFirstCode());
            goodsPackRefs.forEach(g -> {
                // 删除工序中所有包装工序该成品码记录
                history.setProductCode(g.getGoodsCode());
                orderMapper.deleteOrderHistory(history);
            });

            // 删除成品包装中记录
            goodsMapper.deleteGoodsPackRefByFirstCode(goodsPackRef.getFirstCode());
        }
    }

    /**
     *  获取工单信息
     * @param produceOrder
     * @return
     */
    @Override
    public ProduceOrder selectProduceOrder(ProduceOrder produceOrder) {

        // 获取工单信息
        ProduceOrder produceOrder1 = orderMapper.selectProduceOrder(produceOrder.getLineId());

        if (produceOrder1 == null) {
            return null;
        }

        // 获取当前工序已加工数量
        int count = orderMapper.selectCount(produceOrder.getOrderId(),produceOrder1.getWorkNumber());

        produceOrder1.setWorkNum(count);
        produceOrder1.setOverNum(produceOrder1.getPlanAmount() - produceOrder1.getWorkNum());

        return produceOrder1;
    }

    /**
     *  操作说明书列表
     * @param orderId
     * @return
     */
    @Override
    public List<OrderResourceFile> selectOrderResourceFile(Integer orderId) {
        List<OrderResourceFile> orderResourceFiles = orderMapper.selectOrderResourceFile(orderId);
        for (OrderResourceFile orderResourceFile : orderResourceFiles) {
            String[] split = orderResourceFile.getFilePath().split("/");
            orderResourceFile.setFilePath(split[split.length - 1]);
        }
        return orderResourceFiles;
    }


    /**
     * 1. 0 表示普通工序 正常执行
     * 2. 1表示检测工序  要弹窗  是否合格
     * 3. 3表示当前工序不是产品正要走的工序   提示应该去xxx工序  orderName里的值
     * 4. 4表示包装工序
     * 5. 5表示所有工序已完成
     * 6. 6表示装配工序
     * 7. 7表示维修工序
     * @param orderHistory
     * @return
     */
    @Override
    public OrderDetail getOrderLogic(OrderHistory orderHistory) {
        orderHistory.setCreateTime(new Date());
        orderHistory.setQualified(1);
        orderHistory.setIsRework(0);

        // 传入的工序ID
        Integer getOrderId = orderHistory.getOrderId().intValue();

        // 成品码
        String productCode = orderHistory.getProductCode();

        // 工单号
        String produceNumber  = orderHistory.getProduceNumber();

        // 返回对象
        OrderDetail order = new OrderDetail();

        // 首先获取该成品的上一个工序
        OrderDetail oldLastOrderDetail = orderMapper.selectLastOrderId(produceNumber, productCode);

        if (oldLastOrderDetail == null) {
            // 获取该产品得第一道工序看是不是当前工序
            OrderDetail orderDetail = orderMapper.selectFirstOrderDetail(produceNumber);
            if (orderDetail != null && Objects.equals(orderDetail.getOrderId(),getOrderId)) {
                switch (orderDetail.getOrderType()) {
                    case 0 :
                        // 普通工序
                        order.setStatus(0);
                        orderMapper.insertOrderHistory(orderHistory);
                        break;
                    case 1 :
                        // 检测工序
                        order.setStatus(1);
                        break;
                    case 2 :
                        // 维修工序
                        order.setStatus(7);
                        break;
                    case 3 :
                        // 包装工序
                        order.setStatus(4);
                        break;
                    case 4 :
                        // 装配工序
                        order.setStatus(6);
                        break;
                    default: break;
                }
            }else {
                order.setStatus(3);
                order.setOrderName(orderDetail == null ? "" : orderDetail.getOrderName());
            }
            return order;
        }

        Integer oldLastOrderType = oldLastOrderDetail.getOrderType();

        // 判断工序对象
        OrderDetail orderDetail = orderMapper.selectOrderDetailById(getOrderId);
        // 表示该工序都不存在   那就没有意义了
        if (orderDetail == null) {
            return null;
        }

        // 判断当前工序类型
        Integer currentOrderType = orderDetail.getOrderType();

        switch (currentOrderType) {
            case 0 :
                // 普通工序
                // 判断是否是产品要经历的工序
                // 获取产品的上一个工序类型
                if (oldLastOrderType == 0 || oldLastOrderType == 3 || oldLastOrderType == 4) {
                    // 上一个工序是普通工序 包装 装配
                    orderLogic(orderHistory, getOrderId, produceNumber, order, oldLastOrderDetail,0);
                }else if (oldLastOrderType == 1) {
                    // 上一个工序是检测工序
                    // 判断在上一个检测工序是否合格  0 不合格 1 合格
                    if (oldLastOrderDetail.getQualified() == 0) {
                        // 不合格
                        // 应该走维修工序 获取维修工序   当前工序不是维修工序
                        OrderDetail repairOrder = orderMapper.selectRepairOrder(produceNumber);
                        order.setStatus(3);
                        order.setOrderName(repairOrder == null ? "" : repairOrder.getOrderName());
                    }else {
                        // 合格
                        // 获取下一个工序
                        orderLogic(orderHistory, getOrderId, produceNumber, order, oldLastOrderDetail,0);
                    }
                }else if (oldLastOrderType == 2) {
                    // 上一个工序是维修工序
                    // 那产品应该走的是检测工序(已经走过的检测工序 并且是不合格的)
                    OrderHistory history = orderMapper.selectLastOrderQualified(productCode,produceNumber);
                    order.setStatus(3);
                    order.setOrderName(history == null ? "" : history.getOrderName());
                }
                break;
            case 1 :
                // 检测工序
                // 判断是否是产品要经历的工序
                // 获取产品的上一个工序类型
                if (oldLastOrderType == 0 || oldLastOrderType == 3 || oldLastOrderType == 4) {
                    // 上一个工序是普通工序 包装 装配
                    // 获取下一个工序
                    orderLogic(orderHistory, getOrderId, produceNumber, order, oldLastOrderDetail,1);
                }else if (oldLastOrderType == 1) {
                    // 上一个工序是检测工序
                    // 判断在上一个检测工序是否合格  0 不合格 1 合格
                    if (oldLastOrderDetail.getQualified() == 0) {
                        // 不合格
                        // 应该走维修工序 获取维修工序   当前工序不是维修工序
                        OrderDetail repairOrder = orderMapper.selectRepairOrder(produceNumber);
                        order.setStatus(3);
                        order.setOrderName(repairOrder == null ? "" : repairOrder.getOrderName());
                    }else {
                        // 合格
                        orderLogic(orderHistory, getOrderId, produceNumber, order, oldLastOrderDetail,1);
                    }
                }else if (oldLastOrderType == 2) {
                    // 上一个工序是维修工序
                    // 那产品应该走的是检测工序(已经走过的检测工序 并且是不合格的)
                    OrderHistory history = orderMapper.selectLastOrderQualified(productCode,produceNumber);
                    if (history != null && Objects.equals(history.getOrderId().intValue(), getOrderId)) {
                        // 工序ID 相等 当前工序是成品要经历的下一个工序  并且是当前工序是检测工序
                        order.setStatus(1);
                    } else {
                        // 当前工序不是产品要走的工序
                        order.setStatus(3);
                        order.setOrderName(history == null ? "" : history.getOrderName());
                    }
                }
                break;
            case 2 :
                // 维修工序
                // 判断是否是产品要经历的工序
                // 获取产品的上一个工序类型
                if (oldLastOrderType == 0 || oldLastOrderType == 3 || oldLastOrderType == 4) {
                    // 上一个工序是普通工序 包装 装配
                    orderLogic(orderHistory,getOrderId, produceNumber, order, oldLastOrderDetail, 7);
                }else if (oldLastOrderType == 1) {
                    // 上一个工序是检测工序
                    // 判断在上一个检测工序是否合格  0 不合格 1 合格
                    if (oldLastOrderDetail.getQualified() == 0) {
                        // 不合格
                        // 应该走维修工序 获取维修工序   当前工序是维修工序
                        orderHistory.setQualified(0);
                        orderHistory.setIsRework(1);
                        orderMapper.insertOrderHistory(orderHistory);
                        order.setStatus(7);
                    }else {
                        // 合格
                        orderLogic(orderHistory,getOrderId, produceNumber, order, oldLastOrderDetail, 7);
                    }
                }else if (oldLastOrderType == 2) {
                    // 上一个工序是维修工序
                    // 那产品应该走的是检测工序(已经走过的检测工序 并且是不合格的)
                    OrderHistory history = orderMapper.selectLastOrderQualified(productCode,produceNumber);
                    order.setStatus(3);
                    order.setOrderName(history == null ? "" : history.getOrderName());
                }
                break;
            case 3 :
                // 包装工序
                // 判断是否是产品要经历的工序
                // 获取产品的上一个工序类型
                if (oldLastOrderType == 0 || oldLastOrderType == 3 || oldLastOrderType == 4) {
                    // 上一个工序是普通工序 包装 装配
                    orderLogic(orderHistory,getOrderId, produceNumber, order, oldLastOrderDetail, 4);
                }else if (oldLastOrderType == 1) {
                    // 上一个工序是检测工序
                    // 判断在上一个检测工序是否合格  0 不合格 1 合格
                    if (oldLastOrderDetail.getQualified() == 0) {
                        // 不合格
                        // 应该走维修工序 获取维修工序   当前工序不是维修工序
                        OrderDetail repairOrder = orderMapper.selectRepairOrder(produceNumber);
                        order.setStatus(3);
                        order.setOrderName(repairOrder == null ? "" : repairOrder.getOrderName());
                    }else {
                        // 合格
                        // 获取下一个工序
                        orderLogic(orderHistory,getOrderId, produceNumber, order, oldLastOrderDetail, 4);
                    }
                }else if (oldLastOrderType == 2) {
                    // 上一个工序是维修工序
                    // 那产品应该走的是检测工序(已经走过的检测工序 并且是不合格的)
                    OrderHistory history = orderMapper.selectLastOrderQualified(productCode,produceNumber);
                    order.setStatus(3);
                    order.setOrderName(history == null ? "" : history.getOrderName());
                }
                break;
            case 4 :
                // 装配工序
                // 判断是否是产品要经历的工序
                // 获取产品的上一个工序类型
                if (oldLastOrderType == 0 || oldLastOrderType == 3 || oldLastOrderType == 4) {
                    // 上一个工序是普通工序 包装 装配
                    orderLogic(orderHistory, getOrderId, produceNumber, order, oldLastOrderDetail,6);
                }else if (oldLastOrderType == 1) {
                    // 上一个工序是检测工序
                    // 判断在上一个检测工序是否合格  0 不合格 1 合格
                    if (oldLastOrderDetail.getQualified() == 0) {
                        // 不合格
                        // 应该走维修工序 获取维修工序   当前工序不是维修工序
                        OrderDetail repairOrder = orderMapper.selectRepairOrder(produceNumber);
                        order.setStatus(3);
                        order.setOrderName(repairOrder == null ? "" : repairOrder.getOrderName());
                    }else {
                        // 合格
                        // 获取下一个工序
                        orderLogic(orderHistory, getOrderId, produceNumber, order, oldLastOrderDetail,6);
                    }
                }else if (oldLastOrderType == 2) {
                    // 上一个工序是维修工序
                    // 那产品应该走的是检测工序(已经走过的检测工序 并且是不合格的)
                    OrderHistory history = orderMapper.selectLastOrderQualified(productCode,produceNumber);
                    order.setStatus(3);
                    order.setOrderName(history == null ? "" : history.getOrderName());
                }
                break;
            default: break;
        }
        return order;
    }

    /**
     *  当前工序是包装工序逻辑判断
     * @param orderHistory          工序历史对象
     * @param getOrderId            当前页面显示工序ID
     * @param produceNumber         工单号
     * @param order                 要返回状态
     * @param oldLastOrderDetail    产品的上一个工序信息
     * @param i                     当前工序是什么工序 状态表示
     */
    private void orderLogic(OrderHistory orderHistory,Integer getOrderId, String produceNumber, OrderDetail order, OrderDetail oldLastOrderDetail, int i) {
        // 获取下一个工序
        OrderDetail afterOrderDetail = orderMapper.selectAfterOrderId(produceNumber, oldLastOrderDetail.getOrderNum() + 1);
        if (afterOrderDetail == null) {
            // 表示产品没有下一个工序 即全部走完了
            order.setStatus(5);
        } else if (Objects.equals(afterOrderDetail.getOrderId(), getOrderId)) {
            // 工序ID 相等 当前工序是成品要经历的下一个工序  并且是当前工序是什么类型工序
            order.setStatus(i);
            if (i == 0) {
                orderMapper.insertOrderHistory(orderHistory);
            }
        } else {
            // 当前工序不是产品要走的工序
            order.setStatus(3);
            order.setOrderName(afterOrderDetail.getOrderName());
        }
    }

    /**
     *  获取工序所经过的所有的产品名称以及成品码
     * @param orderId           工序ID
     * @param produceNumber     生产工单号
     * @return
     */
    @Override
    public List<OrderHistory> selectOrderHistory(Integer orderId, String produceNumber) {
        return orderMapper.selectOrderHistory(orderId,produceNumber);
    }

    /**
     *  更新产品合格状态
     * @param orderHistory
     */
    @Override
    public void updateQualified(OrderHistory orderHistory) {
        if (orderHistory.getQualified() == 0) {
            // 不合格  设置返工
            orderHistory.setIsRework(1);
        }else {
            // 合格  设置不反工
            orderHistory.setIsRework(0);
        }
        orderHistory.setCreateTime(new Date());
        orderMapper.insertOrderHistory(orderHistory);
    }

    /**
     *  新增装配工序关联记录
     * @param refCode
     * @return
     */
    @Override
    @Transactional(transactionManager = "primaryTransaction",rollbackFor = Exception.class)
    public void appendAssignRefCode(AssignRefCode refCode) {
        // 入关联表
        orderMapper.insertAssignRefCode(refCode);
        // 入工序历史表

        OrderHistory history = new OrderHistory();

        history.setUserId(refCode.getUserId().longValue());
        history.setProductCode(refCode.getGoodsQrCode());
        history.setProduceNumber(refCode.getProduceNumber());
        history.setOrderId(refCode.getOrderId().longValue());
        history.setTeamId(refCode.getTeamId().longValue());
        history.setLineId(refCode.getLineId().longValue());
        history.setGoodsCode(refCode.getGoodsCode());
        history.setIsRework(0);
        history.setCreateTime(new Date());
        history.setQualified(1);

        orderMapper.insertOrderHistory(history);
    }

    /**
     * 获取产品得前段工序和后段工序
     * @param produceNumber  工单号
     * @param productCode    成品码
     * @return               前段工序 后段工序
     */
    @Override
    public Map<String, Object> queryOldAndNewOrderList(String produceNumber, String productCode) {
        // 获取产品的过往工序
        List<OrderDetail> oldOrderList = orderMapper.selectOldOrder(productCode, produceNumber);

        // 所有工序
        List<OrderDetail> allOrder = orderMapper.selectAllOrder(produceNumber);

        // 后续工序
        List<OrderDetail> newOrderList;
        if (!oldOrderList.isEmpty()) {
            // 获取过往工序的最后一个工序
            OrderDetail lastOldOrder = oldOrderList.get(oldOrderList.size() - 1);
            Integer orderType = lastOldOrder.getOrderType();
            switch (orderType) {
                case 2 :
                    // 维修工序
                    OrderHistory history = orderMapper.selectLastOrderQualified(productCode, produceNumber);
                    allOrder.removeIf(e -> e.getOrderNum() < history.getOrderNum());
                    break;
                case 0 :
                    // 普通工序
                case 1 :
                    // 检测工序
                case 3 :
                    // 包装工序
                case 4 :
                    // 装配工序
                    allOrder.removeAll(oldOrderList);
                    break;
                default: break;
            }
        }
        newOrderList = allOrder;
        Map<String,Object> map = new HashMap<>(16);
        map.put("oldOrderList",oldOrderList);
        map.put("newOrderList",newOrderList);
        return map;
    }

}
