package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.GoodsStoreroomRefDTO;

import java.util.List;

/**
 * @author dpeng
 * @create 2019-05-30 17:00
 */
public interface INewPdaGoodsService {

    /**
     *  删除成品入库扫码记录
     * @param refDTO
     * @return
     */
    int cutGoodsStoreroomRef(GoodsStoreroomRefDTO refDTO);

    /**
     *  成品包装信息批量提交
     * @param goodsPacks
     */
    void appendGoodsPackList(List<GoodsPack> goodsPacks);

    /**
     * 成品入库
     * @param cMoCode     生产订单号
     * @param userId      用户ID
     * @param teamId      班组ID
     * @param storeroomId 库房ID
     */
    void appendGoodsInStoreroom(String cMoCode,Integer userId,Integer teamId,Integer storeroomId);

    /**
     *  查询所有生产订单主表信息
     * @return  生产订单编号
     */
    List<MomOrder> queryMomOrder();

    /**
     *  通过生产订单编号查询子表信息
     * @param cMoCode 生产订单编号
     * @return        子表信息
     */
    List<MomOrderDetail> queryMomOrderDetailByCMoCode(String cMoCode);

    /**
     *  新增成品入库扫码记录
     * @param ref  记录对象
     */
    void appendGoodsStoreroomRef(GoodsStoreroomRef ref);

    /**
     *  成品入库扫码返回数量
     * @param qrCode 成品码
     * @return       数量
     */
    int selectCountByQrCode(String qrCode);

    /**
     *  获取第一层包装编码
     * @param secondCode 包装码
     * @return 第一层包装码集合
     */
    List<String> getMinFirstCode(String secondCode);

    /**
     *  通过二维码 判断是否是包装码
     * @param qrCode  二维码
     * @return        0 不是包装码  1 是包装码
     */
    int guessGoodsCode(String qrCode);

    /**
     *  成品入库扫码逻辑
     * @param qrCode  成品码或者成品包装码
     * @param cInvCode   产品码即物料码
     * @return
     */
    String scanGoodsCodeLogic(String qrCode,String cInvCode);
}
