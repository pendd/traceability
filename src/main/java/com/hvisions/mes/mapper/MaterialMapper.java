package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.MaterialStoreroomHistoryVo;
import com.hvisions.mes.controller.vo.MaterialVo;
import com.hvisions.mes.domain.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MaterialMapper{

    List<Material> selectMaterial(@Param("page")Pagination page, @Param("materialName")String materialName, @Param("materialSignCode")String materialSignCode);

    Integer selectMaterialName(String materialName);

    List<Material> selectMaterialCombobox();


    Integer insertMaterial(Material material);


    void updateMaterial(Material material);


    void deleteMaterial(Integer materialId);

    // 获取原料名 原料编号
    List<MaterialStoreroomHistoryVo> selectMaterialNameCode();
}
