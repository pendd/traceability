package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.MaterialCode;
import com.hvisions.mes.domain.PackCode;

/**
 * @author dpeng
 * @description 打码mapper (物料码、包装码)
 * @date 2019-07-21 12:24
 */
public interface NewPdaCodingMapper {

    /**
     *  物料码打码 入库
     * @param materialCode
     */
    void insertMaterialCode(MaterialCode materialCode);

    /**
     *  包装码打码  入库
     * @param packCode
     */
    void insertPackCode(PackCode packCode);

    /**
     *  通过一个码判断在物料码表中是否存在 存在 则是物料码  不存在 则不是物料码
     * @param materialCode
     * @return
     */
    MaterialCode selectMaterialCodeByCode(String materialCode);

    /**
     *  通过一个码判断在包装码表中是否存在 存在 则是包装码  不存在 则不是包装码
     * @param packCode
     * @return
     */
    PackCode selectPackCodeByCode(String packCode);

    /**
     *  通过物料码获取物料基本信息
     * @param materialSignCode   物料码
     * @return  物料基本信息
     */
    Material selectMaterialByMaterialSignCode(String materialSignCode);

    /**
     *  通过物料二维码获取物料信息
     * @param code  物料二维码
     * @return 物料码
     */
    String selectMaterialSignCodeByCode(String code);
}
