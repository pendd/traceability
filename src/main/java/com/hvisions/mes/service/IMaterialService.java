package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MaterialVo;
import com.hvisions.mes.domain.Material;


import java.util.List;

public interface IMaterialService {

    Page<Material> showMaterial(Page<Material> page,String materialName,String materialSignCode);

    List<Material> queryMaterialCombobox();

    Integer findMaterialName(String materialName);

    void AddMaterial(Material material);


    String modMaterial(Material material);


    void DelMaterial(Integer ids);

    /**
     *  原料名  原料数量  最低top5
     */
    List<MaterialVo> queryMaterialCount();
}
