package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<Goods>{
    //查询所有产品信息
    List<Goods> SelectGoods(@Param("page")Pagination page, @Param("goodsCode")String goodsCode, @Param("goodsName")String goodsName);

    //查询产品名是否存在

    Integer selectGoodsName(@Param("goodsName")String goodsName);

    //查询产品下拉
    List<Goods> selectGoodsCombobox(@Param("goodsName")String goodsName);

    //增加产品信息
    Integer InsertGoods(Goods goods);

    //修改产品信息
    void UpdateGoods(Goods goods);

    //删除产品信息
    void DeleteGoods(@Param("goodsId")Integer goodsId);

    // 通过产品名查询产品编号
    Integer selectGoodsIdByGoodsName(@Param("goodsName")String goodsName);

    List<Goods> selectGoodsByGoodsName(@Param("goodsName")String goodsName);
}
