package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.dto.ArrivalOrderRefDTO;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaAssignMaterialMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaBasicInfoMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaCodingMapper;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaMaterialMapper;
import com.hvisions.mes.service.INewPdaService.INewPdaCodingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author dpeng
 * @description  打码接口实现类 (物料码、包装码)
 * @date 2019-07-21 14:35
 */
@Service
public class NewPdaCodingServiceImpl implements INewPdaCodingService {

    @Autowired
    private NewPdaCodingMapper codingMapper;

    @Autowired
    private NewPdaAssignMaterialMapper assignMaterialMapper;

    @Autowired
    private NewPdaMaterialMapper materialMapper;

    @Autowired
    private NewPdaBasicInfoMapper basicInfoMapper;

    /**
     *  新增原料码表记录
     * @param materialCode
     */
    @Override
    public void appendMaterialCode(MaterialCode materialCode) {
        // 通过原料码获取原料信息
        Material material = codingMapper.selectMaterialByMaterialSignCode(materialCode.getMaterialSignCode());
        materialCode.setUnit(material.getUnit());
        materialCode.setSpecs(material.getSpecs());
        codingMapper.insertMaterialCode(materialCode);
    }

    /**
     *  新增包装码表记录
     * @param packCode
     */
    @Override
    public void appendPackCode(PackCode packCode) {
        codingMapper.insertPackCode(packCode);
    }

    /**
     *  传进来一个二维码  判断是原料码、包装码、还是其他   0原料码  1包装码 2都不是
     * @param code
     * @return
     */
    @Override
    public int queryCodeTypeByCode(String code) {
        return guessCodeType(code);
    }

    /**
     *  原料入库扫码判断是否重复
     * @param code  二维码
     * @param parentId 主表ID
     * @return isRepeat 1  扫过  0  没扫过   isMatch  1 匹配 0 不匹配
     */
    @Override
    public Map<String,Object> queryMaterialSignCodeByCode(Integer parentId,Integer childId,String code,String materialSignCode) {
        ArrivalOrderRef ref = new ArrivalOrderRef();
        ref.setParentId(parentId);
        ref.setChildId(childId);
        ref.setQrCode(code);

        Map<String,Object> map = new HashMap<>(16);

        // 判断是否匹配
        Material material = basicInfoMapper.selectMaterialFromMaterialCode(code);

        if (material != null && Objects.equals(materialSignCode,material.getMaterialSignCode())) {
            // 匹配
            map.put("isMatch",1);

            // 判断这个物料二维码在关系表中是否存在  即是否已经扫码过了
            List<ArrivalOrderRef> arrivalOrderRefs = materialMapper.selectArrivalOrderRefByCondition(ref);

            if (arrivalOrderRefs.isEmpty()) {
                // 为null 不存在 没扫过
                map.put("isRepeat",0);
            }else {
                map.put("isRepeat",1);
            }
        }else {
            map.put("isMatch",0);
        }

        return map;
    }

    /**
     *  新增到货关系表记录
     * @param ref  到货关系表对象
     */
    @Override
    public void addArrivalOrderRef(ArrivalOrderRef ref) {
        materialMapper.insertArrivalOrderRef(ref);
    }

    /**
     *  删除到货关系表记录
     * @param refDTO
     */
    @Override
    public int deleteArrivalOrderRef(ArrivalOrderRefDTO refDTO) {
        ArrivalOrderRef ref = new ArrivalOrderRef();
        BeanUtils.copyProperties(refDTO,ref);
        return materialMapper.deleteArrivalOrderRef(ref);
    }

    /**
     *  入原料包装关联表或者原料包装表
     * @param materialPacks 原料包装对象
     */
    @Override
    public void putMaterialPackOrRef(List<MaterialPack> materialPacks) {
        // 入原料包装关联表数据
        List<MaterialPack> packsRef = new ArrayList<>();
        // 入原料包装表数据
        List<MaterialPack> packs = new ArrayList<>();
        for (MaterialPack pack : materialPacks) {
            int type = guessCodeType(pack.getFirstCode());
            if (type == 0) {
                // 原料码  入原料包装关联表
                packsRef.add(pack);
            } else if (type == 1) {
                packs.add(pack);
            }
        }

        if (!packsRef.isEmpty()) {
            materialMapper.insertMaterialPackRefList(packsRef);
        }

        if (!packs.isEmpty()) {
            materialMapper.insertMaterialPackList(packs);
        }
    }

    /**
     *  通过一个码判断它的类型
     * @param code  二维码
     * @return  二维码的类型
     */
    @Override
    public int guessCodeType(String code) {
        MaterialCode materialCode = codingMapper.selectMaterialCodeByCode(code);
        if (materialCode != null) {
            // 原料码
            return 0;
        }else {
            PackCode packCode = codingMapper.selectPackCodeByCode(code);
            if (packCode != null) {
                // 包装码
                return 1;
            } else {
                // 既不是物料码 也不是包装码
                return 2;
            }
        }
    }

    /**
     *  获取第一层包装编码
     * @param secondCode  包装码
     * @return  所有的第一层包装码
     */
    @Override
    public List<String> getMinFirstCode(String secondCode) {

        List<String> list = new ArrayList<>();
        List<MaterialPack> goodsPacks = assignMaterialMapper.selectMaterialPack(secondCode);

        if (goodsPacks.size() == 0) {
            // 表示没有子包装  仅仅只是第一层包装
            list.add(secondCode);
        } else {
            // 有子包装
            for (MaterialPack pack : goodsPacks) {
                List<String> minFirstCode = getMinFirstCode(pack.getFirstCode());
                list.addAll(minFirstCode);
            }
        }
        return list;
    }
}
