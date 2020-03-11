package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dpeng
 * @create 2019-05-31 16:42
 */
@Mapper
@Repository
public interface NewPdaMaterialMapper {

    // 原料入库列表历史记录
    List<MaterialStoreroomHistory> selectMaterialHistoryList(@Param("startTime") String startTime, @Param("endTime") String endTime);

    // 批量入原料入库表
    void insertMaterialHistoryList(List<MaterialBatch> list);

    // 批量入原料包装关联表
    void insertMaterialPackRefList(List<MaterialPack> list);

    // 批量入原料包装表
    void insertMaterialPackList(List<MaterialPack> list);

    // 批量入批次表
    void insertMaterialBatchList(List<MaterialBatch> list);

    // 新增批次表记录
    void insertMaterialBatch(MaterialBatch materialBatch);

    /**
     *  查询子表中是对应库房的主表信息
     * @param storeroomId   库房ID
     * @return  到货单主表信息
     */
    List<PuArrivalvouch> selectPuArrivalVouch(Integer storeroomId);

    /**
     *  通过主表Id查询对应库房的子表信息
     * @param storeroomId  库房ID
     * @param id   主表ID
     * @return  子表集合
     */
    List<PuArrivalvouchs> selectPuArrivalVouchs(@Param("storeroomId")Integer storeroomId, @Param("id")Integer id);

    /**
     *  更新子表状态为已入库
     * @param childId  子表ID
     * @param state    是否入库  0否1是
     */
    void updatePuArrivalVouchs(@Param("childId")Integer childId, @Param("state")Integer state);

    /**
     *  更新到货已扫码记录
     * @param arrivalOrderRef  到货单关系表对象
     */
    void updateArrivalOrderRef(ArrivalOrderRef arrivalOrderRef);

    /**
     *  新增到货已扫码记录
     * @param arrivalOrderRef  到货单关系表对象
     */
    void insertArrivalOrderRef(ArrivalOrderRef arrivalOrderRef);

    /**
     *  删除到货已扫码记录
     * @param arrivalOrderRef
     */
    int deleteArrivalOrderRef(ArrivalOrderRef arrivalOrderRef);

    /**
     *  组合条件查询到货已扫码记录表
     * @param arrivalOrderRef 到货单关系表对象
     * @return 对象集合
     */
    List<ArrivalOrderRef> selectArrivalOrderRefByCondition(ArrivalOrderRef arrivalOrderRef);

    /**
     *  通过主表ID和子表ID查询数量
     * @param parentId 主表ID
     * @param childId  子表ID
     * @return  数量
     */
    int selectCountCodeByParentAndChildId(@Param("parentId")Integer parentId, @Param("childId")Integer childId);

    /**
     *  通过主表ID和物料编码查询字表ID
     * @param parentId   主表ID
     * @param materialSignCode  物料编码
     * @return
     */
    int selectChildIdByParentIdAndMaterialSignCode(@Param("parentId")Integer parentId, @Param("materialSignCode")String materialSignCode);

    /**
     * 通过物料二维码查询物料供应商、库房、原料、单位等信息
     * @param materialCode 物料二维码
     * @param parentId 主表ID
     * @param storeroomId 库房ID
     * @return 供应商、库房、原料、单位等信息
     */
    List<MaterialBatch> selectErpMesInfoByQrCode(@Param("materialCode")String materialCode, @Param("parentId")Integer parentId, @Param("storeroomId")Integer storeroomId);

    /**
     * 通过原料二维码更新打码表中原料供应商和批次号
     * @param qrCode 原料二维码
     * @param supplierId 供应商ID
     * @param supplierNumber 原批次号
     */
    void updateMaterialCodeByQrCode(@Param("qrCode")String qrCode, @Param("supplierId")Integer supplierId, @Param("supplierNumber")String supplierNumber);

}
