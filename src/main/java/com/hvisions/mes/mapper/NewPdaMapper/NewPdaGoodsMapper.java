package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 成品包装  入库
 *
 * @author dpeng
 * @create 2019-05-30 16:48
 */
@Mapper
@Repository
public interface NewPdaGoodsMapper {

    /** 新增成品包装表记录 */
    void insertGoodsPack(List<GoodsPack> list);

    // 新增成品包装关联表记录
    void insertGoodsPackRef(List<GoodsPack> list);
    
    // 成品入库
    void insertGoodsInStoreroom(List<GoodsInStoreroom> list);

    // 通过码判断是否在成品包装关联表中存在  如存在则为成品码  不存在即不是
    int selectCountByGoodsCode(String goodsCode);

    // 在成品包装表中获取子包装
    List<GoodsPack> selectGoodsPack(String secondCode);

    // 通过第一层包装编码获取所有的成品编码
    List<String> selectGoodsCodeByFirstCode(String firstCode);

    /**
     *  新增产量表记录
     * @param goodsTotal
     */
    void insertGoodsTotal(GoodsTotal goodsTotal);

    /**
     *  修改产量表记录
     * @param goodsTotal
     */
    void updateGoodsTotal(GoodsTotal goodsTotal);

    /**
     * 通过产品编码查询产量表记录
     * @param goodsCode      产品编码
     * @param produceNumber  生产工单号
     * @param time           日期
     * @return               产量表记录
     */
    GoodsTotal selectGoodsTotalByQrGoodsCode(@Param("goodsCode")String goodsCode, @Param("produceNumber") String produceNumber, @Param("time") Date time);

    /**
     *  查询所有的生产订单主表信息
     * @return 生产订单号
     */
    List<MomOrder> selectMomOrder();

    /**
     *  通过生产订单编号查询所有子表信息
     * @param cMoCode  生产订单编号
     * @return         子表信息
     */
    List<MomOrderDetail> selectMomOrderDeailByCMoCode(String cMoCode);

    /**
     *  通过原料编码和生产订单号获取数量
     * @param materialSignCode 原料编码
     * @param cMoCode          生产订单号
     * @return                 数量
     */
    int selectCountByCMoCodeAndMaterialSignCode(@Param("materialSignCode")String materialSignCode, @Param("cMoCode")String cMoCode);

    /**
     *  新增成品入库扫码记录表
     * @param ref 扫码记录对象
     */
    void insertGoodsStoreroomRef(GoodsStoreroomRef ref);

    /**
     *  通过生产订单号获取所有的成品码
     * @param cMoCode 生产订单号
     * @return        成品码、产品编码
     */
    List<GoodsStoreroomRef> selectQrGoodsCodeByCMoCode(String cMoCode);

    /**
     *  通过生产订单号获取生产工单号
     * @param cMoCode 生产订单号
     * @return        生产工单号
     */
    String selectWorkNumberByCMoCode(String cMoCode);

    /**
     *  通过生产订单号更新状态为已入库
     * @param cMoCode 生产订单号
     */
    void updateMomOrderDeailByCMoCode(String cMoCode);

    OrderHistory selectOrderHistroyByProductCode(String productCode);

    /**
     *  删除成品入库扫码记录
     * @param ref
     * @return
     */
    int deleteGoodsStoreroomRef(GoodsStoreroomRef ref);

    /**
     *  通过成品码获取包装码
     * @param goodsCode
     * @return
     */
    GoodsPackRef selectGoodsPackRefByGoodsCode(String goodsCode);

    /**
     *  通过包装码获取所有成品码
     * @param firstCode
     * @return
     */
    List<GoodsPackRef> selectGoodsPackRefByFirstCode(String firstCode);

    void deleteGoodsPackRefByFirstCode(String firstCode);

}
