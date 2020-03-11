package com.hvisions.mes.service.impl;

import com.hvisions.mes.controller.vo.GoodsSortVo;
import com.hvisions.mes.controller.vo.GoodsVo;
import com.hvisions.mes.mapper.GoodsInStoreroomMapper;
import com.hvisions.mes.service.IGoodsInStoreroomService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * @author dpeng
 * @create 2019-03-27 21:59
 */
@Service
public class GoodsInStoreroomServiceImpl implements IGoodsInStoreroomService {

    @Autowired
    private GoodsInStoreroomMapper mapper;

    /**
     *  成品合格率top5
     * @return
     */
    @Override
    public List<GoodsVo> getAllGoods() {
        List<GoodsVo> goodsVos = mapper.queryAllGoods();
        for (GoodsVo goodsVo : goodsVos) {
            double div = goodsVo.getDivideCount() * 1.0 / goodsVo.getAllCount();
            Long round = Math.round(div*100);
            goodsVo.setQualifiedPercent(div);
            goodsVo.setQualifiedPercentRound(round.intValue());
        }
        Collections.sort(goodsVos);
        /*TreeSet<GoodsVo> treeSet = new TreeSet<GoodsVo>(goodsVos);
        goodsVos.clear();
        goodsVos.addAll(treeSet);*/
        // 取前五个元素    包含前 不包含后
        if (goodsVos.size() > 5) {
            return goodsVos.subList(0, 5);
        }else {
            return goodsVos;
        }
    }

    /**
     *  成品库存top5
     * @return
     */
    @Override
    public List<GoodsSortVo> getCountGoods() {
        List<GoodsVo> goodsVos = mapper.queryAllGoods();
        List<GoodsSortVo> goodsSortVos = new ArrayList<>();

        for (GoodsVo goodsVo : goodsVos) {
            GoodsSortVo sortVo = new GoodsSortVo();
            try {
                BeanUtils.copyProperties(sortVo,goodsVo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            goodsSortVos.add(sortVo);
        }

        // 排序
        Collections.sort(goodsSortVos);

        if (goodsSortVos.size() > 5) {
            return goodsSortVos.subList(0,5);
        }
        return goodsSortVos;
    }
}
