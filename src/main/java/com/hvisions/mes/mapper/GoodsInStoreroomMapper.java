package com.hvisions.mes.mapper;

import com.hvisions.mes.controller.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsInStoreroomMapper {

    // 成品合格率
    List<GoodsVo> queryAllGoods();

}