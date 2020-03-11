package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.MaterialBatch;
import com.hvisions.mes.domain.MaterialCode;
import com.hvisions.mes.domain.MaterialStoreroomHistory;

/**
 * 旧库存
 * @author dpeng
 * @date 2019-11-18 16:58
 */
public interface NewPdaInitialDataMapper {

    /**
     *  新增原料码表记录
     * @param materialCode
     */
    void insertMaterialCode(MaterialCode materialCode);

    /**
     *  新增原料批次表记录
     * @param materialBatch
     */
    void insertMaterialBatch(MaterialBatch materialBatch);

    /**
     *  原料入库
     * @param materialStoreroomHistory
     */
    void insertMaterialStoreroomHistory(MaterialStoreroomHistory materialStoreroomHistory);

    /**
     *  通过物料编码查询物料信息
     * @param materialSignCode
     * @return
     */
    Material selectMaterialByMaterialSignCode(String materialSignCode);

    /**
     *  通过供应商编码获取供应商ID
     * @param supplierCode
     * @return
     */
    Integer selectSupplierIdBySupplierCode(String supplierCode);

    /**
     *  通过库房编码查询库房ID
     * @param storeroomCode
     * @return
     */
    Integer selectStoreroomIdByStoreroomCode(String storeroomCode);
}
