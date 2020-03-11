package com.hvisions.mes.controller;

import com.hvisions.mes.controller.vo.GoodsCountVo;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.service.IBoardService;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 看板控制器
 *
 * @author dpeng
 * @create 2019-06-20 13:28
 */
@RestController
@RequestMapping("/board")
@ApiIgnore
public class BoardController {

    @Autowired
    private IBoardService boardService;

    /**
     *  获取正在运行的生产工单号  计划时长  开始时间  剩余时长
     * @return
     */
    @RequestMapping("/getRunProduceOrder")
    public ProduceOrder getRunProduceOrder() {
        return boardService.queryRunProduceOrder();
    }

    /**
     *  获取产品和原材料的关系
     * @return
     */
    @RequestMapping("/getGoodsMaterial")
    public List<GoodsMaterial> getGoodsMaterial() {
        return boardService.queryGoodsMaterial();
    }

    /**
     *  获取产线产量
     * @return
     */
    @RequestMapping("/getLineCount")
    public Map<String, Map<Integer, List<ProduceOrder>>> getLineCount() {
        return boardService.queryLineCount();
    }

    /**
     *  获取当前工单的设备运行状态
     * @param produceNumber
     * @return
     */
    @RequestMapping("/getEquipmentState")
    public List<Equipment> getEquipmentState(String produceNumber) {
        return boardService.queryEquipment(produceNumber);
    }

    /**
     *  获取设备异常次数
     * @param produceNumber 生产工单号
     * @return
     */
    @RequestMapping("/getEquipmentAbnormal")
    public List<EquipmentAbnormal> getEquipmentAbnormal(String produceNumber) {
        return boardService.queryEquipmentAbnormal(produceNumber);
    }

    /**
     *  获取工序在线人数
     * @param produceNumber  生产工单号
     * @return
     */
    @RequestMapping("/getOrderEmpNum")
    public List<OrderDetail> getOrderEmpNum(String produceNumber) {
        return boardService.queryOrderEmpNum(produceNumber);
    }

    /**
     *  获取当前工单在线总人数
     * @param produceNumber
     * @return
     */
    @RequestMapping("/getSumOrderNum")
    public Integer getSumOrderNum(String produceNumber) {
        return boardService.querySumOrderNum(produceNumber);
    }

    /**
     *  获取当前工单所需的物料以及数量
     * @param produceNumber
     * @return
     */
    @RequestMapping("/getCurrentGoodsMaterial")
    public List<GoodsMaterial> getCurrentGoodsMaterial(String produceNumber) {
        return boardService.queryCurrentGoodsMaterial(produceNumber);
    }

    /**
     *  获取产品的 已生产数量、未生产数量、不合格数量、维修数量
     * @param produceNumber  生产工单号
     * @return
     */
    @RequestMapping("/getGoodsCount")
    public GoodsCountVo getGoodsCount(String produceNumber) {
        return boardService.queryGoodsCount(produceNumber);
    }
}
