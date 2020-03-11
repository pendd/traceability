package com.hvisions.mes.service.impl;

import java.util.*;

import com.hvisions.mes.controller.vo.OrderDetailVo;
import com.hvisions.mes.domain.OrderDetail;
import com.hvisions.mes.mapper.OrderDetailMapper;
import com.hvisions.mes.mapper.OrderHistoryMapper;
import com.hvisions.mes.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderHistoryMapper historyMapper;

    @Override
    public Page<OrderDetail> showOrderDetail(Page<OrderDetail> page,String goodsName,String orderName) {
        page.setRecords(orderDetailMapper.selectOrderDetail(page,goodsName,orderName));
        return page;
    }

    @Override
    public String addOrderDetail(OrderDetail detail) {
        detail.setCreateTime(new Date());
        detail.setUpdateTime(new Date());

        // 获取产品的所有工序
        List<OrderDetail> orderDetails = orderDetailMapper.selectAllOrderDetailByGoodsId(detail.getParentId());
        return appendDetailLogic(detail, orderDetails,"add");

    }

    /**
     *  新增工序逻辑
     * @param detail          当前要添加的工序
     * @param orderDetails    产品的所有工序
     * @return
     */
    private String appendDetailLogic(OrderDetail detail, List<OrderDetail> orderDetails,String type) {
        // 判断当前工序是不是维修工序
        if (detail.getOrderType() == 2) {
            // 要添加的工序是维修工序  判断该产品有没有维修工序
            Optional<OrderDetail> op = orderDetails.stream().filter(od -> od.getOrderType() != null && od.getOrderType() == 2).findFirst();
            if (op.isPresent()) {
                // 有值 代表存在维修工序  提示一个产品只能有一个维修工序
                return "1";
            }else {
                if (Objects.equals(type,"add")) {
                    return addOrderDetailThrowException(detail);
                }else if (Objects.equals(type,"modify")) {
                    return alterOrderDetailThrowException(detail);
                }else {
                    return "false";
                }
            }
        }

        // 当前工序不是维修工序
        OrderDetail orderDetail = orderDetails.stream().filter(od -> od.getOrderNum() != null).max(Comparator.comparing(OrderDetail::getOrderNum)).orElse(null);

        boolean flag = (orderDetail == null && detail.getOrderNum() != 1)
                || (orderDetail != null && detail.getOrderNum() != orderDetail.getOrderNum() + 1);
        if (flag) {
            // 前一种情况是 当前产品没有添加有顺序的工序  即要添加的工序顺序只能是1 顺序不对  顺序必须是从1开始  依次增加1
            // 后一种情况是 当前工序已经存在有顺序的工序  当前要添加的工序顺序必须是已存在的 + 1
            return "2";
         }else {
            if (Objects.equals(type,"add")) {
                return addOrderDetailThrowException(detail);
            }else if (Objects.equals(type,"modify")) {
                return alterOrderDetailThrowException(detail);
            }else {
                return "false";
            }
        }
    }


    @Override
    public String modOrderDetail(OrderDetail detail) {

        // 获取当前要修改的工序 没修改前的信息
        OrderDetail before = orderDetailMapper.selectOrderDetailByOrderId(detail.getOrderId());

        // 获取产品的所有工序
        List<OrderDetail> orderDetails = orderDetailMapper.selectAllOrderDetailByGoodsId(detail.getParentId());

        if (!Objects.equals(detail.getParentId(),before.getParentId())) {
            // 两次产品不一致 实际上是新增的另一种方式
            return appendDetailLogic(detail, orderDetails,"modify");
        }

        OrderDetail orderDetail = orderDetails.stream().filter(od -> od.getOrderNum() != null).max(Comparator.comparing(OrderDetail::getOrderNum)).orElse(null);

        // 如果之前工序是维修工序
        if (before.getOrderType() == 2) {
            // 要修改为不是维修工序
            if (detail.getOrderType() != 2) {
                boolean flag = (orderDetail == null && detail.getOrderNum() != 1)
                        || (orderDetail != null && detail.getOrderNum() != orderDetail.getOrderNum() + 1);
                if (flag) {
                    // 前一种情况是 当前产品没有添加有顺序的工序  即要添加的工序顺序只能是1 顺序不对  顺序必须是从1开始  依次增加1
                    // 后一种情况是 当前工序已经存在有顺序的工序  当前要添加的工序顺序必须是已存在的 + 1
                    return "2";
                }else {
                    return alterOrderDetailThrowException(detail);
                }
            }else {
                // 还是改为维修工序
                return alterOrderDetailThrowException(detail);
            }
        }else {
            // 之前的工序不是维修工序
            // 要修改的工序是维修工序  判断该产品有没有维修工序
            if (detail.getOrderType() == 2) {
                Optional<OrderDetail> op = orderDetails.stream().filter(od -> od.getOrderType() != null && od.getOrderType() == 2).findFirst();
                if (op.isPresent()) {
                    // 有值 代表存在维修工序  提示一个产品只能有一个维修工序
                    return "1";
                }else {
                    // 修改工序
                    return alterOrderDetailThrowException(detail);
                }
            }else {
                // 修改后的工序不是维修工序
                if (Objects.equals(before.getOrderNum(),detail.getOrderNum())) {
                    // 两次顺序一致 修改
                    return alterOrderDetailThrowException(detail);
                }else {
                    // 不一致
                    return "2";
                }
            }
        }
    }

    /**
     *  新增工序异常处理
     * @param detail
     * @return
     */
    private String addOrderDetailThrowException(OrderDetail detail) {
        try {
            orderDetailMapper.InsertOrderDetail(detail);
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "false";
        }
    }

    /**
     *  修改工序异常处理
     * @param detail
     * @return
     */
    private String alterOrderDetailThrowException(OrderDetail detail) {
        try {
            orderDetailMapper.UpdateOrderDetail(detail);
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "false";
        }
    }

    @Override
    public void DelOrderDetail(Integer ids) {
        orderDetailMapper.DeleteOrderDetail(ids);
    }

    /**
     *  查询未完成的工序数  top5
     * @return
     */
    @Override
    public List<OrderDetailVo> queryUnfinishedOrder() {
        // 总数
        List<OrderDetailVo> allList = orderDetailMapper.selectOrderDetailAllCount();

        // 已完成
        List<OrderDetailVo> finishedList = historyMapper.selectOrderHistoryCount();

        // 当orderId相同时把allCount - finishedCount = unfinishedCount
        for (OrderDetailVo allVo : allList) {
            // 计数器
            int count = 0;
            for (OrderDetailVo finishedVo : finishedList) {
                count++;
                if (allVo.getOrderId().equals(finishedVo.getOrderId())){
                    int unfinishedCount = allVo.getAllCount() - finishedVo.getFinishedCount();
                    allVo.setUnfinishedCount(unfinishedCount);
                    break;
                }
                // 没有相同的id
                if (count == finishedList.size()) {
                    // 如果有相同id的话在集合遍历结束前一定会break出去的
                    // 没有 表示 该工序一次都没有完成  即未完成量等于总量
                    allVo.setUnfinishedCount(allVo.getAllCount());
                }
            }
        }

        // 对allList进行排序
        Collections.sort(allList);
        // 判断有没有4条
        if (allList.size() >4){
            allList.subList(0,4);
        }
        return allList;
    }

    /**
     *  获取工序ID 和工序名称
     * @return
     */
    @Override
    public List<OrderDetail> queryOrderIdOrderName(Integer goodsId,String orderName) {
        return orderDetailMapper.selectOrderIdOrderName(goodsId,orderName == null ? "" : orderName.trim());
    }

    @Override
    public List<OrderDetail> queryOrderDetailByGoodsId(Integer goodsId) {
        return orderDetailMapper.selectOrderDetailByGoodsId(goodsId);
    }

}
