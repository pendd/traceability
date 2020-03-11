package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.AssignRefCode;
import com.hvisions.mes.domain.GoodsInStoreroom;
import com.hvisions.mes.domain.MaterialStoreroomHistory;
import com.hvisions.mes.domain.OrderHistory;

import java.util.List;

/**
 * @author dpeng
 * @description 追溯历史 mapper
 * @date 2019-07-25 9:12
 */
public interface RetrospectHistoryMapper {

    /**
     *  通过装配工序壳子上的码查找现线板上的码
     * @param goodsQrCode  壳子上的码
     * @return
     */
    AssignRefCode selectAssignRefCodeByGoodsQrCode(String goodsQrCode);

    /**
     * 通过成品码获取成品码所在的成品库
     * @param goodsCode  成品码
     * @return 成品库
     */
    GoodsInStoreroom selectStoreroomNameByGoodsCode(String goodsCode);

    /**
     *  通过成品码获取该成品经历过的所有工序 以及工单号
     * @param goodsCode 成品码
     * @return 工序历史对象
     */
    List<OrderHistory> selectOrderHistoryByGoodsCode(String goodsCode);

    /**
     *  通过工单号获取该工单号出库的原料来自哪些原料库
     * @param produceNumber 生产工单号
     * @return 原料库集合
     */
    List<MaterialStoreroomHistory> selectStoreroomNamesByProduceNumber(String produceNumber);

    /**
     *  通过工序ID获取所有工艺
     * @param orderId  工序ID
     * @return
     */
    List<String> selectTechnologyByOrderId(Integer orderId);

}
