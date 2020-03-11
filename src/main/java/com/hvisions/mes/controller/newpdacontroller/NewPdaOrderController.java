package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.dto.OrderHistoryDTO;
import com.hvisions.mes.service.INewPdaService.INewPdaOrderService;
import com.hvisions.mes.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PDA端工序逻辑控制器
 *
 * @author dpeng
 * @create 2019-05-21 16:39
 */
@RestController
@RequestMapping("/json/pda/order")
public class NewPdaOrderController {

    private PdaResponseData pdaResponseData;
    private Map<String,Object> data;

    @Autowired
    private INewPdaOrderService orderService;

    @ModelAttribute
    public void init() {
        pdaResponseData = new PdaResponseData();
        data = new HashMap<>(16);
    }

    @ApiOperation(value = "删除包装工序记录")
    @PostMapping("/removeOrderHistory")
    public PdaResponseData removeOrderHistory(@Validated @RequestBody OrderHistoryDTO historyDTO) {
        orderService.cutOrderHistory(historyDTO);
        return pdaResponseData;
    }

    /**
     *  获取生产工单总信息
     * @param produceOrder
     * @return
     */
    @RequestMapping("/orderList")
    public PdaResponseData getProduceOrder(@RequestBody ProduceOrder produceOrder){
        if (produceOrder == null || produceOrder.getLineId() == null || produceOrder.getOrderId() == null ) {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("参数传入不正确");
            return pdaResponseData;
        }

        ProduceOrder order = orderService.selectProduceOrder(produceOrder);
        data.put("orderList",order);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取操作说明书列表
     * @param order
     * @return
     */
    @RequestMapping("/fileList")
    public PdaResponseData getFileList(@RequestBody OrderDetail order) {
        if (order == null || order.getOrderId() == null) {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("参数传入不正确");
            return pdaResponseData;
        }
        List<OrderResourceFile> files = orderService.selectOrderResourceFile(order.getOrderId());
        data.put("orderResourceFile",files);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }

    /**
     *  获取产品的过往工序  当前工序  后续工序
     * @param orderHistory
     * @return
     */
    @RequestMapping("/showOrderList")
    public PdaResponseData getOrderList(@RequestBody OrderHistory orderHistory) {
        if (orderHistory == null || StringUtil.isNull(orderHistory.getProductCode())
                || StringUtil.isNull(orderHistory.getProduceNumber())
                || StringUtil.isNull(orderHistory.getGoodsCode())) {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("参数传入不正确");
            return pdaResponseData;
        }
        Map<String, Object> map = orderService.queryOldAndNewOrderList(orderHistory.getProduceNumber(), orderHistory.getProductCode());
        pdaResponseData.setData(map);
        return pdaResponseData;
    }

    /**
     *  工序处理逻辑
     * @param history
     * @return
     */
    @RequestMapping("/showOrderMessage")
    public PdaResponseData showOrderMessage(@RequestBody OrderHistory history) {
        if (StringUtil.isNull(history.getProductCode())
                || StringUtil.isNull(history.getProduceNumber())
                || history.getUserId() == null
                || history.getOrderId() == null
                || history.getTeamId() == null
                || StringUtil.isNull(history.getGoodsCode())) {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("参数传入不正确");
            return pdaResponseData;
        }

        OrderDetail order = orderService.getOrderLogic(history);

        if (order != null) {
            List<OrderHistory> goods = orderService.selectOrderHistory(history.getOrderId().intValue(), history.getProduceNumber());
            data.put("status",order.getStatus());
            data.put("orderName",order.getOrderName());
            data.put("goods",goods);
            pdaResponseData.setData(data);
            return pdaResponseData;
        } else {
            pdaResponseData.setStatus(PdaResponseData.STATUS_FAIL);
            pdaResponseData.setErrorMessage("工序异常");
            return pdaResponseData;
        }

    }

    /**
     *  获取工序所经过的所有的产品名称以及成品码
     * @param orderHistory
     * @return
     */
    @RequestMapping("/getOrderHistory")
    public PdaResponseData getOrderHistory(@RequestBody OrderHistory orderHistory) {
        List<OrderHistory> orderHistories = orderService.selectOrderHistory(orderHistory.getOrderId().intValue(), orderHistory.getProduceNumber());
        data.put("orderHistories",orderHistories);
        pdaResponseData.setData(data);
        return pdaResponseData;
    }


    /**
     *  更新产品合格状态
     * @param orderHistory
     * @return
     */
    @RequestMapping("/updateOrderHistoryQualified")
    public PdaResponseData modifyQualified(@RequestBody OrderHistory orderHistory) {
        orderService.updateQualified(orderHistory);
        return pdaResponseData;
    }

    @PostMapping("/addAssignRefCode")
    public PdaResponseData addAssignRefCode(@RequestBody AssignRefCode refCode) {
        orderService.appendAssignRefCode(refCode);
        return pdaResponseData;
    }
}
