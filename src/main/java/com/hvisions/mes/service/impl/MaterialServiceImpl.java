package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MaterialVo;
import com.hvisions.mes.domain.Material;
import com.hvisions.mes.mapper.MaterialMapper;
import com.hvisions.mes.mapper.MaterialStoreroomHistoryMapper;
import com.hvisions.mes.service.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

@Service
public class MaterialServiceImpl implements IMaterialService {
    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialStoreroomHistoryMapper historyMapper;

    @Override
    public Page<Material> showMaterial(Page<Material> page,String materialName,String materialSignCode) {
        page.setRecords(materialMapper.selectMaterial(page,materialName,materialSignCode));
        return page;
    }

    @Override
    public List<Material> queryMaterialCombobox() {
        return materialMapper.selectMaterialCombobox();
    }

    @Override
    public Integer findMaterialName(String materialName) {
        return materialMapper.selectMaterialName(materialName);
    }

    @Override
    public void AddMaterial(Material material) {
        materialMapper.insertMaterial(material);
    }

    @Override
    public String modMaterial(Material material) {
        String res;
        try {
            materialMapper.updateMaterial(material);
            res = "true";
        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }
        return res;
    }

    @Override
    public void DelMaterial(Integer ids) {
        materialMapper.deleteMaterial(ids);
    }

    /**
     *  获取原料名  原料数量  最小的五个
     * @return
     */
    @Override
    public List<MaterialVo> queryMaterialCount() {
        // 获取原料出入库数量
        List<MaterialVo> historys = historyMapper.selectMaterialHistoryCount();

        // 原料剩余数量 = 入库数量 - 出库数量
        for (MaterialVo history : historys) {
            history.setOverNum(history.getInNum()-history.getOutNum());
        }

        // 排序
        Collections.sort(historys);

        if (historys.size() > 5){
            return historys.subList(0,5);
        }
        return historys;
    }
}
