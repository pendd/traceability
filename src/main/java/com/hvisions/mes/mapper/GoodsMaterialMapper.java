package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.GoodsMaterial;import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMaterialMapper extends BaseMapper<GoodsMaterial> {


//    List<GoodsMaterial> selectGoodsMaterial();

    List<GoodsMaterial> selectGoodsMaterial(@Param("page")Pagination page, @Param("materialName")String materialName, @Param("goodsName")String goodsName);
    Integer insertGoodsMaterial(GoodsMaterial goodsMaterial);

    void updateGoodsMaterial(GoodsMaterial goodsMaterial);

  void deleteGoodsMaterial(Integer gmId);
}