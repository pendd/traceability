package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.domain.ArrivalOrderRef;
import com.hvisions.mes.domain.MaterialCode;
import com.hvisions.mes.domain.MaterialPack;
import com.hvisions.mes.domain.PackCode;
import com.hvisions.mes.dto.ArrivalOrderRefDTO;

import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description 打码接口 (物料码、包装码)
 * @date 2019-07-21 14:32
 */
public interface INewPdaCodingService {

    /**
     *  新增原料码表记录
     * @param materialCode
     */
    void appendMaterialCode(MaterialCode materialCode);

    /**
     *  新增包装码表记录
     * @param packCode
     */
    void appendPackCode(PackCode packCode);

    /**
     *  传进来一个二维码  判断是原料码、包装码、还是其他   0原料码  1包装码  2都不是
     * @param code
     * @return
     */
    int queryCodeTypeByCode(String code);

    /**
     *  通过二维码获取物料码
     * @param code  二维码
     * @param parentId 主表ID
     * @return
     */
    Map<String,Object> queryMaterialSignCodeByCode(Integer parentId,Integer childId,String code,String mateiralSignCode);

    /**
     *  更新到货关系表记录
     * @param ref  到货关系表对象
     */
    void addArrivalOrderRef(ArrivalOrderRef ref);

    /**
     *  删除到货关系表记录
     * @param refDTO
     */
    int deleteArrivalOrderRef(ArrivalOrderRefDTO refDTO);

    /**
     *  入原料包装关联表或者原料包装表
     * @param materialPacks  原料包装对象
     */
    void putMaterialPackOrRef(List<MaterialPack> materialPacks);

    /**
     * 通过一个码判断它的类型
     * @param code  二维码
     * @return      二维码类型
     */
    int guessCodeType(String code);

    /**
     *  获取第一层包装编码
     * @param secondCode  包装码
     * @return  所有的第一层包装码
     */
    List<String> getMinFirstCode(String secondCode);
}
