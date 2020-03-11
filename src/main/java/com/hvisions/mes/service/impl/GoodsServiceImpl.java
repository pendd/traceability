package com.hvisions.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Goods;
import com.hvisions.mes.mapper.GoodsMapper;
import com.hvisions.mes.service.IGoodsService;

import java.util.List;
import java.util.Objects;

@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Page<Goods> showGoods(Page<Goods> page,String goodsCode,String goodsName) {
        page.setRecords(goodsMapper.SelectGoods(page,goodsCode,goodsName));
        return page;
    }

    @Override
    public List<Goods> queryGoodsCombobox(String goodsName) {
        return goodsMapper.selectGoodsCombobox(goodsName == null || Objects.equals(goodsName,"") ? "" : goodsName.trim());
    }

    @Override
    public Integer findGoodsName(String goodsName) {
        return goodsMapper.selectGoodsName(goodsName);
    }

    @Override
    public void AddGoods(Goods goods) {

        goodsMapper.InsertGoods(goods);
    }

    @Override
    public void ModGoods(Goods goods) {

        goodsMapper.UpdateGoods(goods);
    }

    @Override
    public void DelGoods(Integer ids) {

        goodsMapper.DeleteGoods(ids);
    }

    /**
     *  通过产品名查询产品编号
     * @param goodsName  产品名称
     * @return
     */
    @Override
    public Integer queryGoodsIdByGoodsName(String goodsName) {
        return goodsMapper.selectGoodsIdByGoodsName(goodsName);
    }

    @Override
    public List<Goods> queryGoodsByGoodsName(String goodsName) {
        return goodsMapper.selectGoodsByGoodsName(goodsName == null || Objects.equals("",goodsName) ? "" : goodsName.trim());
    }
}
