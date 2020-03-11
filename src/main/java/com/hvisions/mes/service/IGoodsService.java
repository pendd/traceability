package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Goods;


public interface IGoodsService {

    Page<Goods> showGoods(Page<Goods> page,String goodsCode,String goodsName);

    List<Goods> queryGoodsCombobox(String goodsName);

    Integer findGoodsName(String goodsName);

    void AddGoods(Goods goods);


    void ModGoods(Goods goods);


    void DelGoods(Integer ids);

    // 通过产品名查询产品编号
    Integer queryGoodsIdByGoodsName(String goodsName);

    List<Goods> queryGoodsByGoodsName(String goodsName);
}
